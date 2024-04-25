package br.com.connectattoo.repository

import android.content.ContentValues.TAG
import android.util.Log
import br.com.connectattoo.api.ApiService
import br.com.connectattoo.api.ApiUrl
import br.com.connectattoo.core.MessageException
import br.com.connectattoo.core.ResourceResult
import br.com.connectattoo.data.TattooClientProfile
import br.com.connectattoo.local.database.dao.TattooClientProfileDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class UserRepository(private val tattooClientProfileDao: TattooClientProfileDao) {
    private val apiService: ApiService = ApiUrl.instance.create(ApiService::class.java)
    fun getProfileUser(token: String): Flow<ResourceResult<TattooClientProfile>> = flow {
        emit(networkBoundResource("Bearer $token"))
    }

    private suspend fun networkBoundResource(token: String): ResourceResult<TattooClientProfile> {
        var data = tattooClientProfileDao.getTattooClientProfile()

        try {
            with(apiService.getProfileUser(token)) {
                val clientProfile = this.body()
                if (this.isSuccessful && clientProfile != null) {
                    tattooClientProfileDao.dellTattooClientProfile()
                    tattooClientProfileDao.insertTattooClientProfile(clientProfile.toClientProfileEntity())
                }
                data = tattooClientProfileDao.getTattooClientProfile()
            }
        } catch (error: IOException) {
            val message = MessageException("Erro na requisição a api")
            Log.e(TAG, error.message.toString())
            return (ResourceResult.Error(data?.toTattooClientProfile(), message))
        }
        return (ResourceResult.Success(data?.toTattooClientProfile()))
    }


    fun getClientProfileRoom(): Flow<ResourceResult<TattooClientProfile>> = flow {
        try {
            val data = tattooClientProfileDao.getTattooClientProfile()
            if (data != null) {
                emit(ResourceResult.Success(data.toTattooClientProfile()))
            } else {
                val error = MessageException("Erro na recuperação dos dados locais")
                emit(ResourceResult.Error(null, error))
            }
        } catch (error: IOException) {
            emit(ResourceResult.Error(null, error))
        }

    }
}
