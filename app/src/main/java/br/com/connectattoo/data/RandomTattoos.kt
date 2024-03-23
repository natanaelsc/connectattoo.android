package br.com.connectattoo.data

data class RandomTattoos(
    val tattoo: String? = null,
    val name: String? = null,
    val profileImage: String? = null,
    var like: Boolean,
    var save:Boolean
)
