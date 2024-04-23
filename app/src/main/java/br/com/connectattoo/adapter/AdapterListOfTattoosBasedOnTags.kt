package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.ListOfTattoosBasedOnTagsAndItemMore
import br.com.connectattoo.databinding.ItemMoreHomeScreenBinding
import br.com.connectattoo.databinding.TagbasedtattoosItemBinding
import br.com.connectattoo.util.Constants.TYPE_MORE_ITEMS_TAG_BASED_TATTOOS
import br.com.connectattoo.util.Constants.TYPE_TAG_BASED_TATTOOS
import java.lang.IllegalArgumentException

class AdapterListOfTattoosBasedOnTags : RecyclerView.Adapter<TagBasedTattoosViewHolder>() {

    private var list = mutableListOf<ListOfTattoosBasedOnTagsAndItemMore>()
    fun setData(newList: List<ListOfTattoosBasedOnTagsAndItemMore>) {
        val diffResult = DiffUtil.calculateDiff(ListOfTattoosBasedOnTagsDiffCallback(list, newList))
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagBasedTattoosViewHolder {

        return when (viewType) {
            TYPE_TAG_BASED_TATTOOS -> TagBasedTattoosViewHolder.TagViewHolder(
                TagbasedtattoosItemBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
            )

            TYPE_MORE_ITEMS_TAG_BASED_TATTOOS -> TagBasedTattoosViewHolder.MoreItemsIcon(
                ItemMoreHomeScreenBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("")

        }
    }
    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is ListOfTattoosBasedOnTagsAndItemMore.TagBasedOfTattoos -> TYPE_TAG_BASED_TATTOOS
        is ListOfTattoosBasedOnTagsAndItemMore.MoreItems -> TYPE_MORE_ITEMS_TAG_BASED_TATTOOS

    }
    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TagBasedTattoosViewHolder, position: Int) {
        when (holder) {
            is TagBasedTattoosViewHolder.TagViewHolder -> holder.bind(list[position]
                as ListOfTattoosBasedOnTagsAndItemMore.TagBasedOfTattoos)
            is TagBasedTattoosViewHolder.MoreItemsIcon -> holder.bind(list[position]
                as ListOfTattoosBasedOnTagsAndItemMore.MoreItems)

        }
    }
}

class ListOfTattoosBasedOnTagsDiffCallback(
    private val oldList: List<ListOfTattoosBasedOnTagsAndItemMore>,
    private val newList: List<ListOfTattoosBasedOnTagsAndItemMore>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
