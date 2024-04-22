package br.com.connectattoo.data

sealed class RandomTattoosAndItemMore{
    class RandomTattoos(
        val tattoo: String? = null,
        val name: String? = null,
        val profileImage: String? = null,
        var like: Boolean,
        var save:Boolean
    ): RandomTattoosAndItemMore()

    class MoreItems(
        val id: Int? = null,
        val title: String? = null
    ) : RandomTattoosAndItemMore()

}

