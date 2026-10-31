package com.example.balneapp.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore by preferencesDataStore(name = "preferencias_usuario")

data class PreferenciasUsuario(
    val nombre: String = "Rodrigo Perez",
    val email: String = "rodrigoperez@gmail.com",
    val notificacionesHabilitadas: Boolean = true
)

class PreferenciasUsuarioRepository(context: Context) {
    private val dataStore = context.applicationContext.dataStore

    val preferencias: Flow<PreferenciasUsuario> = dataStore.data
        .catch { error ->
            if (error is IOException) emit(androidx.datastore.preferences.core.emptyPreferences())
            else throw error
        }
        .map { values ->
            PreferenciasUsuario(
                nombre = values[CLAVE_NOMBRE] ?: PreferenciasUsuario().nombre,
                email = values[CLAVE_EMAIL] ?: PreferenciasUsuario().email,
                notificacionesHabilitadas = values[CLAVE_NOTIFICACIONES] ?: true
            )
        }

    suspend fun guardarPerfil(nombre: String, email: String) {
        dataStore.edit { values ->
            values[CLAVE_NOMBRE] = nombre.trim()
            values[CLAVE_EMAIL] = email.trim()
        }
    }

    suspend fun establecerNotificaciones(habilitadas: Boolean) {
        dataStore.edit { it[CLAVE_NOTIFICACIONES] = habilitadas }
    }

    private companion object {
        val CLAVE_NOMBRE = stringPreferencesKey("nombre")
        val CLAVE_EMAIL = stringPreferencesKey("email")
        val CLAVE_NOTIFICACIONES = booleanPreferencesKey("notificaciones_habilitadas")
    }
}
