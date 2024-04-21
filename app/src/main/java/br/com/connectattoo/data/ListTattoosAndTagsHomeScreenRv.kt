package br.com.connectattoo.data

sealed class ListTattoosAndTagsHomeScreenRv {
    class MoreItens(
        val id: Int? = null,
        val title: String? = null
    ) : ListTattoosAndTagsHomeScreenRv()

    class TagBasedTattoos(
        val id: Int? = null,
        val imageTattoo: String? = null,
        val tagHomeScreens: List<TagHomeScreen>? = null
    ) : ListTattoosAndTagsHomeScreenRv()
}
