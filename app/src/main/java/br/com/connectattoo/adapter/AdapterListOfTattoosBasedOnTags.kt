package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.adapter.model.ListTattoosAndTagsHomeScreenRv
import br.com.connectattoo.databinding.ItemMoreHomeScreenBinding
import br.com.connectattoo.databinding.TagbasedtattoosItemBinding
import br.com.connectattoo.util.Constants.TYPE_MORE_ITEMS
import br.com.connectattoo.util.Constants.TYPE_TAG_BASED_TATTOOS
import java.lang.IllegalArgumentException

class AdapterListOfTattoosBasedOnTags : RecyclerView.Adapter<TagBasedTattoosViewHolder>() {

    private var list = mutableListOf<ListTattoosAndTagsHomeScreenRv>()
    fun setData(newList: List<ListTattoosAndTagsHomeScreenRv>) {
        val diffResult = DiffUtil.calculateDiff(ListTattoosDiffCallback(list, newList))
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
}

class ListTattoosDiffCallback(
    private val oldList: List<ListTattoosAndTagsHomeScreenRv>,
    private val newList: List<ListTattoosAndTagsHomeScreenRv>
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
