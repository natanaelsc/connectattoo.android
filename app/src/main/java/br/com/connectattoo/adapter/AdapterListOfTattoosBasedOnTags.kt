package br.com.connectattoo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.R
import br.com.connectattoo.data.TagBasedTattoos
import br.com.connectattoo.databinding.TagbasedtattoosItemBinding
import com.bumptech.glide.Glide

class AdapterListOfTattoosBasedOnTags :
    ListAdapter<TagBasedTattoos, AdapterListOfTattoosBasedOnTags.TagBasedTattoosViewHolder>(
        DiffCallbackListOfTattoosBasedOnTags()
    ) {

    var listener: (TagBasedTattoos) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagBasedTattoosViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TagbasedtattoosItemBinding.inflate(inflater, parent, false)
        return TagBasedTattoosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TagBasedTattoosViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TagBasedTattoosViewHolder(private val binding: TagbasedtattoosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(tagBasedTattoos: TagBasedTattoos) {

            binding.run {
                cardTagBasedTattoos.setOnClickListener {
                    listener(tagBasedTattoos)
                }
                Glide.with(imageTattoo).load(tagBasedTattoos.imageTattoo).into(imageTattoo)
                setStyleTags(this, tagBasedTattoos)
            }


        }

    }
}

private fun setStyleTags(binding: TagbasedtattoosItemBinding, tagBasedTattoos: TagBasedTattoos) {
    val colorPurple900 = "#30045c"
    binding.run {
        tagBasedTattoos.tags?.forEach { tag ->
            if (tag.id == 1) {
                tag1.text = tag.title
                if (tag.backgroundDeepPurple) {
                    tag1.visibility = View.VISIBLE
                } else {
                    tag1.setBackgroundResource(
                        R.drawable.bg_tag_home_circular_purple100
                    )
                    tag1.setTextColor(Color.parseColor(colorPurple900))
                    tag1.visibility = View.VISIBLE
                }
            }
            if (tag.id == 2) {
                tag2.text = tag.title
                if (tag.backgroundDeepPurple) {
                    tag2.visibility = View.VISIBLE
                } else {
                    tag2.setBackgroundResource(
                        R.drawable.bg_tag_home_circular_purple100
                    )
                    tag2.setTextColor(Color.parseColor(colorPurple900))
                    tag2.visibility = View.VISIBLE
                }
            }
            if (tag.id == 3) {
                tag3.text = tag.title
                if (tag.backgroundDeepPurple) {
                    tag3.visibility = View.VISIBLE
                } else {
                    tag3.setBackgroundResource(
                        R.drawable.bg_tag_home_circular_purple100
                    )
                    tag3.setTextColor(Color.parseColor(colorPurple900))
                    tag3.visibility = View.VISIBLE
                }
            }
            if (tag.id == 4) {
                tag4.text = tag.title
                if (tag.backgroundDeepPurple) {
                    tag4.visibility = View.VISIBLE
                } else {
                    tag4.setBackgroundResource(
                        R.drawable.bg_tag_home_circular_purple100
                    )
                    tag4.setTextColor(Color.parseColor(colorPurple900))
                    tag4.visibility = View.VISIBLE
                }
            }
        }
    }

}

class DiffCallbackListOfTattoosBasedOnTags : DiffUtil.ItemCallback<TagBasedTattoos>() {
    override fun areItemsTheSame(oldItem: TagBasedTattoos, newItem: TagBasedTattoos) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: TagBasedTattoos, newItem: TagBasedTattoos) =
        oldItem.id == newItem.id

}
