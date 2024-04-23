package br.com.connectattoo.util

import br.com.connectattoo.api.response.ClientProfileResponse
import br.com.connectattoo.local.database.entitys.ClientProfileEntity

object Converters {
    fun ClientProfileResponse.toEntity(): ClientProfileEntity {
        return ClientProfileEntity(
            displayName = this.displayName,
            username = this.username,
            birthDate = this.birthDate,
            imageProfile = this.imageProfile
        )
    }

    fun ClientProfileEntity.toClientProfileResponse(): ClientProfileResponse{
        return ClientProfileResponse(
            displayName = this.displayName,
            username = this.username,
            birthDate = this.birthDate,
            imageProfile = this.imageProfile
        )
    }
}
