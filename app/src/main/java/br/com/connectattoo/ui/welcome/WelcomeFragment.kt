package br.com.connectattoo.ui.welcome

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.HomeUserActivity
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentWelcomeBinding
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.util.Constants.API_TOKEN
import br.com.connectattoo.util.DataStoreManager
import kotlinx.coroutines.launch

class WelcomeFragment : BaseFragment<FragmentWelcomeBinding>() {
    override fun setupViews() {

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
    override fun onStart() {
        super.onStart()
        viewLifecycleOwner.lifecycleScope.launch {
            val token = DataStoreManager.getStringToken(requireContext(), API_TOKEN)
            if (token.isNotEmpty()){
                startActivity(Intent(requireContext(), HomeUserActivity::class.java))
            }
        }

    }
}
