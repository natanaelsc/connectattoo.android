package br.com.connectattoo.data


sealed class NearbyTattooArtistsAndItemMore{
    class NearbyTattooArtists(
        val tattoo: String? = null,
        val name: String? = null,
        val assessment: String? = null,
        val address: String? = null,
        val profileImage: String? = null
    ) : NearbyTattooArtistsAndItemMore()
    class MoreItems(
        val id: Int? = null,
        val title: String? = null
    ) : NearbyTattooArtistsAndItemMore()
}
