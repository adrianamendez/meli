package denise.mendez.meli.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import denise.mendez.data.network.ApiResponseCallAdapterFactory
import denise.mendez.data.network.BASE_URL
import denise.mendez.data.network.ItemDescriptionDtoJsonAdapter
import denise.mendez.data.remote.apis.MeliApi
import denise.mendez.data.storage.DataStorage
import denise.mendez.domain.utils.AndroidLogger
import denise.mendez.meli.MeliApplication
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
    fun provideApplication(@ApplicationContext app: Context): MeliApplication {
        return app as MeliApplication
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(ItemDescriptionDtoJsonAdapter())
            .add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
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
        networkInterceptor: Interceptor,
        logger: AndroidLogger,
        @ApplicationContext context: Context
    ): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
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
            logger.d("ErrorNetwork", e.toString())
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

    @Provides
    @Singleton
    fun provideNetworkInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            if (!isInternetAvailable(context)) {
                throw IOException("Please check your internet connection.")
            }
            chain.proceed(chain.request())
        }
    }

    @Provides
    @Singleton
    fun providesMeli(
        retrofit: Retrofit
    ): MeliApi = retrofit.create(MeliApi::class.java)
}
