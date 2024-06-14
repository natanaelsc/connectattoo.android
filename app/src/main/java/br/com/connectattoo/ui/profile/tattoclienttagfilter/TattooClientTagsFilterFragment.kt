package br.com.connectattoo.ui.profile.tattoclienttagfilter

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.connectattoo.ConnectattooApplication
import br.com.connectattoo.R
import br.com.connectattoo.adapter.AdapterListProfileFilterTags
import br.com.connectattoo.databinding.FragmentTattooClientTagsFilterBinding
import br.com.connectattoo.repository.ProfileRepository
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.utils.Constants
import br.com.connectattoo.utils.DataStoreManager
import br.com.connectattoo.utils.UiState
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


class TattooClientTagsFilterFragment : BaseFragment<FragmentTattooClientTagsFilterBinding>() {
    private lateinit var adapterListTagsProfile: AdapterListProfileFilterTags
    private val viewModel: TattooClientTagsFilterViewModel by viewModels()
    private lateinit var profileRepository: ProfileRepository
    override fun setupViews() {
        getAvailableTags()
        setupRecyclerView()
        viewModelObservers()
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
        viewModel.maximumTagsChecking.observe(viewLifecycleOwner) { checkMaximumTags ->
            if (checkMaximumTags) {
                Snackbar.make(
                    binding.root, getString(
                        R.string.txt_you_cannot_select_more_than_5_tags
                    ), Snackbar.LENGTH_LONG
                )
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.orange)
                    ).show()
            }
        }
        viewModel.listAvailableTags.observe(viewLifecycleOwner){listTags ->
            if (listTags.isNotEmpty()){
                adapterListTagsProfile.submitList(listTags)
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    when (uiState) {
                        UiState.Success -> { Log.i(TAG, "")}

                        UiState.Error -> { Log.i(TAG, "")}

                        UiState.Loading -> { Log.i(TAG, "")}

                        else -> { Log.i(TAG, "")}
                    }
                }
            }
        }
    }

    private fun getAvailableTags() {
        val database = (requireActivity().application as ConnectattooApplication).database
        val clientProfileDao = database.tattooClientProfileDao()
        profileRepository = ProfileRepository(clientProfileDao)
        viewLifecycleOwner.lifecycleScope.launch {
            val token = DataStoreManager.getUserSettings(requireContext(), Constants.API_TOKEN)
            viewModel.getAvailableTags(profileRepository, token)
        }
    }

}
