package br.com.connectattoo.ui.tattooclientprofile

import br.com.connectattoo.data.ClientProfile
import br.com.connectattoo.data.ClientProfileTag
import br.com.connectattoo.data.MyGalleryProfile
import br.com.connectattoo.data.TagTattooClientProfile

data class TattooClientProfileState(
    val txtNameUser: String? = null,
    val txtAgeAndEmail: String? = null,
    val txtNameTattooArtist: String? = null,
    val txtTattooArtistProfile: String? = null,
    val txtScheduleTomorrow: String? = null,
    val txtScheduleHour: String? = null,
    val listTagsTattooClientProfile: List<TagTattooClientProfile>? = listOf(),
    val listGalleriesTattooClientProfile: List<MyGalleryProfile>? = listOf(),
    val userImage: String? = null,
    val imageTattooArtist: String? = null,
    val clientProfile: ClientProfile? = null,
    val clientProfileTag: List<ClientProfileTag>? = emptyList(),
    val stateError: String? = ""
)
