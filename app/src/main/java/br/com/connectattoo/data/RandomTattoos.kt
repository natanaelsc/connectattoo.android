package br.com.connectattoo.data

data class RandomTattoos(
    val tatto: String? = null,
    val name: String? = null,
    val profileImage: String? = null,
    var like: Boolean,
    var save:Boolean
)
