package br.com.connectattoo.adapter

import android.graphics.Color
import android.view.LayoutInflater
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

        val colorWhite = "#FFFFFFFF"
        val colorPurple900 = "#30045c"
        fun bind(tagBasedTattoos: TagBasedTattoos) {

            binding.run {
                cardTagBasedTattoos.setOnClickListener {
                    listener(tagBasedTattoos)
                }
                Glide.with(imageTattoo).load(tagBasedTattoos.imageTattoo).into(imageTattoo)

                tagBasedTattoos.tags?.get(0).let { tag ->
                    if (tag != null) {
                        tag1.text = tag.title
                        if (tag.background) {
                            tag1.setBackgroundResource(R.drawable.bg_butao_criar_conta)
                            tag1.setTextColor(Color.parseColor(colorWhite))
                        } else {
                            tag1.setBackgroundResource(
                                R.drawable.bg_butao_criar_conta
                            )
                            tag1.setTextColor(Color.parseColor(colorPurple900))
                        }
                    }
                }
                tagBasedTattoos.tags?.get(1).let { tag ->
                    if (tag != null) {
                        tag2.text = tag.title
                        if (tag.background) {
                            tag2.setBackgroundResource(R.drawable.bg_butao_criar_conta)
                            tag2.setTextColor(Color.parseColor(colorWhite))
                        } else tag2.setBackgroundResource(
                            R.drawable.bg_tag_home_circular_purple100
                        )
                        tag1.setTextColor(Color.parseColor(colorPurple900))
                    }
                }
                tagBasedTattoos.tags?.get(2).let { tag ->
                    if (tag != null) {
                        tag3.text = tag.title
                        if (tag.background) {
                            tag3.setBackgroundResource(R.drawable.bg_butao_criar_conta)
                            tag3.setTextColor(Color.parseColor(colorWhite))
                        } else tag3.setBackgroundResource(
                            R.drawable.bg_tag_home_circular_purple100
                        )
                        tag1.setTextColor(Color.parseColor(colorPurple900))
                    }
                }
                tagBasedTattoos.tags?.get(3).let { tag ->
                    if (tag != null) {
                        tag4.text = tag.title
                        if (tag.background) {
                            tag4.setBackgroundResource(R.drawable.bg_butao_criar_conta)
                            tag2.setTextColor(Color.parseColor(colorWhite))
                        } else tag4.setBackgroundResource(
                            R.drawable.bg_tag_home_circular_purple100
                        )
                        tag1.setTextColor(Color.parseColor(colorWhite))
                    }
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
