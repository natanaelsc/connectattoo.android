package br.com.connectattoo.ui.home.hometattooartist

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.connectattoo.R
import br.com.connectattoo.adapter.AdapterListOfTattoosBasedOnTags
import br.com.connectattoo.databinding.FragmentHomeTattooArtistBinding
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.utils.UiState
import br.com.connectattoo.utils.hideLoadingFragment
import br.com.connectattoo.utils.showLoadingFragment
import kotlinx.coroutines.launch

@Suppress("TooManyFunctions")
@RequiresApi(Build.VERSION_CODES.M)
class HomeTattooArtistFragment : BaseFragment<FragmentHomeTattooArtistBinding>() {

    private val viewModel: HomeTattooArtistViewModel by viewModels()
    private lateinit var adapterListOfTattoosBasedOnTags: AdapterListOfTattoosBasedOnTags

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeTattooArtistBinding {
        return FragmentHomeTattooArtistBinding.inflate(inflater, container, false)

    }

    override fun setupViews() {
        setRecyclerView()
        observerViewModel()
    }

    private fun setRecyclerView() {
        binding.recycleListOfTattoosBasedOnTags.run {
            setHasFixedSize(true)
            adapterListOfTattoosBasedOnTags = AdapterListOfTattoosBasedOnTags()
            adapter = adapterListOfTattoosBasedOnTags


        }

    }

    private fun showUserName(name: String) {
        if (name.isNotEmpty()) {
            binding.txtName.text = getString(R.string.txt_hello_user_home, name)
        } else {
            binding.txtName.text = getString(R.string.txt_hello_user_home_error)
        }
    }

    private fun observerViewModel() {

        viewModel.listOfTattoosBasedOnTags.observe(viewLifecycleOwner) { listOfTattoosBasedOnTags ->
            if (listOfTattoosBasedOnTags != null) {
                adapterListOfTattoosBasedOnTags.setData(listOfTattoosBasedOnTags)
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    when (uiState) {
                        UiState.Success -> {
                            showUserName("José")
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
    }


}
