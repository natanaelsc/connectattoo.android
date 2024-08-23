package br.com.connectattoo.ui.home.hometattooartist

import br.com.connectattoo.data.Tag

data class HomeTattooArtistState(
    val name: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val stateError: String? = "",
    val tags: List<Tag>? = emptyList(),
    val email: String? = "",
)
