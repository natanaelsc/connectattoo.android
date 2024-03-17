package br.com.connectattoo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.connectattoo.data.NearbyTattooArtists
import br.com.connectattoo.databinding.NearbyTattooArtistsItemBinding
import com.bumptech.glide.Glide

class AdapterListOfNearbyTattooartists(private val context: Context, private val listOfNearbyTattooartists: MutableList<NearbyTattooArtists>):
RecyclerView.Adapter<AdapterListOfNearbyTattooartists.NearbyTattooArtistsViewHolder>(){

    override fun onCreateViewHolder( parent: ViewGroup,viewType: Int): NearbyTattooArtistsViewHolder {
        val itemList = NearbyTattooArtistsItemBinding.inflate(LayoutInflater.from(context), parent,false)
        return NearbyTattooArtistsViewHolder(itemList)
    }

    override fun getItemCount() = listOfNearbyTattooartists.size

    override fun onBindViewHolder(holder: NearbyTattooArtistsViewHolder, position: Int) {
        Glide.with(context).load(listOfNearbyTattooartists[position].tatto).into(holder.tatto)
        holder.txtName.text = listOfNearbyTattooartists[position].name
        holder.txtAssessment.text = listOfNearbyTattooartists[position].assessment
        holder.txtAddress.text = listOfNearbyTattooartists[position].address
        Glide.with(context).load(listOfNearbyTattooartists[position].profileImage).into(holder.profileImage)

    }

    inner class NearbyTattooArtistsViewHolder(binding: NearbyTattooArtistsItemBinding): RecyclerView.ViewHolder(binding.root){
        val tatto = binding.tattoo
        val txtName = binding.txtName
        val txtAssessment = binding.txtassessment
        val txtAddress = binding.txtaddress
        val profileImage = binding.profileImage
    }
}
