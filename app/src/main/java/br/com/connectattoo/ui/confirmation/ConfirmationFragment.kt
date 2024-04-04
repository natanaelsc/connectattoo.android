package br.com.connectattoo.ui.confirmation


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import br.com.connectattoo.HomeUserActivity
import br.com.connectattoo.api.ApiService
import br.com.connectattoo.api.ApiUrl
import br.com.connectattoo.databinding.FragmentConfirmationBinding
import br.com.connectattoo.repository.AuthRepository
import br.com.connectattoo.ui.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ConfirmationFragment : BaseFragment<FragmentConfirmationBinding>() {

    private val apiService: ApiService = ApiUrl.instance.create(ApiService::class.java)
    private val repository: AuthRepository = AuthRepository()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConfirmationBinding {
        return FragmentConfirmationBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        verifyUserConfirmation()
    }

    private fun verifyUserConfirmation() {
        val token = arguments?.getString("token")
        Log.i("token_confirm", token ?: "")
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val result = apiService.verifyUserConfirmation("Bearer $token")

                if (result.isSuccessful) {
                    if (result.body()?.emailConfirmed == true) {
                        startActivity(Intent(requireContext(), HomeUserActivity::class.java))
                    } else {
                        Snackbar.make(binding.root, result.message(), Snackbar.ANIMATION_MODE_SLIDE).show()
                    }


                }
            } catch (e: Exception) {
                Snackbar.make(binding.root, "Erro na requisição", Snackbar.ANIMATION_MODE_SLIDE).show()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        verifyUserConfirmation()
    }

    override fun setupViews() {
    }
}
