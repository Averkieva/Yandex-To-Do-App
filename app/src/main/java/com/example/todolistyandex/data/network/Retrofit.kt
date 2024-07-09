package com.example.todolistyandex.data.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection

/**
 * Singleton object for creating a Retrofit client configured with a base URL,
 * an authorization interceptor, and a hostname verifier, providing an instance
 * of the ApiService for making network requests.
 */

object RetrofitClient {
    private const val BASE_URL = "https://beta.mrdekk.ru/todo/"

    private val authInterceptor = AuthInterceptor()

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    private val hostnameVerifier = HostnameVerifier { hostname, session ->
        hostname == "beta.mrdekk.ru" || HttpsURLConnection.getDefaultHostnameVerifier()
            .verify(hostname, session)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .hostnameVerifier(hostnameVerifier)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val api: ApiService = retrofit.create(ApiService::class.java)
}

