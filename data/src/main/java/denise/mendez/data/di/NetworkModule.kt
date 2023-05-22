package denise.mendez.data.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import denise.mendez.data.network.BASE_URL
import denise.mendez.data.storage.DataStorage
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ServiceRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalRetrofit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val READ_TIMEOUT = 30
    private val WRITE_TIMEOUT = 30
    private val CONNECTION_TIMEOUT = 10
    private val CACHE_SIZE_BYTES = 10 * 1024 * 1024L // 10 MB

    // TODO REMOVE
    private val TOKEN_NOT_EMPTY = false

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("appContext")
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDataStorage(@Named("appContext") context: Context): DataStorage {
        return DataStorage.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: Interceptor,
        @Named("authInterceptor") authInterceptor: Interceptor,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val networkInterceptor = provideNetworkInterceptor(context)

        val httpBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(networkInterceptor)
            .connectTimeout(CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level =
                        HttpLoggingInterceptor.Level.BODY
                }
            )

        try {
            return httpBuilder
                .protocols(mutableListOf(Protocol.HTTP_1_1))
                .build()
        } catch (e: Exception) {
            Log.d("ErrorNetwork", e.toString())
        }
        return httpBuilder
            .protocols(mutableListOf(Protocol.HTTP_1_1))
            .build()
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }

    private fun provideNetworkInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            if (!isInternetAvailable(context)) {
                throw IOException("Please check your internet connection.")
            }
            chain.proceed(chain.request())
        }
    }

    // TODO VALIDATE CACHE
/*
    @Provides
    @Singleton
    fun providesCategories(
        retrofit: Retrofit
    ): CategoriesApi = retrofit.create(CategoriesApi::class.java)

*/
}