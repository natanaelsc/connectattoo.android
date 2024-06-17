package br.com.connectattoo.ui.profile.tattoclienttagfilter

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.connectattoo.data.Tag
import br.com.connectattoo.repository.ProfileRepository
import br.com.connectattoo.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TattooClientTagsFilterViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> get() = _uiStateFlow

    private val _listTagsSelected: MutableList<Tag> = mutableListOf()

    private val _listAvailableTags = MutableLiveData <List<Tag>>(mutableListOf())
     val listAvailableTags: LiveData <List<Tag>> = _listAvailableTags


    fun selectTag(tag: Tag) {
        _listTagsSelected.let { listTags ->
            if (listTags.contains(tag)) {
                listTags.remove(tag)
            } else if (listTags.size < 5) {
                _listTagsSelected.add(tag)
            }else{
                Log.i(TAG, "")
            }
        }

    }

    fun getAvailableTags(profileRepository: ProfileRepository, token: String) {
        _uiStateFlow.value = UiState.Loading
        viewModelScope.launch {
            val result = profileRepository.getAvailableTags(token)
            if (result.error != null) {
                _uiStateFlow.value = UiState.Error
            }else if (!result.data.isNullOrEmpty()) {
                 result.data.let {
                     _listAvailableTags.value = it
                 }
                _uiStateFlow.value = UiState.Success
            }else{
                _uiStateFlow.value = UiState.Error
            }

        }

    }
}
