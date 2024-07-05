package com.example.todolistyandex.data.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor class for adding an authorization header to HTTP requests,
 * attaching a Bearer token for authentication.
 */

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer Aerinon")
            .build()
        return chain.proceed(request)
    }
}