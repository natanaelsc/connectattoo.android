package br.com.connectattoo.repository

import android.util.Log
import br.com.connectattoo.api.ApiService
import br.com.connectattoo.api.ApiUrl
import br.com.connectattoo.api.response.ClientProfileResponse
import br.com.connectattoo.core.ResourceResult
import br.com.connectattoo.local.database.daos.ClientProfileDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Suppress("TooGenericExceptionCaught")
class UserRepository (private val clientProfileDao: ClientProfileDao){
    private val apiService: ApiService = ApiUrl.instance.create(ApiService::class.java)
    fun getProfileUser(token: String): Flow<ResourceResult<ClientProfileResponse>> = flow{
       //emit(networkBoundResource("Bearer $token"))
      val response =  apiService.getProfileUser("Bearer $token")
        //Log.i("response", response.body().toString())
        Log.i("response",response.body().toString())
        Log.i("response", response.message())
        emit(ResourceResult.Error(null, Throwable()))
    }
/*
    private suspend fun networkBoundResource(token: String): ResourceResult<ClientProfileResponse>{

        var data = clientProfileDao.getClientProfile()

        try {
            with(apiService.getProfileUser(token)){
                val clientProfile = this.body()
                if (this.isSuccessful && clientProfile != null){
                    clientProfileDao.dellClientProfile()
                    clientProfileDao.insertClientProfile(clientProfile.toClientProfileEntity())
                }
                data = clientProfileDao.getClientProfile()
                Log.i("data", data.toString())
            }
        } catch (error: Throwable) {
           return (ResourceResult.Error(data?.toClientProfileResponse(), error))
        }
        Log.i("data", data.toString())
        return (ResourceResult.Success(data?.toClientProfileResponse()))
    }


 */
/*
    fun getClientProfileTags(token: String): Flow<ResourceResult<ClientProfileTagsResponse>> = flow {
        emit(fetchTagsFromDBAndAPI("Bearer $token"))
    }

    private suspend fun fetchTagsFromDBAndAPI(token: String): ResourceResult<ClientProfileTagsResponse>{

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

 */


}
