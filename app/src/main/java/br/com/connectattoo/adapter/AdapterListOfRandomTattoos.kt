package br.com.connectattoo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.R
import br.com.connectattoo.data.RandomTattoos
import br.com.connectattoo.databinding.RandomTattoosItemBinding
import com.bumptech.glide.Glide


class AdapterListOfRandomTattoos(
    private val context: Context, private val listOfRandomTattoos:
    MutableList<RandomTattoos>
) :
    RecyclerView.Adapter<AdapterListOfRandomTattoos.RandomTattoosViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomTattoosViewHolder {
        val itemList = RandomTattoosItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return RandomTattoosViewHolder(itemList)
    }

    override fun getItemCount() = listOfRandomTattoos.size

    override fun onBindViewHolder(holder: RandomTattoosViewHolder, position: Int) {

        val tattoo = listOfRandomTattoos[position]

        Glide.with(context).load(listOfRandomTattoos[position].tattoo).into(holder.tattoo)
        holder.txtName.text = listOfRandomTattoos[position].name
        Glide.with(context).load(listOfRandomTattoos[position].profileImage)
            .into(holder.profileImage)

        holder.save.setOnClickListener {

            if (tattoo.save == false) {
                holder.save.setImageResource(R.drawable.save_icon_true2)
                tattoo.save = true
            } else if (tattoo.save == true) {
                holder.save.setImageResource(R.drawable.save_icon)
                tattoo.save = false
            }
        }

        holder.like.setOnClickListener {

            // Atualize a imagem do íconeCurti com base no estado atual
            if (tattoo.like == false) {
                holder.like.setImageResource(R.drawable.like_icon_true)
                tattoo.like = true
            } else if (tattoo.like == true) {
                holder.like.setImageResource(R.drawable.like_icon)
                tattoo.like = false
            }
        }


    }

    inner class RandomTattoosViewHolder(binding: RandomTattoosItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tattoo = binding.tattoo
        val txtName = binding.txtName
        val profileImage = binding.profileImage
        var like = binding.likeIcon
        var save = binding.saveIcon
    }
}
