package br.com.connectattoo.api.response

import br.com.connectattoo.local.database.entitys.TattooClientProfileEntity
import br.com.connectattoo.local.database.entitys.TagEntity

data class TattooClientProfileResponse(
    val displayName: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val tags: List<String> = emptyList()
) {
    private fun mapStringsToTags(tagNames: List<String>): List<TagEntity> {
        return tagNames.map { TagEntity(name = it) }
    }

    fun toClientProfileEntity(): TattooClientProfileEntity {
        return TattooClientProfileEntity(
            displayName = this.displayName ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = mapStringsToTags(this.tags)
        )
    }


}
