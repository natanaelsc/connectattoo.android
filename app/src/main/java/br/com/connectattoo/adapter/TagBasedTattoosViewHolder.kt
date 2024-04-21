package br.com.connectattoo.adapter

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.connectattoo.R
import br.com.connectattoo.adapter.model.ListTattoosAndTagsHomeScreenRv
import br.com.connectattoo.data.TagHomeScreen
import br.com.connectattoo.databinding.ItemMoreHomeScreenBinding
import br.com.connectattoo.databinding.TagbasedtattoosItemBinding
import com.bumptech.glide.Glide

sealed class TagBasedTattoosViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {


    class TagViewHolder(
        private val binding: TagbasedtattoosItemBinding,
    ) : TagBasedTattoosViewHolder(binding) {
        fun bind(tagBasedTattoos: ListTattoosAndTagsHomeScreenRv.TagBasedTattoos) {

            binding.run {
                cardTagBasedTattoos.setOnClickListener {

                }
                Glide.with(imageTattoo).load(tagBasedTattoos.imageTattoo).into(imageTattoo)
                setStyleTags(this, tagBasedTattoos)
            }


        }

        private fun setStyleTags(
            binding: TagbasedtattoosItemBinding,
            tagBasedTattoos: ListTattoosAndTagsHomeScreenRv.TagBasedTattoos
        ) {
            val colorPurple900 = "#30045c"
            binding.run {
                tagBasedTattoos.tagHomeScreens?.forEach { tag ->
                    setStyle(tag, colorPurple900)
                }
            }
        }

        private fun TagbasedtattoosItemBinding.setStyle(
            tag: TagHomeScreen,
            colorPurple900: String
        ) {
            if (tag.id == 1) {
                tag1.visibility = View.VISIBLE
                tag1.text = tag.title
                if (tag.backgroundDeepPurple) {
                    tag1.setBackgroundResource(
                        R.drawable.bg_tag_home_circular_purple100
                    )
                    tag1.setTextColor(Color.parseColor(colorPurple900))
                    tag1.visibility = View.VISIBLE
                }
            }
            if (tag.id == 2) {
                tag2.visibility = View.VISIBLE
                tag2.text = tag.title
                if (tag.backgroundDeepPurple) {
                    tag2.setBackgroundResource(
                        R.drawable.bg_tag_home_circular_purple100
                    )
                    tag2.setTextColor(Color.parseColor(colorPurple900))
                }
            }
            if (tag.id == 3) {
                tag3.visibility = View.VISIBLE
                tag3.text = tag.title
                if (tag.backgroundDeepPurple) {

                    tag3.setBackgroundResource(
                        R.drawable.bg_tag_home_circular_purple100
                    )
                    tag3.setTextColor(Color.parseColor(colorPurple900))
                }
            }
            if (tag.id == 4) {
                tag4.visibility = View.VISIBLE
                tag4.text = tag.title
                if (tag.backgroundDeepPurple) {
                    tag4.setBackgroundResource(
                        R.drawable.bg_tag_home_circular_purple100
                    )
                    tag4.setTextColor(Color.parseColor(colorPurple900))
                }
            }
        }
    }

    class MoreItemsIcon(
        private val binding: ItemMoreHomeScreenBinding
    ) : TagBasedTattoosViewHolder(binding) {
        fun bind(moreItems: ListTattoosAndTagsHomeScreenRv.MoreItens) {

            binding.run {
                cardMoraItems.setOnClickListener {
                    //listener(tagBasedTattoos)
                }
                txtMoreItensList.text = moreItems.title
            }
        }
    }
}

