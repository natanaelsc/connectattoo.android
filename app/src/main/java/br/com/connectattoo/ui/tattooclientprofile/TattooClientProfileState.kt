package br.com.connectattoo.ui.tattooclientprofile

import br.com.connectattoo.data.TattooClientProfile
import br.com.connectattoo.data.Tag
import br.com.connectattoo.data.MyGalleryProfile

data class TattooClientProfileState(
    val txtNameUser: String? = null,
    val txtAgeAndEmail: String? = null,
    val txtNameTattooArtist: String? = null,
    val txtTattooArtistProfile: String? = null,
    val txtScheduleTomorrow: String? = null,
    val txtScheduleHour: String? = null,
    val listTagsTattooClientProfile: List<Tag>? = listOf(),
    val listGalleriesTattooClientProfile: List<MyGalleryProfile>? = listOf(),
    val userImage: String? = null,
    val imageTattooArtist: String? = null,
    val tattooClientProfile: TattooClientProfile? = null,
    val tag: List<Tag>? = emptyList(),
    val stateError: String? = ""
)
