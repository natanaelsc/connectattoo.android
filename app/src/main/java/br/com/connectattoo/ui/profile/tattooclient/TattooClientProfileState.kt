package br.com.connectattoo.ui.profile.tattooclient

import br.com.connectattoo.data.Gallery
import br.com.connectattoo.data.TattooClientProfile
import br.com.connectattoo.data.Tag
import br.com.connectattoo.data.MyGalleryProfile

data class TattooClientProfileState(
    val txtNameUser: String? = null,
    val txtAgeAndName: String? = null,
    val txtNameTattooArtist: String? = null,
    val txtTattooArtistProfile: String? = null,
    val txtScheduleTomorrow: String? = null,
    val txtScheduleHour: String? = null,
    val listTagsTattooClientProfile: List<Tag>? = listOf(),
    val listGalleriesTattooClientProfile: MutableList<MyGalleryProfile>? = mutableListOf(),
    val userImage: String? = null,
    val imageTattooArtist: String? = null,
    val tattooClientProfile: TattooClientProfile? = null,
    val tag: List<Tag>? = emptyList(),
    val listGalleries: List<Gallery>? = emptyList(),
    val stateError: String? = ""
)
