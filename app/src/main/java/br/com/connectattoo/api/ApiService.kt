package br.com.connectattoo.api

import br.com.connectattoo.domain.UserDomain
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun registerUser (@Body registrationData: UserDomain)
}
