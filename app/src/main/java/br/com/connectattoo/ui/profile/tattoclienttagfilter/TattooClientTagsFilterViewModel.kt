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
import java.io.IOException

class TattooClientTagsFilterViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> get() = _uiStateFlow

    private val _listTagsSelected: MutableList<Tag> = mutableListOf()
    private val _listTagsClient: MutableList<Tag> = mutableListOf()

    private val _listAvailableTags = MutableLiveData<List<Tag>>(mutableListOf())
    val listAvailableTags: LiveData<List<Tag>> = _listAvailableTags

    private val _message = MutableLiveData<String?>()
    val message: LiveData<String?> = _message


    fun selectTag(tag: Tag) {
        _listTagsSelected.let { listTags ->
            if (listTags.contains(tag)) {
                listTags.remove(tag)
            } else if (listTags.size < 5) {
                _listTagsSelected.add(tag)
            } else {
                Log.i(TAG, "")
            }
        }

    }

    fun saveTagsTattooClient(
        profileRepository: ProfileRepository,
        token: String
    ) {
        try {
            if (_listTagsSelected.isNotEmpty()) {
                viewModelScope.launch {
                    val listIdTags: MutableList<String> = mutableListOf()
                    if (_listTagsSelected.count() < 5) {
                        _listTagsSelected.forEach {
                            it.id?.let { it1 -> listIdTags.add(it1) }
                        }

                        val missingCount = 5 - _listTagsSelected.count()

                        var addedCount = 0
                        for (tag in _listTagsClient) {
                            if (addedCount >= missingCount) break
                            if (!listIdTags.contains(tag.id)) {
                                tag.id?.let { listIdTags.add(it) }
                                addedCount++
                            }
                        }
                    } else {
                        _listTagsSelected.forEach {
                            it.id?.let { it1 -> listIdTags.add(it1) }
                        }
                    }

                    val result =
                        profileRepository.saveTagsTattooClientAndUpdateLocalDb(token, listIdTags)
                    _message.value = result.data

                }
            }
        } catch (error: IOException) {
            _message.value = error.message
        }
    }

    fun getAvailableTags(profileRepository: ProfileRepository, token: String) {
        _uiStateFlow.value = UiState.Loading
        viewModelScope.launch {
            val result = profileRepository.getAvailableTags(token)
            if (result.error != null) {
                _uiStateFlow.value = UiState.Error
            } else if (!result.data.isNullOrEmpty()) {
                result.data.let {
                    _listAvailableTags.value = it
                    it?.forEach { tag ->
                        if (tag.isTagFiltered) {
                            _listTagsSelected.add(tag)
                            _listTagsClient.add(tag)
                        }
                    }

                }
                _uiStateFlow.value = UiState.Success
            } else {
                _uiStateFlow.value = UiState.Error
            }

        }

    }

}
