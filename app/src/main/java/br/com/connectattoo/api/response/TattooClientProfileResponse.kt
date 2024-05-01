package br.com.connectattoo.api.response

import br.com.connectattoo.data.Tag
import br.com.connectattoo.data.toTagEntity
import br.com.connectattoo.local.database.entity.TattooClientProfileEntity

data class TattooClientProfileResponse(
    val displayName: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val tags: List<Tag> = emptyList()
) {
    /*
    private fun mapStringsToTags(tagNames: List<String>): List<TagEntity> {
        return tagNames.map { TagEntity(name = it) }
    }

     */

    fun toTattooClientProfileEntity(): TattooClientProfileEntity {
        return TattooClientProfileEntity(
            displayName = this.displayName ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = this.tags.toTagEntity()
        )
    }
}
