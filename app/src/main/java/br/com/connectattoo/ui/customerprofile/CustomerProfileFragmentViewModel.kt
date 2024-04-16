package br.com.connectattoo.ui.customerprofile

import androidx.lifecycle.ViewModel
import br.com.connectattoo.data.MyGalleryProfile
import br.com.connectattoo.data.TagCustomerProfile

class CustomerProfileFragmentViewModel : ViewModel() {

    private var _state: CustomerProfileState = CustomerProfileState()
    val state: CustomerProfileState get() = _state

    init {
        getInitialInformationCustomer()
        getListGalleriesCustomerProfile()
        getListTagsCustomerProfile()
    }

    private fun getListTagsCustomerProfile() {
        val listTags = listOf(
            TagCustomerProfile(tag = "Old School"),
            TagCustomerProfile(tag = "Preto e branco"),
            TagCustomerProfile(tag = "Geométrico"),
            TagCustomerProfile(tag = "Geométrico 2"),
            TagCustomerProfile(tag = "Geométrico 3")
        )
        _state = _state.copy(listTagsCustomerProfile = listTags)
    }

    private fun getListGalleriesCustomerProfile() {
        val listImages = listOf(
            MyGalleryProfile(
                1,
                title = "Galeria 1",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_tesoura.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_passaro_na_mao.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_calavera.png",

                ),
            MyGalleryProfile(
                2,
                title = "Galeria 2",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_tesoura.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_passaro_na_mao.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_calavera.png"

            ),
            MyGalleryProfile(
                3,
                title = "Galeria 3",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_tesoura.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_passaro_na_mao.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_calavera.png",

            ),
            MyGalleryProfile(
                4,
                title = "Galeria 4",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_tesoura.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_passaro_na_mao.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_calavera.png",

            ),
        )
        _state = _state.copy(listGalleriesCustomerProfile = listImages)
    }

    private fun getInitialInformationCustomer() {

        _state = _state.copy(
            txtNameUser = "João Silva",
            txtAgeAndEmail = "32 Anos | @joaosilva@gmail.com",
            txtNameTattooArtist = "Larissa Diniz",
            txtTattooArtistProfile = "@lari_tattoo",
            txtScheduleTomorrow = "Amanhã",
            txtScheduleHour = "11:45",
            userImage = "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                "_carousel%2Ftattoo_tesoura.png",
            imageTattooArtist = "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/" +
                "home%2Fthird_carousel%2Favatar%2Favatar_maria_carla.png"
        )
    }

}
