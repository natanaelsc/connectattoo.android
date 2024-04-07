package br.com.connectattoo.api

import br.com.connectattoo.api.response.ApiConfirmationResponse
import br.com.connectattoo.data.ArtistData
import br.com.connectattoo.data.ClientData
import br.com.connectattoo.data.TokenData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    fun registerUser (@Body registrationData: ClientData): Call<TokenData>

    @Headers("Content-Type: application/json")
    @POST("auth/register/artist")
    fun registerArtist (@Body registrationData: ArtistData): Call<TokenData>

    @GET("users/confirmation")
    suspend fun verifyUserConfirmation(
        @Header("Authorization") authorization: String
    ): Response<ApiConfirmationResponse>
}
