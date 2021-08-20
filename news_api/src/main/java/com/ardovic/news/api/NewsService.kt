package com.ardovic.news.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("/v2/top-headlines")
    suspend fun topHeadlines(@Query("country") country: String): Articles
}

fun NewsService(apiKey: String): NewsService {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->

            val authorizedRequest = chain.request().newBuilder()
                .addHeader(HEADER_API_KEY, apiKey)
                .build()

            chain.proceed(authorizedRequest)
        }
        .addInterceptor(HttpLoggingInterceptor()
            .apply { setLevel(HttpLoggingInterceptor.Level.BODY) })
        .build()

    val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .client(okHttpClient)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    return retrofit.create(NewsService::class.java)
}

const val HEADER_API_KEY = "X-Api-Key"
