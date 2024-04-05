package br.com.connectattoo.ui.welcome

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.HomeUserActivity
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentWelcomeBinding
import br.com.connectattoo.repository.AuthRepository
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.util.Constants.API_TOKEN
import br.com.connectattoo.util.DataStoreManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {
    private lateinit var repository: AuthRepository
    override fun setupViews() {

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
        repository = AuthRepository()
        viewLifecycleOwner.lifecycleScope.launch {
            val token = DataStoreManager.getStringToken(requireContext(), API_TOKEN)
            Log.i("test", token)
            if (token.isNotEmpty()) {
                try {
                    val result = repository.verifyUserConfirmation("Bearer $token")

                    if (result.isSuccessful) {
                        if (result.body()?.emailConfirmed == true) {
                            startActivity(Intent(requireContext(), HomeUserActivity::class.java))
                            requireActivity().finish()
                        } else {
                            findNavController().navigate(R.id.action_welcomeFragment_to_confirmationFragment)
                        }
                    } else {
                        when (result.code()) {
                            404 -> showValidationError("A URL de destino não foi encontrada.")
                            401 -> {
                                showValidationError("Token Expirou, Faça o cadastro novamente!!!")
                                DataStoreManager.deleteApiKey(requireContext(), API_TOKEN)
                            }

                            else -> showValidationError("Erro: ${result.code()}")
                        }
                    }
                } catch (e: Exception) {
                    showValidationError("Erro de conexão com a internet!")
                }
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
}
