package br.com.connectattoo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiUrl {
    private const val BASE_URL = "https://connectattoo-api-staging.up.railway.app/api/v1/"

    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
