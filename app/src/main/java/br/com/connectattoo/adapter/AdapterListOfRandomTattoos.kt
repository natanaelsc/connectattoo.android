package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.RandomTattoosAndItemMore
import br.com.connectattoo.databinding.ItemMoreHomeScreenBinding
import br.com.connectattoo.databinding.RandomTattoosItemBinding
import br.com.connectattoo.utils.Constants


class AdapterListOfRandomTattoos : RecyclerView.Adapter<RandomTattoosAndItemMoreViewHolder>() {
    private var list = mutableListOf<RandomTattoosAndItemMore>()
    fun setData(newList: List<RandomTattoosAndItemMore>) {
        val diffResult = DiffUtil.calculateDiff(ListOfRandomTattoosDiffCallback(list, newList))
        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RandomTattoosAndItemMoreViewHolder {
        return when (viewType) {
            Constants.TYPE_RANDOM_TATTOOS -> RandomTattoosAndItemMoreViewHolder.RandomTattoosViewHolder(
                RandomTattoosItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false
                )
            )

            Constants.TYPE_MORE_ITEMS_RANDOM_TATTOOS -> RandomTattoosAndItemMoreViewHolder
                .RandomTattoosItemMoreViewHolder(ItemMoreHomeScreenBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("")
        }
    }


    override fun onBindViewHolder(holder: RandomTattoosAndItemMoreViewHolder, position: Int) {
        when (holder) {
            is RandomTattoosAndItemMoreViewHolder.RandomTattoosViewHolder -> holder.bind(
                list[position]
                    as RandomTattoosAndItemMore.RandomTattoos
            )

            is RandomTattoosAndItemMoreViewHolder.RandomTattoosItemMoreViewHolder -> holder.bind(
                list[position]
                    as RandomTattoosAndItemMore.MoreItems
            )
        }
    }

    override fun getItemCount() = list.size
    override fun getItemViewType(position: Int): Int = when (list[position]) {
        is RandomTattoosAndItemMore.RandomTattoos -> Constants.TYPE_RANDOM_TATTOOS
        is RandomTattoosAndItemMore.MoreItems -> Constants.TYPE_MORE_ITEMS_RANDOM_TATTOOS

    }

}

class ListOfRandomTattoosDiffCallback(
    private val oldList: List<RandomTattoosAndItemMore>,
    private val newList: List<RandomTattoosAndItemMore>
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

