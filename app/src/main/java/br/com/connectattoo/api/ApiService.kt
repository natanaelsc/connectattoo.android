package br.com.connectattoo.api

import br.com.connectattoo.data.TokenData
import br.com.connectattoo.data.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    fun registerUser (@Body registrationData: UserData): Call<TokenData>
}
