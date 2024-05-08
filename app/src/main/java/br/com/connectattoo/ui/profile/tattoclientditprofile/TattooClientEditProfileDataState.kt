package br.com.connectattoo.ui.profile.tattoclientditprofile


data class TattooClientEditProfileDataState (
    val displayName: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val email: String? = "",
    val stateError: String? = ""
)
