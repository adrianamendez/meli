package denise.mendez.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import denise.mendez.domain.repositories.ItemsRepository
import denise.mendez.domain.repositories.SitesRepository
import denise.mendez.domain.usecases.ItemsUseCase
import denise.mendez.domain.usecases.ItemsUseCaseImpl
import denise.mendez.domain.usecases.SitesUseCase
import denise.mendez.domain.usecases.SitesUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideSitesRepository(sitesRepository: SitesRepository): SitesUseCase {
        return SitesUseCaseImpl(sitesRepository)
    }

    @Provides
    @Singleton
    fun provideItemsRepository(itemsRepository: ItemsRepository): ItemsUseCase {
        return ItemsUseCaseImpl(itemsRepository)
    }
}
