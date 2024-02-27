package br.com.connectattoo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.R
import br.com.connectattoo.data.RandomTattoos
import br.com.connectattoo.databinding.RandomTattoosItemBinding
import com.bumptech.glide.Glide


class AdapterListOfRandomTattoos(private val context: Context, private val listOfRandomTattoos: MutableList<RandomTattoos>):
RecyclerView.Adapter<AdapterListOfRandomTattoos.RandomTattoosViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RandomTattoosViewHolder {
        val itemList = RandomTattoosItemBinding.inflate(LayoutInflater.from(context), parent,false)
        return RandomTattoosViewHolder(itemList)
    }

    override fun getItemCount() =  listOfRandomTattoos.size

    override fun onBindViewHolder(holder: RandomTattoosViewHolder, position: Int) {

        val tatto = listOfRandomTattoos[position]

        Glide.with(context).load(listOfRandomTattoos[position].tatto).into(holder.tatto)
        holder.txtName.text = listOfRandomTattoos[position].name
        Glide.with(context).load(listOfRandomTattoos[position].profileImage).into(holder.profileImage)

        holder.save.setOnClickListener {

            if (tatto.save == false) {
                holder.save.setImageResource(R.drawable.save_icon_true2)
                tatto.save = true
            } else if(tatto.save == true) {
                holder.save.setImageResource(R.drawable.save_icon)
                tatto.save  = false
            }
        }

        holder.like.setOnClickListener {

            // Atualize a imagem do íconeCurti com base no estado atual
            if (tatto.like == false) {
                holder.like.setImageResource(R.drawable.like_icon_true)
                tatto.like = true
            } else if (tatto.like == true)  {
                holder.like.setImageResource(R.drawable.like_icon)
                tatto.like = false
            }
        }


    }

    inner class RandomTattoosViewHolder(binding: RandomTattoosItemBinding):RecyclerView.ViewHolder(binding.root){
        val tatto = binding.tatto
        val txtName = binding.txtName
        val profileImage= binding.profileImage
        var like = binding.likeIcon
        var save = binding.saveIcon
    }
}
