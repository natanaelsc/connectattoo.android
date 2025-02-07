package br.com.connectattoo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.connectattoo.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeUserViewModel : ViewModel() {

    private var _state: HomeUserState = HomeUserState()
    val state: HomeUserState get() = _state

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> get() = _uiStateFlow

    fun getClientProfile(profileRepository: ProfileRepository, token: String) {

        viewModelScope.launch {
            _uiStateFlow.value = UiState.Loading
            val result = profileRepository.getProfileUser(token)

            result.collect {
                if (it.error != null) {
                    _state = _state.copy(stateError = it.error?.message.toString())
                    _uiStateFlow.value = UiState.Error
                }
                it.data?.let { clientProfile ->
                    val firstName = clientProfile.name?.split(" ")?.get(0)
                    _state = _state.copy(
                        name = firstName,
                        username = clientProfile.username,
                        birthDate = clientProfile.birthDate,
                        imageProfile = clientProfile.imageProfile,
                        tags = clientProfile.tags,
                        email = clientProfile.email
                    )
                    _uiStateFlow.value = UiState.Success
                }
            }
        }
    }

    sealed class UiState {
        data object Success : UiState()
        data object Error : UiState()
        data object Loading : UiState()
        data object Initial : UiState()
    }
}
