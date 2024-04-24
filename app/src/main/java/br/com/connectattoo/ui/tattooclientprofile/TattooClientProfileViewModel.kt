package br.com.connectattoo.ui.tattooclientprofile

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.connectattoo.data.MyGalleryProfile
import br.com.connectattoo.data.TagTattooClientProfile
import br.com.connectattoo.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter

class TattooClientProfileViewModel : ViewModel() {

    private var _state: TattooClientProfileState = TattooClientProfileState()
    val state: TattooClientProfileState get() = _state

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> get() = _uiStateFlow

    init {
        getListTagsTattooClientProfile()
        getListGalleriesTattooClientProfile()
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun getInitialInformationTattooClientProfile(userRepository: UserRepository) {
        _uiStateFlow.value = UiState.Loading
        viewModelScope.launch {
            val result = userRepository.getClientProfileRoom()

            result.collect {
                if (it.error != null) {
                    _state = _state.copy(stateError = it.error?.message.toString())
                    _uiStateFlow.value = UiState.Error
                }
                it.data?.let { clientProfile ->

                    if (clientProfile.birthDate != null) {
                        val age = calculateDateBirth(clientProfile.birthDate)

                        _state = _state.copy(txtAgeAndEmail = "$age")
                    }


                    if (clientProfile.imageProfile != null) {
                        _state = _state.copy(userImage = clientProfile.imageProfile)
                    }

                    _state = _state.copy(
                        txtNameUser = "@${clientProfile.username}",
                        txtNameTattooArtist = "Larissa Diniz",
                        txtTattooArtistProfile = "@lari_tattoo",
                        txtScheduleTomorrow = "Amanhã",
                        txtScheduleHour = "11:45",
                        imageTattooArtist = "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/" +
                            "home%2Fthird_carousel%2Favatar%2Favatar_maria_carla.png"
                    )
                    _uiStateFlow.value = UiState.Success
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateDateBirth(birthDate: String): Int {
        val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        val formattedBirthDate = LocalDate.parse(birthDate, format)

        val currentDate = LocalDate.now()

        val age = Period.between(formattedBirthDate, currentDate)

        return age.years
    }

    private fun getImageTattooArtistNextAppointment() {
        _state = _state.copy(
            imageTattooArtist = "https://pub-777ce89a8a3641429d92a32c49eac191.r2" +
                ".dev/tattoo_client_profile%2Favatar%2FLarissa_Dias_tatuadora.png"
        )
    }

    sealed class UiState {
        data object Success : UiState()
        data object Error : UiState()
        data object Loading : UiState()
        data object Initial : UiState()
    }

}
