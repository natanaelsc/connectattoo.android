package br.com.connectattoo.ui.profile.tattooclientconfiguration

import android.content.ContentValues.TAG
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.ConnectattooApplication
import br.com.connectattoo.MainActivity
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentTattooClientConfigurationBinding
import br.com.connectattoo.repository.ProfileRepository
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.utils.Constants
import br.com.connectattoo.utils.DataStoreManager
import kotlinx.coroutines.launch
import java.io.IOException

class TattooClientConfigurationFragment : BaseFragment<FragmentTattooClientConfigurationBinding>() {
    override fun setupViews() {
        setupListeners()
    }
    private lateinit var profileRepository: ProfileRepository

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
            logOutClient()
        }
        binding.txtLogout.setOnClickListener {
            logOutClient()
        }
    }
    private fun logOutClient(){
        val database = (requireActivity().application as ConnectattooApplication).database
        val clientProfileDao = database.tattooClientProfileDao()
        profileRepository = ProfileRepository(clientProfileDao)
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                DataStoreManager.deleteApiKey(requireContext(), Constants.API_TOKEN)
                profileRepository.deleteClientProfile()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                requireActivity().finish()
            }catch (error: IOException){
                Log.e(TAG, error.message.toString())
            }
        }

    }
}
