package br.com.connectattoo.repository

import br.com.connectattoo.api.ApiService
import br.com.connectattoo.api.ApiUrl
import br.com.connectattoo.core.MessageException
import br.com.connectattoo.core.ResourceResult
import br.com.connectattoo.data.ClientProfile
import br.com.connectattoo.local.database.daos.ClientProfileDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Suppress("TooGenericExceptionCaught")
class UserRepository(private val clientProfileDao: ClientProfileDao) {
    private val apiService: ApiService = ApiUrl.instance.create(ApiService::class.java)
    fun getProfileUser(token: String): Flow<ResourceResult<ClientProfile>> = flow {
        emit(networkBoundResource("Bearer $token"))
    }

    private suspend fun networkBoundResource(token: String): ResourceResult<ClientProfile> {
        var data = clientProfileDao.getClientProfile()

        try {
            with(apiService.getProfileUser(token)) {
                val clientProfile = this.body()
                if (this.isSuccessful && clientProfile != null) {
                    clientProfileDao.dellClientProfile()
                    clientProfileDao.insertClientProfile(clientProfile.toClientProfileEntity())
                }
                data = clientProfileDao.getClientProfile()
            }
        } catch (error: Throwable) {
            return (ResourceResult.Error(data?.toClientProfile(), error))
        }
        return (ResourceResult.Success(data?.toClientProfile()))
    }


    fun getClientProfileRoom(): Flow<ResourceResult<ClientProfile>> = flow {
        try {
            val data = clientProfileDao.getClientProfile()
            if (data != null) {
                emit(ResourceResult.Success(data.toClientProfile()))
            }else{
                val error = MessageException("Erro na recuperação dos dados locais")
                emit(ResourceResult.Error(null, error))
            }
        } catch (error: Throwable) {
            emit(ResourceResult.Error(null, error))
        }

    }
}
