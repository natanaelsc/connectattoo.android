package br.com.connectattoo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.MostRecentConversation
import br.com.connectattoo.databinding.ChatUserItemLayoutBinding
import com.bumptech.glide.Glide

class AdapterListMostRecentConversations :
    androidx.recyclerview.widget.ListAdapter<MostRecentConversation,
        AdapterListMostRecentConversations.ListMostRecentConversationsViewHolder>(
        DiffCallbackListLastConversations()
    ) {
    var listenerChatUser: (MostRecentConversation) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListMostRecentConversationsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChatUserItemLayoutBinding.inflate(inflater, parent, false)

        return ListMostRecentConversationsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListMostRecentConversationsViewHolder, position: Int) {
        holder.bind(getItem(position))

    }

    inner class ListMostRecentConversationsViewHolder(
        private val binding: ChatUserItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mostRecentConversation: MostRecentConversation) {
            binding.run {


                cardUserChat.setOnClickListener {
                    listenerChatUser(mostRecentConversation)
                }
                txtClientName.text = mostRecentConversation.nameClient

                if (mostRecentConversation.numberPendingMessages.isNullOrEmpty()) {
                    amountOfUnreadMessage.visibility = View.GONE
                } else {

                    amountOfUnreadMessage.text = mostRecentConversation.numberPendingMessages
                }

                Glide.with(cardUserChat)
                    .load(mostRecentConversation.photoClient)
                    .circleCrop()
                    .into(ivProfileImage)

            }

        }
    }
}

class DiffCallbackListLastConversations :
    DiffUtil.ItemCallback<MostRecentConversation>() {
    override fun areItemsTheSame(
        oldItem: MostRecentConversation,
        newItem: MostRecentConversation
    ) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: MostRecentConversation,
        newItem: MostRecentConversation
    ) =
        oldItem.nameClient == newItem.nameClient

}
