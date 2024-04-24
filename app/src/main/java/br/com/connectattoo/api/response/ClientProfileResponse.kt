package br.com.connectattoo.api.response

import br.com.connectattoo.local.database.entitys.ClientProfileEntity
import br.com.connectattoo.local.database.entitys.ClientProfileTagEntity

data class ClientProfileResponse(
    val displayName: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val tags: List<String> = emptyList()
) {
    private fun mapStringsToTags(tagNames: List<String>): List<ClientProfileTagEntity> {
        return tagNames.map { ClientProfileTagEntity(name = it) }
    }

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
