package denise.mendez.meli

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MeliApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
