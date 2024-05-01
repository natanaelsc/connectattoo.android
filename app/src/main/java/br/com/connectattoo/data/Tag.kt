package br.com.connectattoo.data

import br.com.connectattoo.local.database.entitys.TagEntity

data class Tag(
    val id: String? = "",
    val name: String? = ""
){
    fun toTagEntity(): TagEntity = TagEntity(
        id = id,
        name = name
    )
}
fun List<Tag>.toTagEntity(): List<TagEntity> =
    this.map {
        it.toTagEntity()
    }
