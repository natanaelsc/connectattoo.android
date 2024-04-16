package br.com.connectattoo.ui.customerprofile

import br.com.connectattoo.data.MyGalleryProfile
import br.com.connectattoo.data.TagCustomerProfile

data class CustomerProfileState(
    val txtNameUser: String? = null,
    val txtAgeAndEmail: String? = null,
    val txtNameTattooArtist: String? = null,
    val txtTattooArtistProfile: String? = null,
    val txtScheduleTomorrow: String? = null,
    val txtScheduleHour: String? = null,
    val listTagsCustomerProfile: List<TagCustomerProfile>? = listOf(),
    val listGalleriesCustomerProfile: List<MyGalleryProfile>? = listOf(),
    val userImage: String? = null,
    val imageTattooArtist: String? = null
)
