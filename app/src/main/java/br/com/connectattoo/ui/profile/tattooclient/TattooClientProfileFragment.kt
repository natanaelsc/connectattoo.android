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
import br.com.connectattoo.utils.Constants
import br.com.connectattoo.utils.UiState
import br.com.connectattoo.utils.hideLoadingFragment
import br.com.connectattoo.utils.showLoadingFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
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


    override fun setupViews() {
        setupRecyclerView()
        getInitialInformation()
        observerViewModel()
        setupBtnClicks()
    }

    private fun getInitialInformation() {
        val database = (requireActivity().application as ConnectattooApplication).database
        val clientProfileDao = database.tattooClientProfileDao()
        profileRepository = ProfileRepository(clientProfileDao)
        viewLifecycleOwner.lifecycleScope.launch {
            val startTime = System.currentTimeMillis()
            viewModel.getInitialInformationTattooClientProfile(profileRepository)
            val endTime = System.currentTimeMillis()
            val durationMs = endTime - startTime

            if (durationMs > 1) {
                showLoadingFragment(binding.root, R.id.nav_user_fragment)
                delay(Constants.INTERVAL_TIME_MILLIS_1500)
            }
        }

    }


    private fun observerViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    when (uiState) {
                        UiState.Success -> {
                            hideLoadingFragment(binding.root)
                            insertInformationTattooClientProfile()
                        }

                        UiState.Error -> {
                            hideLoadingFragment(binding.root)
                        }

                        UiState.Loading -> {
                        }

                        else -> {
                            hideLoadingFragment(binding.root)
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
        adapterListTagsProfile.listenerTagProfile = { tag ->
            Log.i(TAG, tag.toString())
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
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(btnUserImage)
            Glide.with(ivImageTattooArtist)
                .load(viewModel.state.imageTattooArtist)
                .circleCrop()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivImageTattooArtist)
            txtNameUser.text = viewModel.state.txtNameUser
            txtAgeAndName.text = viewModel.state.txtAgeAndName
            txtNameTattooArtist.text = viewModel.state.txtNameTattooArtist
            txtTattoArtistProfile.text = viewModel.state.txtTattooArtistProfile
            txtScheduleTomorrow.text = viewModel.state.txtScheduleTomorrow
            txtScheduleHour.text = viewModel.state.txtScheduleHour

            Glide.with(ivImageTattooArtist).load(viewModel.state.imageTattooArtist).circleCrop()
                .placeholder(R.drawable.icon_person_profile)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(ivImageTattooArtist)

        }
    }

    private fun setupBtnClicks() {
        binding.run {

            btnUserImage.setOnClickListener {
            }
            btnSettings.setOnClickListener {
                findNavController().navigate(R.id.action_clientUserProfileFragment_to_tattooClientConfigurationFragment)
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
