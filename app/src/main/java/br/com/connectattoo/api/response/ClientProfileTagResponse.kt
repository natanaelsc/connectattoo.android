package br.com.connectattoo.api.response

import br.com.connectattoo.data.ClientProfileTag
import br.com.connectattoo.local.database.entitys.ClientProfileTagEntity

data class ClientProfileTagResponse(
    val id: String? = "",
    val name: String? = ""
){
    fun toClientProfileTagEntity() : ClientProfileTagEntity = ClientProfileTagEntity(
        id = id,
        name = name
    )
    fun toClientProfileTag() : ClientProfileTag = ClientProfileTag(
        id = id,
        name = name
    )
}
fun List<ClientProfileTagResponse>.toClientProfileTag() : List<ClientProfileTag> =
    this.map {
        it.toClientProfileTag()
    }
fun List<ClientProfileTagResponse>.toClientProfileTagEntity() : List<ClientProfileTagEntity> =
    this.map {
        it.toClientProfileTagEntity()
    }


