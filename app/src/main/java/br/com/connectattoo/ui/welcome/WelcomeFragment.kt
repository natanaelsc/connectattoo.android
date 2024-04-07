package br.com.connectattoo.ui.welcome

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.HomeUserActivity
import br.com.connectattoo.R
import br.com.connectattoo.api.response.ApiConfirmationResponse
import br.com.connectattoo.databinding.FragmentWelcomeBinding
import br.com.connectattoo.repository.AuthRepository
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.util.Constants.API_TOKEN
import br.com.connectattoo.util.Constants.CODE_ERROR_401
import br.com.connectattoo.util.Constants.CODE_ERROR_404
import br.com.connectattoo.util.DataStoreManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.io.IOException

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {
    private lateinit var repository: AuthRepository
    override fun setupViews() {
        repository = AuthRepository()
        verifyTokenApi()
        binding.cardArtist.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_artistRegistrationFragment)
        }
        binding.cardUser.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_userRegistrationFragment)
        }
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWelcomeBinding {
        return FragmentWelcomeBinding.inflate(inflater, container, false)

    }
    private fun verifyTokenApi() {
        viewLifecycleOwner.lifecycleScope.launch {
            val token = DataStoreManager.getStringToken(requireContext(), API_TOKEN)
            if (token.isNotEmpty()) {
                try {
                    verifyUserConfirmation("Bearer $token")
                } catch (e: IOException) {
                    showValidationError("Erro: ${e.message}")
                }
            }
        }
    }

    private suspend fun verifyUserConfirmation(token: String) {
        val result = repository.verifyUserConfirmation(token)
        if (result.isSuccessful) {
            handleSuccessfulResponse(result.body())
        } else {
            handleErrorResponse(result.code())
        }
    }

    private fun handleSuccessfulResponse(body: ApiConfirmationResponse?) {
        if (body?.emailConfirmed == true) {
            startActivity(Intent(requireContext(), HomeUserActivity::class.java))
            requireActivity().finish()
        } else {
            findNavController().navigate(R.id.action_welcomeFragment_to_confirmationFragment)
        }
    }

    private suspend fun handleErrorResponse(code: Int) {
        when (code) {
            CODE_ERROR_404 -> {
                showValidationError("A URL de destino não foi encontrada.")
                DataStoreManager.deleteApiKey(requireContext(), API_TOKEN)
            }
            CODE_ERROR_401 -> {
                showValidationError("Token Expirou, Faça o cadastro novamente!!!")
                DataStoreManager.deleteApiKey(requireContext(), API_TOKEN)
            }
            else -> showValidationError("Erro: $code")
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
}
