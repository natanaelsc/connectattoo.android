package br.com.connectattoo.api.response

import br.com.connectattoo.data.Gallery
import br.com.connectattoo.data.Tag
import br.com.connectattoo.data.toGalleryList
import br.com.connectattoo.data.toTagEntity
import br.com.connectattoo.local.database.entity.TattooClientProfileEntity

data class TattooClientProfileResponse(
    val name: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val tags: List<Tag> = emptyList(),
    val email: String? = "",
    val galleries: List<Gallery> = emptyList()
) {
    fun toTattooClientProfileEntity(): TattooClientProfileEntity {
        return TattooClientProfileEntity(
            name = this.name ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = this.tags.toTagEntity(),
            email = this.email,
            galleries = this.galleries.toGalleryList()
        )
    }
}
