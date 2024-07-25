package br.com.connectattoo.ui.profile.tattoclienttagfilter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.ConnectattooApplication
import br.com.connectattoo.R
import br.com.connectattoo.adapter.AdapterListProfileFilterTags
import br.com.connectattoo.databinding.FragmentTattooClientTagsFilterBinding
import br.com.connectattoo.local.database.AppDatabase
import br.com.connectattoo.repository.ProfileRepository
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.utils.Constants
import br.com.connectattoo.utils.DataStoreManager
import br.com.connectattoo.utils.UiState
import br.com.connectattoo.utils.hideLoadingFragment
import br.com.connectattoo.utils.showLoadingFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import kotlinx.coroutines.launch


class TattooClientTagsFilterFragment : BaseFragment<FragmentTattooClientTagsFilterBinding>() {
    private lateinit var adapterListTagsProfile: AdapterListProfileFilterTags
    private val viewModel: TattooClientTagsFilterViewModel by viewModels()
    private lateinit var profileRepository: ProfileRepository
    private lateinit var database: AppDatabase

    override fun setupViews() {
        database = (requireActivity().application as ConnectattooApplication).database
        val clientProfileDao = database.tattooClientProfileDao()
        profileRepository = ProfileRepository(clientProfileDao)
        viewModelObservers()

        setupListeners()
        getAvailableTags()
        setupRecyclerView()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooClientTagsFilterBinding {
        return FragmentTattooClientTagsFilterBinding.inflate(inflater, container, false)
    }

    private fun setupRecyclerView() {
        adapterListTagsProfile = AdapterListProfileFilterTags()

        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }

        binding.rvTagsInterests.apply {
            setHasFixedSize(true)
            this.layoutManager = layoutManager
            adapter = adapterListTagsProfile
        }

        adapterListTagsProfile.listenerTagProfile = { tag ->
            viewModel.selectTag(tag)
        }

    }

    private fun viewModelObservers() {

        viewModel.listAvailableTags.observe(viewLifecycleOwner) { listTags ->
            if (listTags.isNotEmpty()) {
                adapterListTagsProfile.submitList(listTags)
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    when (uiState) {
                        UiState.Success -> {
                            hideLoadingFragment(binding.root)
                        }

                        UiState.Error -> {
                            hideLoadingFragment(binding.root)
                        }

                        UiState.Loading -> {
                            showLoadingFragment(binding.root, R.id.nav_user_fragment)
                        }

                        else -> {
                            hideLoadingFragment(binding.root)
                        }
                    }
                }
            }
        }
        viewModel.message.observe(viewLifecycleOwner) { message ->
            if (message == "Sucesso") {
                findNavController().popBackStack()
            }
        }
    }

    private fun getAvailableTags() {
        viewLifecycleOwner.lifecycleScope.launch {
            val token = DataStoreManager.getUserSettings(requireContext(), Constants.API_TOKEN)
            viewModel.getAvailableTags(profileRepository, token)
        }
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.btnFilter.setOnClickListener {
            saveTagsTattooClient()
        }
    }

    private fun saveTagsTattooClient() {
        viewLifecycleOwner.lifecycleScope.launch {
            val token = DataStoreManager.getUserSettings(requireContext(), Constants.API_TOKEN)
            viewModel.saveTagsTattooClient(profileRepository, token = token)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        adapterListTagsProfile.clearTags()
    }
}
