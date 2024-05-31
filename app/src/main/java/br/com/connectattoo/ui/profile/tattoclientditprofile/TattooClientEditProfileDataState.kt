package br.com.connectattoo.ui.profile.tattoclientditprofile

data class TattooClientEditProfileDataState(
    val name: String? = "",
    val username: String? = "",
    val birthDate: String? = "",
    val imageProfile: String? = "",
    val email: String? = "",
    val stateError: String? = "",
    val stateErrorDeleteImage: String? = "",
    val messageDeleteImage: String? = ""
)
