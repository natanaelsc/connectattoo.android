package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.TagTattooClientProfile
import br.com.connectattoo.databinding.ItemTagsMyInterestsBinding

class AdapterListTagsProfile :
    androidx.recyclerview.widget.ListAdapter<TagTattooClientProfile, AdapterListTagsProfile.ListTagsProfileViewHolder>(
        DiffCallbackTagsProfile()
    ) {
    var listenerTagProfile: (TagTattooClientProfile) -> Unit = {}


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
        fun bind(tagTattooClientProfile: TagTattooClientProfile) {
            binding.run {

                btnTagMyInterests.setOnClickListener {
                    listenerTagProfile(tagTattooClientProfile)
                }
                btnTagMyInterests.text = tagTattooClientProfile.tag
            }

        }
    }
}

class DiffCallbackTagsProfile : DiffUtil.ItemCallback<TagTattooClientProfile>() {
    override fun areItemsTheSame(oldItem: TagTattooClientProfile, newItem: TagTattooClientProfile) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TagTattooClientProfile, newItem: TagTattooClientProfile) =
        oldItem.id == newItem.id

}
