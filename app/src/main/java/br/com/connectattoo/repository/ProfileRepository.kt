package br.com.connectattoo.repository

import android.content.ContentValues.TAG
import android.util.Log
import br.com.connectattoo.api.ApiService
import br.com.connectattoo.api.ApiUrl
import br.com.connectattoo.core.MessageException
import br.com.connectattoo.core.ResourceResult
import br.com.connectattoo.data.TattooClientProfile
import br.com.connectattoo.local.database.dao.TattooClientProfileDao
import br.com.connectattoo.util.Constants.CODE_SUCCESS_204
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException

class ProfileRepository(private val tattooClientProfileDao: TattooClientProfileDao) {
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
                    tattooClientProfileDao.insertTattooClientProfile(clientProfile.toTattooClientProfileEntity())
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

    fun deleteProfilePhoto(token: String): Flow<ResourceResult<String>> = flow {
        emit(deleteProfilePhotoAPIAndRoom("Bearer $token"))
    }
    private suspend fun deleteProfilePhotoAPIAndRoom(token: String): ResourceResult<String> {
        try {
            with(apiService.deleteProfilePhoto(token)) {
                return resourceResultDeleteProfilePhoto()
            }

        } catch (error: IOException) {
            val message = MessageException("Erro na requisição a api")
            Log.e(TAG, error.message.toString())
            return (ResourceResult.Error(null, message))
        }

    }

    private suspend fun Response<String>.resourceResultDeleteProfilePhoto(): ResourceResult<String> {
        val message = this.code()
        return if (message == CODE_SUCCESS_204) {
            val data = tattooClientProfileDao.getTattooClientProfile()
            data?.let { tattooClientProfileDao.dellTattooClientProfilePhoto(it.id) }
            (ResourceResult.Success("Sucesso ao deletar a foto de perfil"))
        } else {
            val error = MessageException("Foto não encontrada")
            (ResourceResult.Error(null, error))
        }
    }
}
