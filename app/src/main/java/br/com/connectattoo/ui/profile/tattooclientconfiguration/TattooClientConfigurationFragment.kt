package br.com.connectattoo.ui.profile.tattooclientconfiguration

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentTattooClientConfigurationBinding
import br.com.connectattoo.ui.BaseFragment

class TattooClientConfigurationFragment : BaseFragment<FragmentTattooClientConfigurationBinding>() {
    override fun setupViews() {
        setupListeners()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooClientConfigurationBinding {
        return FragmentTattooClientConfigurationBinding.inflate(inflater, container, false)
    }
    private fun setupListeners(){
        binding.ivBack.setOnClickListener {
            findNavController().navigate(R.id.action_tattooClientConfigurationFragment_to_clientUserProfileFragment)
        }
        binding.ivCommunicationSettings.setOnClickListener {

        }
        binding.ivChangePasswordSettings.setOnClickListener {

        }
        binding.ivDeleteAccountSettings.setOnClickListener {

        }
        binding.ivAboutSettings.setOnClickListener {

        }
        binding.ivHelpSettings.setOnClickListener {

        }
        binding.ivLogoutSettings.setOnClickListener {

        }
    }

}
