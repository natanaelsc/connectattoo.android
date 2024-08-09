package br.com.connectattoo.ui.loadingscreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentLoadingScreenBinding
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.utils.Constants.INTERVAL_TIME_MILLIS_1000
import br.com.connectattoo.utils.Constants.INTERVAL_TIME_MILLIS_10000
import br.com.connectattoo.utils.Constants.INTERVAL_TIME_MILLIS_5000
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class LoadingScreenFragment : BaseFragment<FragmentLoadingScreenBinding>() {
    private val viewModel: LoadingScreenViewModel by viewModels()
    override fun setupViews() {
        viewModel.setLoadingState(true)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    sortRandomPhrase()
                    delay(INTERVAL_TIME_MILLIS_5000)
                }
            }
        }


        observerLoadingState()
        lifecycleScope.launch {
            delay(INTERVAL_TIME_MILLIS_10000)

        }
    }

    private fun sortRandomPhrase() {
        val phrase: List<String> = listOf(
            getString(R.string.phrase_loading_your_next_masterpiece_on_your_skin),
            getString(R.string.phrase_preparing_the_inks_and_the_inspiration),
            getString(R.string.phrase_please_wait_while_we_customize_your_next_expression_mark),
            getString(R.string.phrase_transforming_your_ideas_into_art),
            getString(R.string.phrase_the_wait_is_worth_it_your_new_tattoo_is_on_its_way),
            getString(R.string.phrase_loading_because_great_artworks_take_time),
            getString(R.string.phrase_coming_soon_your_story_will_gain_a_new_chapter_on_your_skin),
            getString(R.string.phrase_loading_because_perfection_demands_patience),
            getString(R.string.phrase_getting_ready_to_make_history_on_your_skin),
            getString(R.string.phrase_loading_because_every_detail_is_important_to_us_and_to_you)
        )
        val index = Random.nextInt(phrase.size)
        val phraseSorted = phrase[index]
        binding.txtLoading.text = phraseSorted
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLoadingScreenBinding {
        return FragmentLoadingScreenBinding.inflate(inflater, container, false)
    }

    private fun observerLoadingState() {
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
