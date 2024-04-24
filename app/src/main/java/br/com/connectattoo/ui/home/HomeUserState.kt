package br.com.connectattoo.ui.home

import br.com.connectattoo.data.ClientProfileTag

data class HomeUserState(
    val displayName: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val stateError: String? = "",
    val tags: List<ClientProfileTag>? = emptyList()
)
