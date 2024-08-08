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
            "Carregando sua próxima obra-prima na pele...",
            "Preparando as tintas e a inspiração...",
            "Aguarde enquanto customizamos sua próxima marca de expressão...",
            "Transformando suas ideias em arte!",
            "A espera vale a pena: sua nova tatuagem está chegando!",
            "Carregando... porque grandes obras de arte levam tempo.",
            "Em breve: sua história ganhará um novo capítulo na pele.",
            "Carregando... porque a perfeição demanda paciência.",
            "Preparando-se para fazer história na sua pele.",
            "Carregando... porque cada detalhe é importante para nós e para você."
        )
        val index = Random.nextInt(phrase.size)
        val phraseSorted = phrase[index]
        binding.txtThisMayTakeAFewSeconds.text = phraseSorted
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
