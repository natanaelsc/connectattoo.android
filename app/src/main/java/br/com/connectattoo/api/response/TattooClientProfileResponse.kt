package br.com.connectattoo.api.response

import br.com.connectattoo.data.Gallery
import br.com.connectattoo.data.Tag
import br.com.connectattoo.data.toGalleryList
import br.com.connectattoo.data.toTagEntity
import br.com.connectattoo.local.database.entity.TattooClientProfileEntity

data class TattooClientProfileResponse(
    val displayName: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val tags: List<Tag> = emptyList(),
    val email: String? = "",
    val galleries: List<Gallery> = mock()
) {
    fun toTattooClientProfileEntity(): TattooClientProfileEntity {
        return TattooClientProfileEntity(
            displayName = this.displayName ?: "",
            username = this.username ?: "",
            birthDate = this.birthDate ?: "",
            imageProfile = this.imageProfile ?: "",
            tags = this.tags.toTagEntity(),
            email = this.email,
            galleries = this.galleries.toGalleryList()
        )
    }
}

fun mock(): List<Gallery> {
    val gallery = listOf(
        Gallery(
            id = "6524a08f-a04d-4bc2-97f4-c975a329ed29",
            name = "Animais Cartoon"
        ),
        Gallery(
            "535c2bc1-46b8-47e2-84ee-ac3dd4cead1f",
            "Caveiras"
        ),
        Gallery(

            "5e880889-8093-4b31-82c9-f331ce8fc92e",
            "Mãos"
        ),
        Gallery(
            "d8c7cf42-184f-4d03-906d-6b4daa8bc72a",
            "Costas Fechadas"
        )


    )
    return gallery
}
