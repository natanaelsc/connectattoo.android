package br.com.connectattoo.local.database.entitys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.connectattoo.api.response.ClientProfileResponse
import br.com.connectattoo.api.response.ClientProfileTagResponse

@Entity(tableName = "client_profile_tags")
data class ClientProfileTagEntity (
    @PrimaryKey(autoGenerate = true) val idRoom: Long = 0,
    @ColumnInfo("id_api") val id: String? = "",
    @ColumnInfo("tag_name") val name: String? = ""
){
    fun toClientProfileTagResponse() : ClientProfileTagResponse = ClientProfileTagResponse(
        id = id,
        name = name
    )
}
fun List<ClientProfileTagEntity>.toClientProfileTagResponse() : List<ClientProfileTagResponse> =
    this.map {
        it.toClientProfileTagResponse()
    }
