package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.NearbyTattooArtistsAndItemMore
import br.com.connectattoo.databinding.ItemMoreHomeScreenBinding
import br.com.connectattoo.databinding.NearbyTattooArtistsItemBinding
import br.com.connectattoo.util.Constants.TYPE_MORE_ITEMS_NEARBY_TATOOO_ARTISTS
import br.com.connectattoo.util.Constants.TYPE_NEARBY_TATOOO_ARTISTS

class AdapterListOfNearbyTattooArtists : RecyclerView.Adapter<NearbyTattooArtistsAndItemMoreViewHolder>() {
    private var list = mutableListOf<NearbyTattooArtistsAndItemMore>()
    fun setData(newList: List<NearbyTattooArtistsAndItemMore>) {
        val diffResult =
            DiffUtil.calculateDiff(ListOfNearbyTattooArtistsDiffCallback(list, newList))
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): NearbyTattooArtistsAndItemMoreViewHolder {
        return when (viewType) {
            TYPE_NEARBY_TATOOO_ARTISTS -> NearbyTattooArtistsAndItemMoreViewHolder.NearbyTattooArtistsViewHolder(
                NearbyTattooArtistsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            TYPE_MORE_ITEMS_NEARBY_TATOOO_ARTISTS -> NearbyTattooArtistsAndItemMoreViewHolder
                .NearbyTattooArtistsMoreItemsViewHolder(ItemMoreHomeScreenBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw java.lang.IllegalArgumentException("")

        }
    }

    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is NearbyTattooArtistsAndItemMore.NearbyTattooArtists -> TYPE_NEARBY_TATOOO_ARTISTS
        is NearbyTattooArtistsAndItemMore.MoreItems -> TYPE_MORE_ITEMS_NEARBY_TATOOO_ARTISTS
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NearbyTattooArtistsAndItemMoreViewHolder, position: Int) {
        when (holder) {
            is NearbyTattooArtistsAndItemMoreViewHolder.NearbyTattooArtistsViewHolder ->
                holder.bind(list[position] as NearbyTattooArtistsAndItemMore.NearbyTattooArtists)

            is NearbyTattooArtistsAndItemMoreViewHolder.NearbyTattooArtistsMoreItemsViewHolder ->
                holder.bind(list[position] as NearbyTattooArtistsAndItemMore.MoreItems)
        }
    }
}

class ListOfNearbyTattooArtistsDiffCallback(
    private val oldList: List<NearbyTattooArtistsAndItemMore>,
    private val newList: List<NearbyTattooArtistsAndItemMore>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
