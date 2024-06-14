package br.com.connectattoo.ui.profile.tattoclienttagfilter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.connectattoo.data.Tag
import br.com.connectattoo.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TattooClientTagsFilterViewModel : ViewModel() {

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Initial)
    val uiStateFlow: StateFlow<UiState> get() = _uiStateFlow

    private val _listTagsSelected: MutableList<Tag> = mutableListOf()

    private val _maximumTagsChecking = MutableLiveData(false)
    val maximumTagsChecking: LiveData<Boolean> get() = _maximumTagsChecking


    fun selectTag(tag: Tag) {
        _listTagsSelected.let { listTags ->
            if (listTags.contains(tag)) {
                listTags.remove(tag)
            } else if (listTags.count() <= 4) {
                _listTagsSelected.add(tag)
            } else {
                _maximumTagsChecking.value = true
            }
        }


    }
}
