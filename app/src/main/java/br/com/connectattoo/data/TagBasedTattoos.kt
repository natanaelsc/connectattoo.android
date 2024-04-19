package br.com.connectattoo.data

data class TagBasedTattoos(
    val id: Int? = null,
    val imageTattoo: String? = null,
    val tags: List<Tag>? = null
)

data class Tag(
    val id: Int? = null,
    val title: String? = null,
    val background: Boolean = false,
    val titleColor: Int? = null
)
