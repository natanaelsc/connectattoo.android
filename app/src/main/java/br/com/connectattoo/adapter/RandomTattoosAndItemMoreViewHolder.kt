package br.com.connectattoo.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import br.com.connectattoo.R
import br.com.connectattoo.data.RandomTattoosAndItemMore
import br.com.connectattoo.databinding.ItemMoreHomeScreenBinding
import br.com.connectattoo.databinding.RandomTattoosItemBinding
import com.bumptech.glide.Glide

sealed class RandomTattoosAndItemMoreViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class RandomTattoosViewHolder(private val binding: RandomTattoosItemBinding)
        : RandomTattoosAndItemMoreViewHolder(binding){
        fun bind(randomTattoosAndItemMore: RandomTattoosAndItemMore.RandomTattoos){
            binding.run {

                Glide.with(tattoo).load(randomTattoosAndItemMore.tattoo).into(tattoo)

                txtName.text = randomTattoosAndItemMore.name
                Glide.with(profileImage).load(randomTattoosAndItemMore.profileImage)
                    .into(profileImage)

                saveIcon.setOnClickListener {

                    if (randomTattoosAndItemMore.save == false) {
                        saveIcon.setImageResource(R.drawable.save_icon_true2)
                        randomTattoosAndItemMore.save = true
                    } else if (randomTattoosAndItemMore.save == true) {
                        saveIcon.setImageResource(R.drawable.save_icon)
                        randomTattoosAndItemMore.save = false
                    }
                }

                likeIcon.setOnClickListener {

                    if (randomTattoosAndItemMore.like == false) {
                        likeIcon.setImageResource(R.drawable.like_icon_true)
                        randomTattoosAndItemMore.like = true
                    } else if (randomTattoosAndItemMore.like == true) {
                        likeIcon.setImageResource(R.drawable.like_icon)
                        randomTattoosAndItemMore.like = false
                    }
                }
            }
        }
    }

    class RandomTattoosItemMoreViewHolder(
        private val binding: ItemMoreHomeScreenBinding
    ) : RandomTattoosAndItemMoreViewHolder(binding) {
        fun bind(moreItems: RandomTattoosAndItemMore.MoreItems) {

            binding.run {
                cardMoraItems.setOnClickListener {

                }
                txtMoreItensList.text = moreItems.title
            }
        }
    }
}
