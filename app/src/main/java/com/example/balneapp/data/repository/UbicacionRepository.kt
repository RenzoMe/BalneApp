package com.example.balneapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

data class UbicacionActual(
    val latitud: Double,
    val longitud: Double
)

class UbicacionRepository(context: Context) {
    private val appContext = context.applicationContext

    @SuppressLint("MissingPermission")
    suspend fun obtenerUbicacionAproximada(): UbicacionActual? = withTimeoutOrNull(15_000) {
        suspendCancellableCoroutine { continuation ->
            val manager = appContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val provider = listOf(LocationManager.NETWORK_PROVIDER, LocationManager.GPS_PROVIDER)
                .firstOrNull { name -> runCatching { manager.isProviderEnabled(name) }.getOrDefault(false) }

            if (provider == null) {
                continuation.resume(null)
                return@suspendCancellableCoroutine
            }

            val cached = runCatching { manager.getLastKnownLocation(provider) }.getOrNull()
            val cachedIsRecent = cached != null && System.currentTimeMillis() - cached.time <= MAX_LOCATION_AGE_MS
            if (cachedIsRecent) {
                continuation.resume(cached.toUbicacionActual())
                return@suspendCancellableCoroutine
            }

            val listener = object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    manager.removeUpdates(this)
                    if (continuation.isActive) continuation.resume(location.toUbicacionActual())
                }

                override fun onProviderEnabled(provider: String) = Unit

                override fun onProviderDisabled(provider: String) = Unit

                @Suppress("DEPRECATION")
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) = Unit
            }

            continuation.invokeOnCancellation { manager.removeUpdates(listener) }
            try {
                @Suppress("DEPRECATION")
                manager.requestSingleUpdate(provider, listener, Looper.getMainLooper())
            } catch (_: SecurityException) {
                if (continuation.isActive) continuation.resume(null)
            } catch (_: IllegalArgumentException) {
                if (continuation.isActive) continuation.resume(null)
            }
        }
    }

    private fun Location?.toUbicacionActual(): UbicacionActual? =
        this?.let { UbicacionActual(latitude, longitude) }

    private companion object {
        const val MAX_LOCATION_AGE_MS = 2 * 60 * 1000L
    }
}
