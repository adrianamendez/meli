package denise.mendez.meli.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import denise.mendez.domain.utils.AndroidLogger
import denise.mendez.domain.utils.Logger

@Module
@InstallIn(SingletonComponent::class)
object LoggerModule {
    @Provides
    fun provideLogger(androidLogger: AndroidLogger): Logger = androidLogger
}