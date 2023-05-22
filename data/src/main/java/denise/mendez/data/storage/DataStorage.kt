package denise.mendez.data.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorage @Inject constructor(context: Context) {

    private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create {
        context.preferencesDataStoreFile("app_preferences")
    }

    companion object {
        @Volatile
        private var INSTANCE: DataStorage? = null

        fun getInstance(context: Context): DataStorage {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: DataStorage(context.applicationContext).also { INSTANCE = it }
            }
        }
    }

    suspend fun saveToken(token: String) {
        val tokenKey = stringPreferencesKey("auth_token")
        dataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    suspend fun setSomethingPlaying(isSomethingPlaying: Boolean) {
        val tokenKey = booleanPreferencesKey("playing")
        dataStore.edit { preferences ->
            preferences[tokenKey] = isSomethingPlaying
        }
    }

    val somethingPlayingFlow: Flow<Boolean?> = dataStore.data.map { preferences ->
        val tokenKey = booleanPreferencesKey("playing")
        preferences[tokenKey]
    }
    val authTokenFlow: Flow<String?> = dataStore.data.map { preferences ->
        val tokenKey = stringPreferencesKey("auth_token")
        preferences[tokenKey]
    }
}
