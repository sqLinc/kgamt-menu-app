package com.kgamt.menu.app.data.repositories

import android.util.Log
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class JwtInterceptor(
    private val settingsRepositoryImpl: SettingsRepositoryImpl
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()

        val token = runBlocking {
            settingsRepositoryImpl.getToken()
        }
        Log.d("JWT", "$token")
        
        val newRequest = if (!token.isNullOrBlank()) {
           originalRequest.newBuilder()
               .addHeader("Authorization", "Bearer $token")
               .build()
        } else {
           originalRequest
        }

       
        Log.d("JWT", "INTERCEPTOR CALLED")
        return chain.proceed(newRequest)

    }

}

