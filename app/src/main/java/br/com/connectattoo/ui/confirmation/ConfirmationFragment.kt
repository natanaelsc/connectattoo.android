package br.com.connectattoo.ui.confirmation

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import br.com.connectattoo.HomeUserActivity
import br.com.connectattoo.api.response.ApiConfirmationResponse
import br.com.connectattoo.databinding.FragmentConfirmationBinding
import br.com.connectattoo.repository.AuthRepository
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.utils.Constants
import br.com.connectattoo.utils.Constants.CODE_ERROR_401
import br.com.connectattoo.utils.Constants.CODE_ERROR_404
import br.com.connectattoo.utils.DataStoreManager
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

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
            val token = arguments?.getString("token")
            try {
                if (token != null){
                    val result = repository.verifyUserConfirmation(token)
                    verifyRequestApi(result)
                }

            } catch (error: IOException) {
                Log.i(TAG, error.message.toString())
                binding.swipeRefreshConfirmationScreen.isRefreshing = false
            }

        }
    }

    private suspend fun ConfirmationFragment.verifyRequestApi(result: Response<ApiConfirmationResponse>) {
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
                CODE_ERROR_404 -> deleteUserInfoDataStore()
                CODE_ERROR_401 -> deleteUserInfoDataStore()
            }
            binding.swipeRefreshConfirmationScreen.isRefreshing = false
        }
    }

    private suspend fun deleteUserInfoDataStore() {
        DataStoreManager.deleteApiKey(requireContext(), Constants.API_TOKEN)
        DataStoreManager.deleteApiKey(requireContext(), Constants.API_USER_NAME)
    }

    private fun swipeRefresh() {
        binding.swipeRefreshConfirmationScreen.setOnRefreshListener {
            verifyUserConfirmation()
        }
    }


}
