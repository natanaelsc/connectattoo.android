package br.com.connectattoo.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.connectattoo.repository.UserRepository
import kotlinx.coroutines.launch

class HomeUserViewModel: ViewModel() {

    private var _state: HomeUserState = HomeUserState()
    val state: HomeUserState get() = _state

    fun getClientProfile(userRepository: UserRepository, token: String){

        viewModelScope.launch {
            val result = userRepository.getProfileUser(token)

            result.collect {
                it.data?.let {clientProfile ->
                    _state = _state.copy(
                        displayName = clientProfile.displayName,
                        username = clientProfile.username,
                        birthDate = clientProfile.birthDate,
                        imageProfile = clientProfile.imageProfile
                    )
                }
            }
        }
    }
}
