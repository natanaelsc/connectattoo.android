package br.com.connectattoo.local.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.connectattoo.data.Tag

@Entity(tableName = "client_profile_tags")
data class TagEntity(
    @PrimaryKey(autoGenerate = true) val idRoom: Long = 0,
    @ColumnInfo("id_api") val id: String? = "",
    @ColumnInfo("tag_name") val name: String? = ""
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



