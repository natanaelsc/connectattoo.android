package br.com.connectattoo.utils

sealed class UiState {
    data object Success : UiState()
    data object Error : UiState()
    data object Loading : UiState()
    data object Initial : UiState()
}
