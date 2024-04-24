package br.com.connectattoo.local.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.connectattoo.data.ClientProfileTag

@Entity(tableName = "client_profile_tags")
data class ClientProfileTagEntity(
    @PrimaryKey(autoGenerate = true) val idRoom: Long = 0,
    @ColumnInfo("id_api") val id: String? = "",
    @ColumnInfo("tag_name") val name: String? = ""
) {
    fun toClientProfileTagResponse(): ClientProfileTag = ClientProfileTag(
        id = id,
        name = name
    )
}

fun List<ClientProfileTagEntity>.toClientProfileTag(): List<ClientProfileTag> =
    this.map {
        it.toClientProfileTagResponse()
    }
