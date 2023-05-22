package denise.mendez.meli.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import denise.mendez.data.remote.apis.MeliApi
import denise.mendez.data.repository.ItemsRepositoryImpl
import denise.mendez.data.repository.SitesRepositoryImpl
import denise.mendez.domain.repositories.ItemsRepository
import denise.mendez.domain.repositories.SitesRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideItemsRepository(
        meliApi: MeliApi
    ): ItemsRepository = ItemsRepositoryImpl(meliApi)

    @Singleton
    @Provides
    fun provideSitesRepository(
        meliApi: MeliApi
    ): SitesRepository = SitesRepositoryImpl(meliApi)

}