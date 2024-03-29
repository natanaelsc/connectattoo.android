package br.com.connectattoo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.NearbyTattooArtists
import br.com.connectattoo.databinding.NearbyTattooArtistsItemBinding
import com.bumptech.glide.Glide

class AdapterListOfNearbyTattooArtists(private val context: Context, private val listOfNearbyTattooArtists: MutableList<NearbyTattooArtists>):
RecyclerView.Adapter<AdapterListOfNearbyTattooArtists.NearbyTattooArtistsViewHolder>(){

    override fun onCreateViewHolder( parent: ViewGroup,viewType: Int): NearbyTattooArtistsViewHolder {
        val itemList = NearbyTattooArtistsItemBinding.inflate(LayoutInflater.from(context), parent,false)
        return NearbyTattooArtistsViewHolder(itemList)
    }

    override fun getItemCount() = listOfNearbyTattooArtists.size

    override fun onBindViewHolder(holder: NearbyTattooArtistsViewHolder, position: Int) {
        Glide.with(context).load(listOfNearbyTattooArtists[position].tattoo).into(holder.tattoo)
        holder.txtName.text = listOfNearbyTattooArtists[position].name
        holder.txtAssessment.text = listOfNearbyTattooArtists[position].assessment
        holder.txtAddress.text = listOfNearbyTattooArtists[position].address
        Glide.with(context).load(listOfNearbyTattooArtists[position].profileImage).into(holder.profileImage)

    }

    inner class NearbyTattooArtistsViewHolder(binding: NearbyTattooArtistsItemBinding): RecyclerView.ViewHolder(binding.root){
        val tattoo = binding.tattoo
        val txtName = binding.txtName
        val txtAssessment = binding.txtassessment
        val txtAddress = binding.txtaddress
        val profileImage = binding.profileImage
    }
}
