package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.MyGalleryProfile
import br.com.connectattoo.databinding.ItemMyGalleryBinding
import com.bumptech.glide.Glide

class AdapterListMyGalleries :
    androidx.recyclerview.widget.ListAdapter<MyGalleryProfile, AdapterListMyGalleries.ListGalleriesProfileViewHolder>(
        DiffCallbackMyGalleries()
    ) {
    var listenerMyGalleries: (MyGalleryProfile) -> Unit = {}


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListGalleriesProfileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMyGalleryBinding.inflate(inflater, parent, false)

        return ListGalleriesProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListGalleriesProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListGalleriesProfileViewHolder(
        private val binding: ItemMyGalleryBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(myGalleryProfile: MyGalleryProfile) {
            binding.run {

                cardMyGallery.setOnClickListener {
                    listenerMyGalleries(myGalleryProfile)
                }
                txtTitleGallery.text = myGalleryProfile.title
                Glide.with(cardMyGallery)
                    .load(myGalleryProfile.firstImage)
                    .centerCrop()
                    .into(ivMyGallery1)
                Glide.with(cardMyGallery)
                    .load(myGalleryProfile.secondImage)
                    .centerCrop()
                    .into(ivMyGallery2)
                Glide.with(cardMyGallery)
                    .load(myGalleryProfile.thirdImage)
                    .centerCrop()
                    .into(ivMyGallery3)
                Glide.with(cardMyGallery)
                    .load(myGalleryProfile.fourthImage)
                    .centerCrop()
                    .into(ivMyGallery4)

            }

        }
    }
}

class DiffCallbackMyGalleries : DiffUtil.ItemCallback<MyGalleryProfile>() {
    override fun areItemsTheSame(oldItem: MyGalleryProfile, newItem: MyGalleryProfile) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: MyGalleryProfile, newItem: MyGalleryProfile) =
        oldItem.id == newItem.id

}
