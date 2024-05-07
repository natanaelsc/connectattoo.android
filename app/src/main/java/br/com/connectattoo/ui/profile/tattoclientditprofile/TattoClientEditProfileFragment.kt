package br.com.connectattoo.ui.profile.tattoclientditprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.connectattoo.databinding.FragmentTattoClientEditProfileBinding
import br.com.connectattoo.ui.BaseFragment
import java.text.SimpleDateFormat
import java.util.Locale


class TattoClientEditProfileFragment : BaseFragment<FragmentTattoClientEditProfileBinding>() {
    override fun setupViews() {
        getAndValidateBirthDate()
    }

    private fun getAndValidateBirthDate() {
        val clientBirthDate = binding.etBirthDate.masked
        validateDate(clientBirthDate)
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattoClientEditProfileBinding {
        return FragmentTattoClientEditProfileBinding.inflate(inflater, container, false)
    }

    private fun validateDate(birthDate: String): Boolean {
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        format.isLenient = false

        return try {
            format.parse(birthDate)
            true
        } catch (e: Exception) {
            false
        }
    }

}
