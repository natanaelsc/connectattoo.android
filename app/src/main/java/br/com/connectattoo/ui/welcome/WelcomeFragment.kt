package br.com.connectattoo.ui.welcome

import android.content.Intent
import android.os.Bundle
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
import br.com.connectattoo.util.Constants.API_USER_NAME
import br.com.connectattoo.util.Constants.CODE_ERROR_401
import br.com.connectattoo.util.Constants.CODE_ERROR_404
import br.com.connectattoo.util.DataStoreManager
import kotlinx.coroutines.launch

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
            val token = DataStoreManager.getUserSettings(requireContext(), API_TOKEN)
            if (token.isNotEmpty()) {
                verifyUserConfirmation(token)
            }
        }
    }

    private suspend fun verifyUserConfirmation(token: String) {
        val result = repository.verifyUserConfirmation("Bearer $token")
        if (result.isSuccessful) {
            handleSuccessfulResponse(result.body(), token)
        } else {
            handleErrorResponse(result.code())
        }
    }

    private fun handleSuccessfulResponse(body: ApiConfirmationResponse?, token: String) {
        if (body?.emailConfirmed == true) {
            startActivity(Intent(requireContext(), HomeUserActivity::class.java))
            requireActivity().finish()
        } else {
            val bundle = Bundle().apply { putString("token", token) }
            findNavController().navigate(
                R.id.action_welcomeFragment_to_confirmationFragment,
                bundle
            )
        }
    }

    private suspend fun handleErrorResponse(code: Int) {
        when (code) {
            CODE_ERROR_404 -> {
                deleteUserInfoDataStore()
            }

            CODE_ERROR_401 -> {
                deleteUserInfoDataStore()
            }
        }
    }

    private suspend fun deleteUserInfoDataStore() {
        DataStoreManager.deleteApiKey(requireContext(), API_TOKEN)
        DataStoreManager.deleteApiKey(requireContext(), API_USER_NAME)
    }
}
