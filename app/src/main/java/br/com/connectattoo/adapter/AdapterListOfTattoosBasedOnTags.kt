package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.adapter.model.ListTattoosAndTagsHomeScreenRv
import br.com.connectattoo.databinding.ItemMoreHomeScreenBinding
import br.com.connectattoo.databinding.TagbasedtattoosItemBinding
import java.lang.IllegalArgumentException

class AdapterListOfTattoosBasedOnTags : RecyclerView.Adapter<TagBasedTattoosViewHolder>() {

    //var listener: (TagBasedTattoos) -> Unit = {}
    private var list = mutableListOf<ListTattoosAndTagsHomeScreenRv>()
    fun setData(listTattoosAndTags: MutableList<ListTattoosAndTagsHomeScreenRv>){
        list = listTattoosAndTags
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagBasedTattoosViewHolder {

        return when (viewType) {
            TYPE_TAG_BASED_TATTOOS -> TagBasedTattoosViewHolder.TagViewHolder(
                TagbasedtattoosItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
            )

            TYPE_MORE_ITEMS -> TagBasedTattoosViewHolder.MoreItemsIcon(
                ItemMoreHomeScreenBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("")

        }
    }
    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is ListTattoosAndTagsHomeScreenRv.TagBasedTattoos -> TYPE_TAG_BASED_TATTOOS
        is ListTattoosAndTagsHomeScreenRv.MoreItens -> TYPE_MORE_ITEMS

    }
    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TagBasedTattoosViewHolder, position: Int) {
        when (holder) {
            is TagBasedTattoosViewHolder.TagViewHolder -> holder.bind(list[position] as ListTattoosAndTagsHomeScreenRv.TagBasedTattoos)
            is TagBasedTattoosViewHolder.MoreItemsIcon -> holder.bind(list[position] as ListTattoosAndTagsHomeScreenRv.MoreItens)
        }
    }

    companion object {
        const val TYPE_TAG_BASED_TATTOOS = 0
        const val TYPE_MORE_ITEMS = 1
    }
}
