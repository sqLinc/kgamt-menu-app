package com.kgamt.menu.app.domain.repositories

import android.util.Log

object JwtUtils {

    fun isTokenExpired(token: String): Boolean {
        return try {
            val parts = token.split(".")
            if (parts.size != 3) return true

            val payload = String(
                android.util.Base64.decode(parts[1], android.util.Base64.URL_SAFE)
            )

            val exp = org.json.JSONObject(payload).getLong("exp")

            val currentTime = System.currentTimeMillis() / 1000
            Log.d("JWT", "exp: $exp, currentTime: $currentTime")
            exp < currentTime
        } catch (e: Exception) {
            true
        }
    }
}