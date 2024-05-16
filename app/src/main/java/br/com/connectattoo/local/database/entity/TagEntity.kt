package br.com.connectattoo.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.connectattoo.data.Tag

@Entity(tableName = "tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true) val idRoom: Long = 0,
    val id: String? = "",
    val name: String? = ""
) {
    fun toTagResponse(): Tag = Tag(
        id = id,
        name = name
    )

}

fun List<TagEntity>.toTag(): List<Tag> =
    this.map {
        it.toTagResponse()
    }



