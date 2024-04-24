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
    fun mapStringsToTags(tagNames: List<String>): List<ClientProfileTag> {
        return tagNames.map { ClientProfileTag(name = it) }
    }
    /*
    fun mapResponseToProfile(response: ClientProfileResponse): ClientProfile {
        val tagList = mutableListOf<ClientProfileTag>()
        for (tagResponse in response.tags) {
            val tag = ClientProfileTag(name = tagResponse)
            tagList.add(tag)
        }
        return ClientProfile(
            displayName = response.displayName,
            username = response.username,
            birthDate = response.birthDate,
            imageProfile = response.imageProfile,
            tags = tagList
        )
    }

    fun toClientProfile(): ClientProfile {
        return ClientProfile(
            displayName = this.displayName ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = this.tags
        )
    }

    fun toClientProfileEntity(): ClientProfileEntity {
        return ClientProfileEntity(
            displayName = this.displayName ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = this.tags
        )
    }


     */
}



