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
import br.com.connectattoo.utils.Constants.API_TOKEN
import br.com.connectattoo.utils.Constants.API_USER_NAME
import br.com.connectattoo.utils.Constants.CODE_ERROR_401
import br.com.connectattoo.utils.Constants.CODE_ERROR_403
import br.com.connectattoo.utils.Constants.CODE_ERROR_404
import br.com.connectattoo.utils.DataStoreManager
import br.com.connectattoo.utils.executeWithLoadingAsync
import br.com.connectattoo.utils.hideLoadingFragment
import kotlinx.coroutines.launch

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {
    private lateinit var repository: AuthRepository
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentWelcomeBinding {
        return FragmentWelcomeBinding.inflate(inflater, container, false)

    }

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


    private fun verifyTokenApi() {
        viewLifecycleOwner.lifecycleScope.launch {
            val token = DataStoreManager.getUserSettings(requireContext(), API_TOKEN)
            if (token.isNotEmpty()) {
                verifyUserConfirmation(token)
            }
        }
    }

    private suspend fun verifyUserConfirmation(token: String) {
        val result = executeWithLoadingAsync(binding.root) {
            repository.verifyUserConfirmation(token)
        }
        if (result.await().isSuccessful) {
            handleSuccessfulResponse(result.await().body(), token)
        } else {
            handleErrorResponse(result.await().code(), token)
        }
        hideLoadingFragment(binding.root)
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

    private suspend fun handleErrorResponse(code: Int, token: String) {
        when (code) {
            CODE_ERROR_403 -> {
                val bundle = Bundle().apply { putString("token", token) }
                findNavController().navigate(
                    R.id.action_welcomeFragment_to_confirmationFragment,
                    bundle
                )
            }
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
