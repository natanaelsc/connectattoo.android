package br.com.connectattoo.repository

import android.util.Log
import br.com.connectattoo.api.ApiService
import br.com.connectattoo.api.ApiUrl
import br.com.connectattoo.api.response.ClientProfileResponse
import br.com.connectattoo.core.ResourceResult
import br.com.connectattoo.local.database.daos.ClientProfileDao
import br.com.connectattoo.util.Converters.toClientProfileResponse
import br.com.connectattoo.util.Converters.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

@Suppress("TooGenericExceptionCaught")
class UserRepository (private val clientProfileDao: ClientProfileDao){
    private val apiService: ApiService = ApiUrl.instance.create(ApiService::class.java)
    fun getProfileUser(token: String): Flow<ResourceResult<ClientProfileResponse>> = flow{
       emit(networkBoundResource("Bearer $token"))
    }

    private suspend fun networkBoundResource(token: String): ResourceResult<ClientProfileResponse>{

        var data = clientProfileDao.getClientProfile()

        try {
            with(apiService.getProfileUser(token)){
                val clientProfile = this.body()
                if (this.isSuccessful && clientProfile != null){
                    clientProfileDao.dellClientProfile()
                    clientProfileDao.insertClientProfile(clientProfile.toEntity())
                }
                data = clientProfileDao.getClientProfile()
            }
        } catch (error: Throwable) {
           return (ResourceResult.Error(data?.toClientProfileResponse(), error))
        }
        return (ResourceResult.Success(data?.toClientProfileResponse()))
    }
}
