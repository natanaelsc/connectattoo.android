package br.com.connectattoo.data

data class Gallery(
    val id: String? = "",
    val name: String? = ""
)

fun List<Gallery>.toGalleryList(): List<Gallery> =
    this.map {
        it
    }
