package br.com.connectattoo.ui.profile.tattoclientditprofile

import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.connectattoo.ConnectattooApplication
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentTattooClientEditProfileBinding
import br.com.connectattoo.repository.ProfileRepository
import br.com.connectattoo.ui.BaseFragment
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
class TattooClientEditProfileFragment : BaseFragment<FragmentTattooClientEditProfileBinding>() {
    private lateinit var profileRepository: ProfileRepository
    private val viewModel: TattooClientEditProfileViewModel by viewModels()

    override fun setupViews() {
        getInitialInformationClientProfile()
        observerViewModel()
        observerAndValidateBirthDate()
    }

    private fun getInitialInformationClientProfile() {
        val database = (requireActivity().application as ConnectattooApplication).database
        val clientProfileDao = database.tattooClientProfileDao()
        profileRepository = ProfileRepository(clientProfileDao)
        viewModel.getInitialInformationTattooClientProfile(profileRepository)
    }

    private fun observerViewModel() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collect { uiState ->
                    when (uiState) {
                        TattooClientEditProfileViewModel.UiState.Success -> {
                            insertInformationTattooClientProfile()
                        }

                        TattooClientEditProfileViewModel.UiState.Error -> {
                        }

                        TattooClientEditProfileViewModel.UiState.Loading -> {

                        }

                        else -> {
                        }
                    }
                }
            }
        }
    }

    private fun insertInformationTattooClientProfile() {
        val image = viewModel.dataState.imageProfile
        binding.run {
            Glide.with(ivPhotoClient)
                .load(
                    if (image.isNullOrEmpty()) R.drawable.icon_person_profile_black
                    else image
                )
                .circleCrop()
                .placeholder(R.drawable.icon_person_profile)
                .into(ivPhotoClient)
            etDisplayName.setText(viewModel.dataState.displayName)
            etUserName.setText(viewModel.dataState.username)
            etClientEmail.setText(viewModel.dataState.email)
            etBirthDate.setText(viewModel.dataState.birthDate)
        }
    }

    private fun observerAndValidateBirthDate() {
        binding.etBirthDate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (count == 10) {
                    val clientBirthDate = binding.etBirthDate.unMasked
                    val check = validateDate(clientBirthDate)
                    if (check != null) {
                        Log.i("check", check)
                    } else {
                        Log.i("check", "error")
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooClientEditProfileBinding {
        return FragmentTattooClientEditProfileBinding.inflate(inflater, container, false)
    }

    private fun validateDate(birthDate: String): String? {
        if (birthDate.length != 8) {
            return null
        }

        val day = birthDate.substring(0, 2)
        val month = birthDate.substring(2, 4)
        val year = birthDate.substring(4)

        val formattedDate = "$year-$month-$day"

        val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        format.isLenient = false

        return try {
            format.parse(formattedDate)
            formattedDate
        } catch (e: Exception) {
            null
        }
    }

}
