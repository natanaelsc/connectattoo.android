package br.com.connectattoo.ui.profile.tattooclient

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.ConnectattooApplication
import br.com.connectattoo.R
import br.com.connectattoo.adapter.AdapterListMyGalleries
import br.com.connectattoo.adapter.AdapterListTagsProfile
import br.com.connectattoo.databinding.FragmentTattooClientProfileBinding
import br.com.connectattoo.repository.ProfileRepository
import br.com.connectattoo.ui.BaseFragment
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch


class TattooClientProfileFragment : BaseFragment<FragmentTattooClientProfileBinding>() {
    private lateinit var adapterListTagsProfile: AdapterListTagsProfile
    private var adapterListMyGalleries = AdapterListMyGalleries()
    private val viewModel: TattooClientProfileViewModel by viewModels()
    private lateinit var profileRepository: ProfileRepository
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooClientProfileBinding {
        return FragmentTattooClientProfileBinding.inflate(inflater, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setupViews() {
        val database = (requireActivity().application as ConnectattooApplication).database
        val clientProfileDao = database.tattooClientProfileDao()
        profileRepository = ProfileRepository(clientProfileDao)
        viewModel.getInitialInformationTattooClientProfile(profileRepository)
        setupRecyclerView()
        setupBtnClicks()
        observerViewModel()
    }

    private fun observerViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    when (uiState) {
                        TattooClientProfileViewModel.UiState.Success -> {
                            insertInformationTattooClientProfile()
                        }

                        TattooClientProfileViewModel.UiState.Error -> {
                        }

                        TattooClientProfileViewModel.UiState.Loading -> {

                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        adapterListTagsProfile = AdapterListTagsProfile()
        binding.rvTagsInterests.run {
            setHasFixedSize(true)
            adapter = adapterListTagsProfile
        }
        adapterListTagsProfile.listenerTagProfile = { Tag ->
            Log.i(TAG, Tag.toString())
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
                .placeholder(R.drawable.icon_person_profile)
                .into(btnUserImage)
            Glide.with(ivImageTattooArtist)
                .load(viewModel.state.imageTattooArtist)
                .circleCrop()
                .into(ivImageTattooArtist)
            txtNameUser.text = viewModel.state.txtNameUser
            txtAgeAndName.text = viewModel.state.txtAgeAndName
            txtNameTattooArtist.text = viewModel.state.txtNameTattooArtist
            txtTattoArtistProfile.text = viewModel.state.txtTattooArtistProfile
            txtScheduleTomorrow.text = viewModel.state.txtScheduleTomorrow
            txtScheduleHour.text = viewModel.state.txtScheduleHour

            Glide.with(ivImageTattooArtist).load(viewModel.state.imageTattooArtist).circleCrop()
                .placeholder(R.drawable.icon_person_profile)
                .into(ivImageTattooArtist)

        }
    }

    private fun setupBtnClicks() {
        binding.run {

            btnUserImage.setOnClickListener {
            }
            btnSettings.setOnClickListener {
            }
            btnEditProfile.setOnClickListener {
                findNavController().navigate(R.id.action_clientUserProfileFragment_to_tattooClientEditProfileFragment)
            }
            btnManageInterests.setOnClickListener {
                findNavController().navigate(R.id.action_clientUserProfileFragment_to_tattoClientTagsFilterFragment)
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
