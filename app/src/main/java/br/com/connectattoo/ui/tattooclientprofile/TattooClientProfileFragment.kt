package br.com.connectattoo.ui.tattooclientprofile

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.connectattoo.adapter.AdapterListMyGalleries
import br.com.connectattoo.adapter.AdapterListTagsProfile
import br.com.connectattoo.databinding.FragmentTattooClientProfileBinding
import br.com.connectattoo.ui.BaseFragment
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch


class TattooClientProfileFragment : BaseFragment<FragmentTattooClientProfileBinding>() {
    private lateinit var adapterListTagsProfile: AdapterListTagsProfile
    private var adapterListMyGalleries = AdapterListMyGalleries()
    private val viewModel: TattooClientProfileViewModel by viewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooClientProfileBinding {
        return FragmentTattooClientProfileBinding.inflate(inflater, container, false)
    }

    override fun setupViews() {
        setupRecyclerView()
        insertInformationTattooClientProfile()
        setupBtnClicks()
    }

    private fun setupRecyclerView() {
        adapterListTagsProfile = AdapterListTagsProfile()
        binding.rvTagsInterests.run {
            setHasFixedSize(true)
            adapter = adapterListTagsProfile
        }
        adapterListTagsProfile.listenerTagProfile = { tagTattooClientProfile ->
            Log.i(TAG, tagTattooClientProfile.toString())
        }
        binding.rvMyGalleries.run {
            setHasFixedSize(true)
            adapter = adapterListMyGalleries
        }

    }

    private fun insertInformationTattooClientProfile() {
        viewLifecycleOwner.lifecycleScope.launch {
            val listTagsProfile = viewModel.state.listTagsTattooClientProfile

            adapterListTagsProfile.submitList(listTagsProfile)

        }
        viewLifecycleOwner.lifecycleScope.launch {
            val listMyGalleries = viewModel.state.listGalleriesTattooClientProfile
            adapterListMyGalleries.submitList(listMyGalleries)

        }

        binding.run {
            Glide.with(btnUserImage)
                .load(viewModel.state.userImage)
                .circleCrop()
                .into(btnUserImage)
            Glide.with(ivImageTattooArtist)
                .load(viewModel.state.imageTattooArtist)
                .circleCrop()
                .into(ivImageTattooArtist)
            txtNameUser.text = viewModel.state.txtNameUser
            txtAgeAndEmail.text = viewModel.state.txtAgeAndEmail
            txtNameTattooArtist.text = viewModel.state.txtNameTattooArtist
            txtTattoArtistProfile.text = viewModel.state.txtTattooArtistProfile
            txtScheduleTomorrow.text = viewModel.state.txtScheduleTomorrow
            txtScheduleHour.text = viewModel.state.txtScheduleHour
        }
    }

    private fun setupBtnClicks() {
        binding.run {

            btnUserImage.setOnClickListener {
            }
            btnSettings.setOnClickListener {
            }
            btnEditProfile.setOnClickListener {
            }
            btnManageInterests.setOnClickListener {
            }
            btnManageNextAppointment.setOnClickListener {
            }
            btnMoreInfoNextScheduling.setOnClickListener {
            }
            btnManageGallery.setOnClickListener {
            }
        }

    }

}
