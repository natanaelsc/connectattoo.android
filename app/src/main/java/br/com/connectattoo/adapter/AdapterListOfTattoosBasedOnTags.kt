package br.com.connectattoo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.TagBasedTattoos
import br.com.connectattoo.databinding.TagbasedtattoosItemBinding
import com.bumptech.glide.Glide

class AdapterListOfTattoosBasedOnTags(private val context:Context, private val listOfTattoosBasedOnTags: MutableList<TagBasedTattoos>):
    RecyclerView.Adapter<AdapterListOfTattoosBasedOnTags.TagBasedTattoosViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagBasedTattoosViewHolder {
        val itemList = TagbasedtattoosItemBinding.inflate(LayoutInflater.from(context), parent,false)
        return TagBasedTattoosViewHolder(itemList)
    }

    override fun getItemCount() = listOfTattoosBasedOnTags.size

    override fun onBindViewHolder(holder: TagBasedTattoosViewHolder, position: Int) {
        Glide.with(context).load(listOfTattoosBasedOnTags[position].tatto).into(holder.imageTatto)
        holder.txtTag.text = listOfTattoosBasedOnTags[position].tags
    }

    inner class TagBasedTattoosViewHolder(binding: TagbasedtattoosItemBinding):RecyclerView.ViewHolder(binding.root){
        val imageTatto = binding.imageTatto
        val txtTag = binding.tag
    }
}
