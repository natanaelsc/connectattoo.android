package br.com.connectattoo.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.connectattoo.data.NearbyTattooArtistsAndItemMore
import br.com.connectattoo.databinding.ItemMoreHomeScreenBinding
import br.com.connectattoo.databinding.NearbyTattooArtistsItemBinding
import com.bumptech.glide.Glide

sealed class NearbyTattooArtistsAndItemMoreViewHolder (binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
        class NearbyTattooArtistsViewHolder(
            private val binding: NearbyTattooArtistsItemBinding
        ): NearbyTattooArtistsAndItemMoreViewHolder(binding){
            fun bind(nearbyTattooArtists: NearbyTattooArtistsAndItemMore.NearbyTattooArtists){
                binding.run {
                    Glide.with(tattoo).load(nearbyTattooArtists.tattoo).into(tattoo)
                    txtName.text = nearbyTattooArtists.name
                    txtassessment.text = nearbyTattooArtists.assessment
                    txtaddress.text = nearbyTattooArtists.address
                    Glide.with(profileImage).load(nearbyTattooArtists.profileImage)
                        .into(profileImage)
                }
            }
        }
    class NearbyTattooArtistsMoreItemsViewHolder(
        private val binding: ItemMoreHomeScreenBinding
    ) : NearbyTattooArtistsAndItemMoreViewHolder(binding) {
        fun bind(moreItems: NearbyTattooArtistsAndItemMore.MoreItems) {

            binding.run {
                cardMoraItems.setOnClickListener {

                }
                txtMoreItensList.text = moreItems.title
            }
        }
    }
}
