package br.com.connectattoo.ui.loadingscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentLoadingScreenBinding
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.utils.Constants.INTERVAL_TIME_MILLIS_1000
import br.com.connectattoo.utils.Constants.INTERVAL_TIME_MILLIS_10000
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingScreenFragment : BaseFragment<FragmentLoadingScreenBinding>() {
    private val viewModel: LoadingScreenViewModel by viewModels()
    override fun setupViews() {
        viewModel.setLoadingState(true)
        observerLoadingState()
        lifecycleScope.launch {
            delay(INTERVAL_TIME_MILLIS_10000)
            //viewModel.setLoadingState(false)
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoadingScreenBinding {
        return FragmentLoadingScreenBinding.inflate(inflater, container, false)
    }
    private fun observerLoadingState(){
        lifecycleScope.launch {
          viewModel.loadingState.collect { loading ->
                if (loading) {
                    binding.run {
                        while (viewModel.loadingState.value) {
                            delay(INTERVAL_TIME_MILLIS_1000)
                            ivLoading.setImageResource(R.drawable.logo_loading_02)
                            delay(INTERVAL_TIME_MILLIS_1000)
                            ivLoading.setImageResource(R.drawable.logo_loading_03)
                            delay(INTERVAL_TIME_MILLIS_1000)
                            ivLoading.setImageResource(R.drawable.logo_loading_04)
                            delay(INTERVAL_TIME_MILLIS_1000)
                            ivLoading.setImageResource(R.drawable.logo_loading_01)
                        }
                    }
                }
            }
        }
    }
}
