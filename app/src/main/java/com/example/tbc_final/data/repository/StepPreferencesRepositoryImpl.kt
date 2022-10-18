package com.example.tbc_final.data.repository


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.tbc_final.domain.repository.StepPreferencesRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class StepPreferencesRepositoryImpl @Inject constructor(
    private val stepPreferences: DataStore<Preferences>
):StepPreferencesRepository {
    override suspend fun putStep(steps: String) {
        Result.runCatching {
            stepPreferences.edit {preferences ->
                preferences[stringPreferencesKey(KEY_GOAL)] = steps
            }
        }
    }

    override suspend fun getStep(): Result<String> {
        return Result.runCatching {
            val flow = stepPreferences.data.catch {
                if(it is IOException){
                    emit(emptyPreferences())
                }else{
                    throw it
                }
            }
                .map {
                    it[stringPreferencesKey(KEY_GOAL)]
                }
            val value = flow.firstOrNull() ?: ""
            value
        }
    }

    companion object{
        private const val KEY_GOAL = "goalKey"
    }
}