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
import java.util.Locale

private val Context.dataStore by preferencesDataStore(name = "preferencias_usuario")

data class PreferenciasUsuario(
    val nombre: String = "",
    val email: String = "",
    val notificacionesHabilitadas: Boolean = true,
    val sesionIniciada: Boolean = false,
    val tarjetasGuardadas: List<String> = emptyList()
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
                notificacionesHabilitadas = values[CLAVE_NOTIFICACIONES] ?: true,
                sesionIniciada = values[CLAVE_SESION_INICIADA] ?: false,
                tarjetasGuardadas = values[CLAVE_TARJETAS]
                    ?.lineSequence()
                    ?.filter { it.isNotBlank() }
                    ?.toList()
                    ?: values[CLAVE_TARJETA]?.let { listOf(it) }.orEmpty()
            )
        }

    val sesionIniciada: Flow<Boolean> = preferencias.map { it.sesionIniciada }

    suspend fun iniciarSesion(email: String) {
        val correo = email.trim().lowercase(Locale.ROOT)
        dataStore.edit { values ->
            val correoGuardado = values[CLAVE_EMAIL].orEmpty()
            if (!correoGuardado.equals(correo, ignoreCase = true) || values[CLAVE_NOMBRE].isNullOrBlank()) {
                values[CLAVE_NOMBRE] = nombreDesdeCorreo(correo)
            }
            values[CLAVE_EMAIL] = correo
            values[CLAVE_SESION_INICIADA] = true
        }
    }

    suspend fun registrarCuenta(nombre: String, email: String) {
        dataStore.edit { values ->
            values[CLAVE_NOMBRE] = nombre.trim()
            values[CLAVE_EMAIL] = email.trim().lowercase(Locale.ROOT)
            values[CLAVE_SESION_INICIADA] = true
        }
    }

    suspend fun cerrarSesion() {
        dataStore.edit { values -> values[CLAVE_SESION_INICIADA] = false }
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

    suspend fun guardarTarjeta(marca: String, ultimosDigitos: String) {
        dataStore.edit { values ->
            val tarjetas = (values[CLAVE_TARJETAS]?.lineSequence()?.filter { it.isNotBlank() }?.toList()
                ?: values[CLAVE_TARJETA]?.let { listOf(it) }.orEmpty()).toMutableList()
            val tarjeta = "$marca •••• $ultimosDigitos"
            if (tarjeta !in tarjetas) tarjetas.add(tarjeta)
            values[CLAVE_TARJETAS] = tarjetas.joinToString("\n")
            values.remove(CLAVE_TARJETA)
        }
    }

    suspend fun quitarTarjeta(tarjeta: String) {
        dataStore.edit { values ->
            val tarjetas = (values[CLAVE_TARJETAS]?.lineSequence()?.filter { it.isNotBlank() }?.toList()
                ?: values[CLAVE_TARJETA]?.let { listOf(it) }.orEmpty()).filterNot { it == tarjeta }
            if (tarjetas.isEmpty()) values.remove(CLAVE_TARJETAS)
            else values[CLAVE_TARJETAS] = tarjetas.joinToString("\n")
            values.remove(CLAVE_TARJETA)
        }
    }

    private fun nombreDesdeCorreo(email: String): String = email.substringBefore('@')
        .replace('.', ' ')
        .replace('_', ' ')
        .replace('-', ' ')
        .split(' ')
        .filter(String::isNotBlank)
        .joinToString(" ") { palabra -> palabra.replaceFirstChar { it.titlecase(Locale("es", "AR")) } }
        .ifBlank { "Usuario BalneApp" }

    private companion object {
        val CLAVE_NOMBRE = stringPreferencesKey("nombre")
        val CLAVE_EMAIL = stringPreferencesKey("email")
        val CLAVE_NOTIFICACIONES = booleanPreferencesKey("notificaciones_habilitadas")
        val CLAVE_SESION_INICIADA = booleanPreferencesKey("sesion_iniciada")
        val CLAVE_TARJETA = stringPreferencesKey("tarjeta_guardada")
        val CLAVE_TARJETAS = stringPreferencesKey("tarjetas_guardadas")
    }
}
