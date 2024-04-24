package br.com.connectattoo.api.response

import br.com.connectattoo.data.ClientProfile
import br.com.connectattoo.data.ClientProfileTag
import br.com.connectattoo.local.database.entitys.ClientProfileEntity
import br.com.connectattoo.local.database.entitys.ClientProfileTagEntity

data class ClientProfileResponse(
    val displayName: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val tags: List<String> = emptyList()
){
    fun mapStringsToTags(tagNames: List<String>): List<ClientProfileTagEntity> {
        return tagNames.map { ClientProfileTagEntity(name = it) }
    }
    /*

    fun toClientProfile(): ClientProfile {
        return ClientProfile(
            displayName = this.displayName ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = this.tags
        )
    }

     */

    fun toClientProfileEntity(): ClientProfileEntity {
        return ClientProfileEntity(
            displayName = this.displayName ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = mapStringsToTags(this.tags)
        )
    }



}



