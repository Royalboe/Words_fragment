package com.example.wordsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException


private const val LAYOUT_PREFERENCES_NAME = "layout_preferences"
/* Create a DataStore instance using the preferencesDataStore delegate, with the Context as
 receiver.
 Create the DataStore instance at the top level of your Kotlin file once, and access it through
 this property throughout the rest of your application.
 This makes it easier to keep your DataStore as a singleton.*/
val Context.datastore: DataStore<Preferences> by preferencesDataStore(
    LAYOUT_PREFERENCES_NAME
)

class SettingsDataStore(context: Context) {
    private val IS_LINEAR_LAYOUT_MANAGER = booleanPreferencesKey("is_Linear_Layout_Manager")

    suspend fun saveLayoutToPreferencesStore(isLinearLayoutManager: Boolean, context: Context) =
        context.datastore.edit {
            it[IS_LINEAR_LAYOUT_MANAGER] = isLinearLayoutManager
        }
    val preferencesFlow: Flow<Boolean> = context.datastore.data
        .catch {
            if(it is IOException) {
                it.printStackTrace()
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map {
        it[IS_LINEAR_LAYOUT_MANAGER] ?: true
    }
}