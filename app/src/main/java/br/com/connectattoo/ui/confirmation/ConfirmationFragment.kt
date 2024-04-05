package br.com.connectattoo.ui.confirmation

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import br.com.connectattoo.HomeUserActivity
import br.com.connectattoo.databinding.FragmentConfirmationBinding
import br.com.connectattoo.repository.AuthRepository
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.util.Constants.API_TOKEN
import br.com.connectattoo.util.DataStoreManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ConfirmationFragment : BaseFragment<FragmentConfirmationBinding>() {

    private lateinit var repository: AuthRepository
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConfirmationBinding {
        return FragmentConfirmationBinding.inflate(inflater, container, false)
    }

    override fun setupViews() {
        verifyUserConfirmation()
        swipeRefresh()
    }

    private fun verifyUserConfirmation() {
        repository = AuthRepository()

        viewLifecycleOwner.lifecycleScope.launch {
            val token = DataStoreManager.getStringToken(requireContext(), API_TOKEN)

            try {
                val result = repository.verifyUserConfirmation("Bearer $token")

                if (result.isSuccessful) {
                    if (result.body()?.emailConfirmed == true) {
                        binding.swipeRefreshConfirmationScreen.isRefreshing = false
                        startActivity(Intent(requireContext(), HomeUserActivity::class.java))
                        requireActivity().finish()
                    } else {
                        binding.swipeRefreshConfirmationScreen.isRefreshing = false
                    }
                } else {
                    when (result.code()) {
                        404 -> showValidationError("A URL de destino não foi encontrada.")
                        401 -> showValidationError("Erro de Autenticação!!!")
                        else -> showValidationError("Erro: ${result.code()}")
                    }
                    binding.swipeRefreshConfirmationScreen.isRefreshing = false
                }
            } catch (e: Exception) {
                showValidationError("Erro de conexão com a internet!")
                binding.swipeRefreshConfirmationScreen.isRefreshing = false
            }

        }
    }


    private fun showValidationError(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.RED)
                .show()
        }
    }

    private fun swipeRefresh() {
        binding.swipeRefreshConfirmationScreen.setOnRefreshListener {
            verifyUserConfirmation()
        }
    }


}
