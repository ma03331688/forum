package com.example.forum.data.remote.service

import com.example.forum.data.remote.api.JsonPlaceholderApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private const val BASE_URL ="https://jsonplaceholder.typicode.com/"
    private val retrofit= Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api : JsonPlaceholderApi=retrofit.create(JsonPlaceholderApi::class.java)
}