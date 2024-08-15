package br.com.connectattoo.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.NextOnYourAgenda
import br.com.connectattoo.databinding.ItemLatestConversationsTattooArtistBinding
import com.bumptech.glide.Glide

class AdapterListNextOnYourAgenda :
    androidx.recyclerview.widget.ListAdapter<NextOnYourAgenda, AdapterListNextOnYourAgenda.ListNextOnYourAgendaViewHolder>(
        DiffCallbackListNextOnYourAgenda()
    ) {
    var listenerNextAgenda: (NextOnYourAgenda) -> Unit = {}
    var listenerBtnMoreInfo: (NextOnYourAgenda) -> Unit = {}


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListNextOnYourAgendaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLatestConversationsTattooArtistBinding.inflate(inflater, parent, false)

        return ListNextOnYourAgendaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListNextOnYourAgendaViewHolder, position: Int) {
        holder.bind(getItem(position), position)

    }

    inner class ListNextOnYourAgendaViewHolder(
        private val binding: ItemLatestConversationsTattooArtistBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nextOnYourAgenda: NextOnYourAgenda, position: Int) {
            binding.run {
                if (position >= 1) {
                    val color = "#ebd7ff"
                    layoutCard.setBackgroundColor(Color.parseColor(color))
                } else {
                    layoutCard.setBackgroundColor(Color.WHITE)
                }

                cardTattooArtist.setOnClickListener {
                    listenerNextAgenda(nextOnYourAgenda)
                }
                txtTitleAgenda.text = nextOnYourAgenda.title
                txtScheduleTomorrow.text = nextOnYourAgenda.date
                txtScheduleHour.text = nextOnYourAgenda.time
                txtNameClient.text = nextOnYourAgenda.clientName
                txtClientAt.text = nextOnYourAgenda.clientAt
                btnMoreInfoNextScheduling.setOnClickListener {
                    listenerBtnMoreInfo(nextOnYourAgenda)
                }

                Glide.with(cardTattooArtist)
                    .load(nextOnYourAgenda.clientPhoto)
                    .circleCrop()
                    .into(ivImageTattooArtist)

            }

        }
    }
}

class DiffCallbackListNextOnYourAgenda : DiffUtil.ItemCallback<NextOnYourAgenda>() {
    override fun areItemsTheSame(oldItem: NextOnYourAgenda, newItem: NextOnYourAgenda) =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: NextOnYourAgenda, newItem: NextOnYourAgenda) =
        oldItem.clientName == newItem.clientName

}
