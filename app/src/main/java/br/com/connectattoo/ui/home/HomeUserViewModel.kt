package br.com.connectattoo.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.connectattoo.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeUserViewModel: ViewModel() {

    private var _state: HomeUserState = HomeUserState()
    val state: HomeUserState get() = _state

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> get() = _uiStateFlow

    fun getClientProfile(userRepository: UserRepository, token: String){

        viewModelScope.launch {
            _uiStateFlow.value = UiState.Loading
            val result = userRepository.getProfileUser(token)

            result.collect {
                if (it.error != null){
                    _state = _state.copy(stateError = it.error?.message.toString())
                    _uiStateFlow.value = UiState.Error
                }
                it.data?.let {clientProfile ->
                    _state = _state.copy(
                        displayName = clientProfile.displayName,
                        username = clientProfile.username,
                        birthDate = clientProfile.birthDate,
                        imageProfile = clientProfile.imageProfile
                    )
                    _uiStateFlow.value = UiState.Success
                }
            }
        }
    }
    sealed class UiState() {
        object Success : UiState()
        object Error : UiState()
        object Loading : UiState()
        object Initial : UiState()
    }
}
