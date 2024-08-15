package br.com.connectattoo.ui.home.hometattooartist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.connectattoo.data.ListOfTattoosBasedOnTagsAndItemMore
import br.com.connectattoo.data.TagHomeScreen
import br.com.connectattoo.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeTattooArtistViewModel : ViewModel() {

    private var _state: HomeTattooArtistState = HomeTattooArtistState()
    val state: HomeTattooArtistState get() = _state

    private val _uiStateFlow = MutableStateFlow<UiState>(UiState.Success)
    val uiStateFlow: StateFlow<UiState> get() = _uiStateFlow

    private val _listOfTattoosBasedOnTags =
        MutableLiveData<MutableList<ListOfTattoosBasedOnTagsAndItemMore>>(mutableListOf())
    val listOfTattoosBasedOnTags: LiveData<MutableList<ListOfTattoosBasedOnTagsAndItemMore>> =
        _listOfTattoosBasedOnTags

    init {
        getListOfTattoosBasedOnTags()
    }

    fun getListOfTattoosBasedOnTags() {
        val tattooByTagsUrl = mutableListOf(
            "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_tesoura.png",
            "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_cartas.png",
            "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_passaro_na_mao.png",
            "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_calavera.png",
        )
        val listTags1 = nearbyTags1()
        val listTags2 = nearbyTags2()
        val listTags3 = nearbyTags3()
        val listTags4 = nearbyTags4()
        val tattooBasedOnTags1 =
            ListOfTattoosBasedOnTagsAndItemMore.TagBasedOfTattoos(
                id = 1,
                tattooByTagsUrl[0],
                listTags1
            )
        listOfTattoosBasedOnTags.value?.add(tattooBasedOnTags1)

        val tattooBasedOnTags2 =
            ListOfTattoosBasedOnTagsAndItemMore.TagBasedOfTattoos(
                id = 2,
                tattooByTagsUrl[1],
                listTags2
            )
        listOfTattoosBasedOnTags.value?.add(tattooBasedOnTags2)

        val tattooBasedOnTags3 =
            ListOfTattoosBasedOnTagsAndItemMore.TagBasedOfTattoos(
                id = 3,
                tattooByTagsUrl[2],
                listTags3
            )
        listOfTattoosBasedOnTags.value?.add(tattooBasedOnTags3)

        val tattooBasedOnTags4 =
            ListOfTattoosBasedOnTagsAndItemMore.TagBasedOfTattoos(
                id = 4,
                tattooByTagsUrl[3],
                listTags4
            )
        listOfTattoosBasedOnTags.value?.add(tattooBasedOnTags4)
        val tattooBasedOnTags5 =
            ListOfTattoosBasedOnTagsAndItemMore.MoreItems(id = 1, title = "Referências")
        listOfTattoosBasedOnTags.value?.add(tattooBasedOnTags5)
    }

    private fun nearbyTags1(): List<TagHomeScreen> {
        return listOf(
            TagHomeScreen(
                id = 1,
                title = "PB",
                backgroundDeepPurple = false
            ),
            TagHomeScreen(
                id = 2,
                title = "Engraved",
                backgroundDeepPurple = false
            ),
            TagHomeScreen(
                id = 3,
                title = "Surrealism",
                backgroundDeepPurple = true
            )
        )
    }

    private fun nearbyTags2(): List<TagHomeScreen> {
        return listOf(
            TagHomeScreen(
                id = 1,
                title = "Old School",
                backgroundDeepPurple = false
            ),
            TagHomeScreen(
                id = 2,
                title = "Color",
                backgroundDeepPurple = true
            ),
            TagHomeScreen(
                id = 3,
                title = "Classic",
                backgroundDeepPurple = true
            )
        )
    }

    private fun nearbyTags3(): List<TagHomeScreen> {
        return listOf(
            TagHomeScreen(
                id = 3,
                title = "PB",
                backgroundDeepPurple = false
            ),
            TagHomeScreen(
                id = 4,
                title = "Nature",
                backgroundDeepPurple = true
            )
        )
    }

    private fun nearbyTags4(): List<TagHomeScreen> {
        return listOf(
            TagHomeScreen(
                id = 1,
                title = "PB",
                backgroundDeepPurple = false
            ),
            TagHomeScreen(
                id = 2,
                title = "Realism",
                backgroundDeepPurple = false
            ),
            TagHomeScreen(
                id = 3,
                title = "Skull",
                backgroundDeepPurple = true
            )
        )
    }

}
