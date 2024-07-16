package br.com.connectattoo.ui.loadingscreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoadingScreenViewModel : ViewModel() {
    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> get() = _loadingState


    fun setLoadingState(isLoading: Boolean) {
        _loadingState.value = isLoading
    }
}
