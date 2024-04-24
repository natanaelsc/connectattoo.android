package br.com.connectattoo.data

data class ClientProfile(
    val displayName: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val tags: List<ClientProfileTag> = emptyList()
)
