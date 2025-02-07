package br.com.connectattoo.api

import br.com.connectattoo.api.response.ApiConfirmationResponse
import br.com.connectattoo.api.response.TattooClientProfileResponse
import br.com.connectattoo.data.ArtistData
import br.com.connectattoo.data.ClientData
import br.com.connectattoo.data.Tag
import br.com.connectattoo.data.TokenData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("auth/register")
    fun registerUser(@Body registrationData: ClientData): Call<TokenData>

    @Headers("Content-Type: application/json")
    @POST("auth/register/artist")
    fun registerArtist(@Body registrationData: ArtistData): Call<TokenData>

    @GET("users/confirmation")
    suspend fun verifyUserConfirmation(
        @Header("Authorization") authorization: String
    ): Response<ApiConfirmationResponse>

    @GET("profile/me")
    suspend fun getProfileUser(
        @Header("Authorization") authorization: String
    ): Response<TattooClientProfileResponse>

    @DELETE("profile/me/image")
    suspend fun deleteProfilePhoto(
        @Header("Authorization") authorization: String
    ): Response<Unit>

    @Multipart
    @PATCH("profile/me/image")
    suspend fun uploadProfilePhoto(
        @Header("Authorization") authorization: String,
        @Part image: MultipartBody.Part
    ): Response<Unit>

    @PATCH("profile/me")
    suspend fun updateProfile(
        @Header("Authorization") authorization: String,
        @Body fields: Map<String, String>
    ) : Response<Unit>

    @GET("tags")
    suspend fun getAvailableTags(
        @Header("Authorization") authorization: String
    ): Response<List<Tag>>

    @PATCH("profile/me/tags")
    suspend fun saveTagsTattooClient(
        @Header("Authorization") authorization: String,
        @Body listTags: List<String>
    ): Response<Unit>
}
