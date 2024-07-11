package br.com.connectattoo.data

import java.io.Serializable


sealed class NearbyTattooArtistsAndItemMore : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
    class NearbyTattooArtists(
        val tattoo: String? = null,
        val name: String? = null,
        val assessment: String? = null,
        val address: String? = null,
        val profileImage: String? = null
    ) : NearbyTattooArtistsAndItemMore(), Serializable {
        companion object {
            private const val serialVersionUID: Long = 1L
        }
    }
    class MoreItems(
        val id: Int? = null,
        val title: String? = null
    ) : NearbyTattooArtistsAndItemMore()
}
