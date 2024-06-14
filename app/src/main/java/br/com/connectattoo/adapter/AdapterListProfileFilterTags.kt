package br.com.connectattoo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.Tag
import br.com.connectattoo.databinding.ItemTagsMyInterestsBinding

class AdapterListProfileFilterTags :
    ListAdapter<Tag, AdapterListProfileFilterTags.ListTagsProfileViewHolder>(
        DiffCallbackListProfileFilterTags()
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
        private var isSelected = false
        fun bind(tag: Tag) {
            binding.run {

                btnTagMyInterests.setOnClickListener {
                    if (isSelected) {
                        btnTagMyInterests.setBackgroundColor(Color.parseColor("#ebd7ff"))
                        btnTagMyInterests.setTextColor(Color.parseColor("#460d7d"))
                        isSelected = false
                        selectedTags.remove(tag)
                    } else {
                        if (selectedTags.size < 5) {
                            btnTagMyInterests.setBackgroundColor(Color.parseColor("#7A32C1"))
                            btnTagMyInterests.setTextColor(Color.WHITE)
                            isSelected = true
                            selectedTags.add(tag)
                        }
                    }
                    listenerTagProfile(tag)
                }
                btnTagMyInterests.text = tag.name
            }
        }
    }

    companion object {
        private val selectedTags = mutableListOf<Tag>()
    }
}

class DiffCallbackListProfileFilterTags : DiffUtil.ItemCallback<Tag>() {
    override fun areItemsTheSame(oldItem: Tag, newItem: Tag) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Tag, newItem: Tag) =
        oldItem.id == newItem.id

}
