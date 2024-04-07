package br.com.connectattoo.ui.registration

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.R
import br.com.connectattoo.data.ClientData
import br.com.connectattoo.data.TokenData
import br.com.connectattoo.databinding.FragmentTattooClientRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TattooClientRegistrationFragment :
    UserRegistration<FragmentTattooClientRegistrationBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooClientRegistrationBinding {
        return FragmentTattooClientRegistrationBinding.inflate(inflater, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun setupSpecificViews() {
        name = binding.editTextName
        email = binding.editTextEmail
        password = binding.editTextPassword
        confirmPassword = binding.editTextConfirmPassword
        birthDay = binding.editTextDate
        terms = binding.checkBox

        btnCreateAccount = binding.btCreateAccount
        btnCancel = binding.btCancel

        btnCreateAccount(R.id.action_userRegistrationFragment_to_confirmationFragment)
        btnCancel(R.id.action_userRegistrationFragment_to_welcomeFragment)
    }

    override fun validatePassword() {
        val password = password.text.toString()
        val validationResults = listOf(
            Validation(
                HAS_SPECIAL_SYMBOL.toRegex(),
                binding.txtSpecialSymbol,
                binding.ImgCheckSpecialSymbol,
                binding.ImgCloseSpecialSymbol
            ),
            Validation(
                HAS_UPPER_CASE.toRegex(),
                binding.txtCapitalLetter,
                binding.ImgCheckCapitalLetter,
                binding.ImgCloseCapitalLetter
            ),
            Validation(
                HAS_LOWER_CASE.toRegex(),
                binding.txtLowerCase,
                binding.ImgCheckLowerCase,
                binding.ImgCloseLowerCase
            ),
            Validation(
                HAS_NUMBER.toRegex(),
                binding.txtNumber,
                binding.ImgCheckNumber,
                binding.ImgCloseNumber
            )
        )

        var hasErrors = false
        for ((regex, textView, checkImage, closeImage) in validationResults) {
            val isValid = password.matches(regex)
            textView.setTextColor(if (isValid) Color.GREEN else Color.RED)
            checkImage.visibility = if (isValid) View.VISIBLE else View.GONE
            closeImage.visibility = if (isValid) View.GONE else View.VISIBLE
            if (!isValid) hasErrors = true
        }

        binding.txtConditionsPassword.visibility = if (!isPasswordValid(password))
            View.VISIBLE else View.GONE
        binding.txtpasswordNotCharacteristics.visibility = if (hasErrors)
            View.VISIBLE else View.GONE
        binding.linearLayout.visibility = if (hasErrors) View.VISIBLE else View.GONE
        binding.txtpasswordFeature.visibility = if (hasErrors) View.GONE else View.VISIBLE

        if (!isPasswordValid(password) || hasErrors) {
            correctPassword = false
            setBackgroundInvalid(binding.editTextPassword)
        } else {
            correctPassword = true
            setBackgroundValid(binding.editTextPassword)
        }
    }

    data class Validation(
        val regex: Regex, val textView: TextView, val checkImage: ImageView,
        val closeImage: ImageView
    )

    override fun validateConfirmPassword() {
        val confirmPassword = binding.editTextConfirmPassword.text.toString()
        val password = binding.editTextPassword.text.toString()
        if (!isPasswordValid(confirmPassword)) {
            incorrectConfirmPassword = true
            binding.txtconfirmPasswordError.visibility = View.GONE
            setBackgroundInvalid(binding.editTextConfirmPassword)
        } else if (password != confirmPassword) {
            incorrectConfirmPassword = true
            binding.txtconfirmPasswordError.visibility = View.VISIBLE
            setBackgroundInvalid(binding.editTextConfirmPassword)
        } else {
            incorrectConfirmPassword = false
            binding.txtconfirmPasswordError.visibility = View.GONE
            setBackgroundValid(binding.editTextConfirmPassword)
        }
    }

    override fun conditionChecking(view: View) {
        val name = this.name.text.toString()
        val termsChecked = this.terms.isChecked
        fieldsComplete =
            !(name.isEmpty() || incorrectEmail || !correctPassword || !termsChecked
                || incorrectConfirmPassword || incorrectDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createRegisterUserData(): ClientData {
        return ClientData(
            name = this.name.text.toString(),
            email = this.email.text.toString(),
            birthDate = formatBirthDate(this.birthDay.text.toString()),
            password = this.password.text.toString(),
            termsAccepted = this.terms.isChecked
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun validateUserRegistration(action: Int) {
        val userRegisterData = createRegisterUserData()

        apiService.registerUser(userRegisterData).enqueue(object : Callback<TokenData> {
            override fun onResponse(call: Call<TokenData>, response: Response<TokenData>) {
                registrationResponse(action, response) {
                    response.body()?.let {
                        findNavController().navigate(action)
                    }
                }
            }

            override fun onFailure(call: Call<TokenData>, t: Throwable) {
                showValidationError("Falha na conexão com a internet")
            }
        })
    }

}
