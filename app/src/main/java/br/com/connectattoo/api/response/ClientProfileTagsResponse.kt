package br.com.connectattoo.api.response

data class ClientProfileTagsResponse(
    val listTagsProfile: List<ClientProfileTagsResponseItem>? = listOf()
)
