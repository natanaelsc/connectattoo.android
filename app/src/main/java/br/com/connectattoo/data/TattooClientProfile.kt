package br.com.connectattoo.data

data class TattooClientProfile(
    val displayName: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val tags: List<Tag> = emptyList(),
    val email: String? = "",
    val galleries: List<Gallery> = emptyList()
)
