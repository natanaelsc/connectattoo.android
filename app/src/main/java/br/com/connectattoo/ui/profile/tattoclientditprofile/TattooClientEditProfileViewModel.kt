package br.com.connectattoo.ui.profile.tattoclientditprofile

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.connectattoo.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class TattooClientEditProfileViewModel : ViewModel() {

    private var _dataState: TattooClientEditProfileDataState = TattooClientEditProfileDataState()
    val dataState: TattooClientEditProfileDataState get() = _dataState

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> get() = _uiStateFlow


    @RequiresApi(Build.VERSION_CODES.O)
    fun getInitialInformationTattooClientProfile(profileRepository: ProfileRepository) {
        _uiStateFlow.value = UiState.Loading
        viewModelScope.launch {
            val result = profileRepository.getClientProfileRoom()

            result.collect {
                if (it.error != null) {
                    _dataState = _dataState.copy(stateError = it.error?.message.toString())
                    _uiStateFlow.value = UiState.Error
                }
                it.data?.let { clientProfile ->
                    val birthDate = transformBirthDate(clientProfile.birthDate)
                    _dataState = _dataState.copy(
                        birthDate = birthDate,
                        imageProfile = clientProfile.imageProfile,
                        displayName = clientProfile.displayName,
                        email = clientProfile.email,
                        username = clientProfile.username
                    )

                    _uiStateFlow.value = UiState.Success
                }
            }
        }

    }

    fun deleteClientProfilePhoto(profileRepository: ProfileRepository, token: String) {

        viewModelScope.launch {
            _uiStateFlow.value = UiState.Loading
            val result = profileRepository.deleteProfilePhoto(token)

            result.collect {
                if (it.error != null) {
                    _dataState =
                        _dataState.copy(stateErrorDeleteImage = it.error?.message.toString())
                    _uiStateFlow.value = UiState.Error
                }
                it.data?.let { message ->
                    _dataState = _dataState.copy(
                        messageDeleteImage = message,
                        imageProfile = ""
                    )
                    _uiStateFlow.value = UiState.Success
                }
            }
        }
    }

    private fun transformBirthDate(birthDate: String?): String? {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())

        return try {
            val date = birthDate?.let { inputFormat.parse(it) }
            outputFormat.format(date ?: "")
        } catch (error: IOException) {
            Log.i(TAG, error.message.toString())
            null
        }
    }

    sealed class UiState {
        data object Success : UiState()
        data object Error : UiState()
        data object Loading : UiState()
        data object Initial : UiState()
    }
}
