package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.Tag
import br.com.connectattoo.databinding.ItemTagsMyInterestsBinding

class AdapterListTagsProfile :
    ListAdapter<Tag, AdapterListTagsProfile.ListTagsProfileViewHolder>(
        DiffCallbackTagsProfile()
    ) {
    var listenerTagProfile: (Tag) -> Unit = {}


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
        fun bind(tag: Tag) {
            binding.run {

                btnTagMyInterests.setOnClickListener {
                    listenerTagProfile(tag)
                }
                btnTagMyInterests.text = tag.name
            }

        }
    }
}

class DiffCallbackTagsProfile : DiffUtil.ItemCallback<Tag>() {
    override fun areItemsTheSame(oldItem: Tag, newItem: Tag) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Tag, newItem: Tag) =
        oldItem.id == newItem.id

}
