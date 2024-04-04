package br.com.connectattoo.repository

import br.com.connectattoo.api.ApiService
import br.com.connectattoo.api.ApiUrl
import br.com.connectattoo.data.ApiConfirmationResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class AuthRepository {
    suspend fun verifyUserConfirmation(token: String) : Response<ApiConfirmationResponse>{
         val apiService: ApiService =  ApiUrl.instance.create(ApiService::class.java)
        return apiService.verifyUserConfirmation(token)
    }
}
