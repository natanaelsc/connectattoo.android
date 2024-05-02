package br.com.connectattoo.ui.profile.tattooclient

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.connectattoo.data.MyGalleryProfile
import br.com.connectattoo.repository.ProfileRepository
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
        getListGalleriesTattooClientProfile()
        getImageTattooArtistNextAppointment()
    }

    private fun getListGalleriesTattooClientProfile() {
        val listImages = listOf(
            MyGalleryProfile(
                1,
                title = "Animais Cartoon",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FAnimais%20" +
                    "Cartoon%2FCoelho.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FAnimais%20" +
                    "Cartoon%2FDinossauro_com_oculos.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FAnimais%20" +
                    "Cartoon%2FGirafa.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FAnimais%20" +
                    "Cartoon%2FUrso.png",

                ),
            MyGalleryProfile(
                2,
                title = "Caveiras",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCaveiras%2F" +
                    "Caveira_1.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCaveiras" +
                    "%2FCaveira_2.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCaveiras" +
                    "%2FCaveira_3.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCaveiras" +
                    "%2FCaveira_4.png"

            ),
            MyGalleryProfile(
                3,
                title = "Costas Fechadas",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCostas%20" +
                    "Fechadas%2FCostas_Cobra.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCostas%20" +
                    "Fechadas%2FCostas_Diabo.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCostas%20" +
                    "Fechadas%2FCostas_Face.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCostas%20" +
                    "Fechadas%2FCostas_caveiras.png",

                ),
            MyGalleryProfile(
                4,
                title = "Mãos",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FMãos%2FDuasmãos.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FMãos%2FMão_1.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FMãos%2FMão_coraç-ão.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FMãos%2FMão_olho.png",

                ),
        )
        _state = _state.copy(listGalleriesTattooClientProfile = listImages)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getInitialInformationTattooClientProfile(profileRepository: ProfileRepository) {
        _uiStateFlow.value = UiState.Loading
        viewModelScope.launch {
            val result = profileRepository.getClientProfileRoom()

            result.collect {
                if (it.error != null) {
                    _state = _state.copy(stateError = it.error?.message.toString())
                    _uiStateFlow.value = UiState.Error
                }
                it.data?.let { clientProfile ->

                    if (clientProfile.birthDate != null) {
                        val age = calculateDateBirth(clientProfile.birthDate)
                        _state =
                            _state.copy(txtAgeAndDisplayName = "$age | @${clientProfile.username}")
                    }

                    if (clientProfile.imageProfile != null) {
                        _state = _state.copy(userImage = clientProfile.imageProfile)
                    }

                    _state = _state.copy(
                        txtNameUser = "${clientProfile.displayName}",
                        listTagsTattooClientProfile = clientProfile.tags
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
            txtNameTattooArtist = "Larissa Diniz",
            txtTattooArtistProfile = "@lari_tattoo",
            txtScheduleTomorrow = "Amanhã",
            txtScheduleHour = "11:45",
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
