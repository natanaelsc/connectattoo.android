package br.com.connectattoo.ui.profile.tattooclient

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.connectattoo.data.MyGalleryProfile
import br.com.connectattoo.repository.ProfileRepository
import br.com.connectattoo.utils.UiState
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
        getImageTattooArtistNextAppointment()
    }

    private fun getListGalleriesTattooClientProfile() {
        if (!_state.listGalleries.isNullOrEmpty()) {

            _state.listGalleries?.forEach { gallery ->
                if (!gallery.id.isNullOrEmpty()) {
                    val listImagesUrl = getImagesGalleryFake(gallery.id)
                    _state.listGalleriesTattooClientProfile?.add(
                        MyGalleryProfile(
                            gallery.id,
                            title = gallery.name,
                            listImagesUrl[0],
                            listImagesUrl[1],
                            listImagesUrl[2],
                            listImagesUrl[3]
                        )
                    )
                }
            }
        }
    }

    private fun getImagesGalleryFake(id: String): List<String> {
        return when (id) {
            "6524a08f-a04d-4bc2-97f4-c975a329ed29" -> {
                listOf(
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FAnimais%20" +
                        "Cartoon%2FCoelho.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FAnimais%20" +
                        "Cartoon%2FDinossauro_com_oculos.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FAnimais%20" +
                        "Cartoon%2FGirafa.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FAnimais%20" +
                        "Cartoon%2FUrso.png"
                )
            }

            "535c2bc1-46b8-47e2-84ee-ac3dd4cead1f" -> {
                listOf(
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCaveiras%2F" +
                        "Caveira_1.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCaveiras" +
                        "%2FCaveira_2.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCaveiras" +
                        "%2FCaveira_3.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCaveiras" +
                        "%2FCaveira_4.png"
                )
            }

            "d8c7cf42-184f-4d03-906d-6b4daa8bc72a" -> {
                listOf(
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCostas%20" +
                        "Fechadas%2FCostas_Cobra.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCostas%20" +
                        "Fechadas%2FCostas_Diabo.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCostas%20" +
                        "Fechadas%2FCostas_Face.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FCostas%20" +
                        "Fechadas%2FCostas_caveiras.png",
                )
            }

            "5e880889-8093-4b31-82c9-f331ce8fc92e" -> {
                listOf(
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FMãos%2FDuasmãos.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FMãos%2FMão_1.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FMãos%2FMão_coraç-ão.png",
                    "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/galleries%2FMãos%2FMão_olho.png",
                )
            }

            else -> emptyList()
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getInitialInformationTattooClientProfile(profileRepository: ProfileRepository) {
        viewModelScope.launch {
            _uiStateFlow.value = UiState.Loading
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
                            _state.copy(txtAgeAndName = "$age | @${clientProfile.username}")
                    }

                    if (clientProfile.imageProfile != null) {
                        _state = _state.copy(userImage = clientProfile.imageProfile)
                    }

                    _state = _state.copy(
                        txtNameUser = "${clientProfile.name}",
                        listTagsTattooClientProfile = clientProfile.tags,
                        listGalleries = clientProfile.galleries
                    )
                    getListGalleriesTattooClientProfile()
                    _uiStateFlow.value = UiState.Success
                }
            }
         _uiStateFlow.value = UiState.Success
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

}
