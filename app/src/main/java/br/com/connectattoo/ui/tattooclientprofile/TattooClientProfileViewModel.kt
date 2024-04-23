package br.com.connectattoo.ui.tattooclientprofile

import androidx.lifecycle.ViewModel
import br.com.connectattoo.data.MyGalleryProfile
import br.com.connectattoo.data.TagTattooClientProfile

class TattooClientProfileViewModel : ViewModel() {

    private var _state: TattooClientProfileState = TattooClientProfileState()
    val state: TattooClientProfileState get() = _state

    init {
        getInitialInformationCustomer()
        getListGalleriesCustomerProfile()
        getListTagsCustomerProfile()
        getImageTattooArtistNextAppointment()
    }

    private fun getListTagsTattooClientProfile() {
        val listTags = listOf(
            TagTattooClientProfile(tag = "Old School"),
            TagTattooClientProfile(tag = "Preto e branco"),
            TagTattooClientProfile(tag = "Geométrico"),
            TagTattooClientProfile(tag = "Geométrico 2"),
            TagTattooClientProfile(tag = "Geométrico 3")
        )
        _state = _state.copy(listTagsTattooClientProfile = listTags)
    }

    private fun getListGalleriesTattooClientProfile() {
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
        _state = _state.copy(listGalleriesTattooClientProfile = listImages)
    }

    private fun getInitialInformationTattooClientProfile() {

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
    private fun getImageTattooArtistNextAppointment(){
        _state = _state.copy(imageTattooArtist = "https://pub-777ce89a8a3641429d92a32c49eac191.r2" +
            ".dev/tattoo_client_profile%2Favatar%2FLarissa_Dias_tatuadora.png")
    }

}
