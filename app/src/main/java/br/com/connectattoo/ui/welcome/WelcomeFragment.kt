package br.com.connectattoo.ui.welcome

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentWelcomeBinding
import br.com.connectattoo.ui.BaseFragment

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
}
