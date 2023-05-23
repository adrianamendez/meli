package denise.mendez.domain.utils

import android.util.Log
import denise.mendez.domain.BuildConfig
import javax.inject.Inject

class AndroidLogger @Inject constructor() : Logger {
    override fun d(tag: String, msg: String) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg)
        }
    }
}
