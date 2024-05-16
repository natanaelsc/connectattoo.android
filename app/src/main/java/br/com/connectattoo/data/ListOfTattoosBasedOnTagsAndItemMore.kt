package br.com.connectattoo.data

sealed class ListOfTattoosBasedOnTagsAndItemMore {
    class MoreItems(
        val id: Int? = null,
        val title: String? = null
    ) : ListOfTattoosBasedOnTagsAndItemMore()

    class TagBasedOfTattoos(
        val id: Int? = null,
        val imageTattoo: String? = null,
        val tagHomeScreens: List<TagHomeScreen>? = null
    ) : ListOfTattoosBasedOnTagsAndItemMore()
}
