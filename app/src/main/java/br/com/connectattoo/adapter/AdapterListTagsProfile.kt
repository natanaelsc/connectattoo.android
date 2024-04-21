package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.TagCustomerProfile
import br.com.connectattoo.databinding.ItemTagsMyInterestsBinding

class AdapterListTagsProfile :
    ListAdapter<TagCustomerProfile, AdapterListTagsProfile.ListTagsProfileViewHolder>(
        DiffCallbackTagsProfile()
    ) {
    var listenerTagProfile: (TagCustomerProfile) -> Unit = {}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListTagsProfileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTagsMyInterestsBinding.inflate(inflater, parent, false)

        return ListTagsProfileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListTagsProfileViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListTagsProfileViewHolder(
        private val binding: ItemTagsMyInterestsBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tagCustomerProfile: TagCustomerProfile) {
            binding.run {

                btnTagMyInterests.setOnClickListener {
                    listenerTagProfile(tagCustomerProfile)
                }
                btnTagMyInterests.text = tagCustomerProfile.tag
            }

        }
    }
}

class DiffCallbackTagsProfile : DiffUtil.ItemCallback<TagCustomerProfile>() {
    override fun areItemsTheSame(oldItem: TagCustomerProfile, newItem: TagCustomerProfile) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TagCustomerProfile, newItem: TagCustomerProfile) =
        oldItem.id == newItem.id

}
