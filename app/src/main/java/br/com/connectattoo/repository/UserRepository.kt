package br.com.connectattoo.repository

import br.com.connectattoo.api.ApiService
import br.com.connectattoo.api.ApiUrl
import br.com.connectattoo.api.response.ProfileUserResponse
import retrofit2.Response

class UserRepository {
    suspend fun getProfileUser(token: String): Response<ProfileUserResponse>{
        val apiService = ApiUrl.instance.create(ApiService::class.java)
        return apiService.getProfileUser(token)
    }
}
