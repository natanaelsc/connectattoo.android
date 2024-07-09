package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.Gallery
import br.com.connectattoo.databinding.ItemPhotoProfileTattooArtistDatailsBinding
import com.bumptech.glide.Glide

class AdapterListTattoosTattooArtistDetails :
    ListAdapter<Gallery, AdapterListTattoosTattooArtistDetails.ListTattoosTattooArtistDetailsViewHolder>(
        DiffCallbackListTattoosTattooArtistDetails()
    ) {
    var listenerPhotoTattoo: (Gallery) -> Unit = {}


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListTattoosTattooArtistDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPhotoProfileTattooArtistDatailsBinding.inflate(inflater, parent, false)

        return ListTattoosTattooArtistDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListTattoosTattooArtistDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListTattoosTattooArtistDetailsViewHolder(
        private val binding: ItemPhotoProfileTattooArtistDatailsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(gallery: Gallery) {
            binding.run {

                cardTagBasedTattoos.setOnClickListener {
                    listenerPhotoTattoo(gallery)
                }
                Glide.with(ivArtistPhoto).load(gallery.name) .centerCrop().into(ivArtistPhoto)
            }
        }
    }

}

class DiffCallbackListTattoosTattooArtistDetails : DiffUtil.ItemCallback<Gallery>() {
    override fun areItemsTheSame(oldItem: Gallery, newItem: Gallery) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Gallery, newItem: Gallery) =
        oldItem.id == newItem.id


}
