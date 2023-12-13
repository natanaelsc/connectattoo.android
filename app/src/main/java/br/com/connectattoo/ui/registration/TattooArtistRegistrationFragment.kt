package br.com.connectattoo.ui.registration

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentTattooArtistRegistrationBinding

class TattooArtistRegistrationFragment : UserRegistration<FragmentTattooArtistRegistrationBinding>() {

    protected lateinit var cep : EditText
    protected lateinit var street : EditText
    protected lateinit var number : EditText
    protected lateinit var city : EditText
    protected lateinit var state : EditText
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooArtistRegistrationBinding {
        return FragmentTattooArtistRegistrationBinding.inflate(inflater, container, false)
    }

	override fun setupSpecificViews() {
		name = binding.editTextName
		email = binding.editTextEmail
		password = binding.editTextPassword
		confirmPassword = binding.editTextConfirmPassword
        cep = binding.editTextCep
        street = binding.editTextStreet
        number = binding.editTextNumber
        city = binding.editTextCity
        state = binding.editTextState
		birthDay = binding.editTextDate
        terms = binding.checkBox

        btnCreateAccount = binding.btCreateAccount
        btnCancel = binding.btCancel

        btnCancel(R.id.action_artistRegistrationFragment_to_welcomeFragment)

        onTextCh
	}

    override fun validatePassword() {
        val password = password.text.toString()
        if (!isPasswordValid(password)) {
            isChar = true
            binding.txtConditionsPassword.visibility = View.VISIBLE
            binding.txtpasswordNotCharacteristics.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtpasswordFeature.visibility = View.GONE
            binding.txtMinimumCharacters.setTextColor(Color.RED)
            binding.ImgCloseMinimumCharacters.visibility = View.VISIBLE
            binding.ImgCheckMinimumCharacters.visibility = View.GONE
        } else {
            isChar = false
            binding.txtMinimumCharacters.setTextColor(Color.GREEN)
            binding.ImgCheckMinimumCharacters.visibility = View.VISIBLE
            binding.ImgCloseMinimumCharacters.visibility = View.GONE
        }

        if (!password.matches(HAS_SPECIAL_SYMBOL.toRegex())) {
            hasSpecialSymbol = true
            binding.txtpasswordNotCharacteristics.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtpasswordFeature.visibility = View.GONE
            binding.txtSpecialSymbol.setTextColor(Color.RED)
            binding.ImgCloseSpecialSymbol.visibility = View.VISIBLE
            binding.ImgCheckSpecialSymbol.visibility = View.GONE
        } else {
            hasSpecialSymbol = false
            binding.txtSpecialSymbol.setTextColor(Color.GREEN)
            binding.ImgCheckSpecialSymbol.visibility = View.VISIBLE
            binding.ImgCloseSpecialSymbol.visibility = View.GONE
        }

        if (!password.matches(HAS_UPPER_CASE.toRegex())) {
            hasUpper = true
            binding.txtpasswordNotCharacteristics.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtpasswordFeature.visibility = View.GONE
            binding.txtCapitalLetter.setTextColor(Color.RED)
            binding.ImgCloseCapitalLetter.visibility = View.VISIBLE
            binding.ImgCheckCapitalLetter.visibility = View.GONE
        } else {
            hasUpper = false
            binding.txtCapitalLetter.setTextColor(Color.GREEN)
            binding.ImgCheckCapitalLetter.visibility = View.VISIBLE
            binding.ImgCloseCapitalLetter.visibility = View.GONE
        }

        if (!password.matches(HAS_LOWER_CASE.toRegex())) {
            hasLow = true
            binding.txtpasswordNotCharacteristics.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtpasswordFeature.visibility = View.GONE
            binding.txtLowerCase.setTextColor(Color.RED)
            binding.ImgCloseLowerCase.visibility = View.VISIBLE
            binding.ImgCheckLowerCase.visibility = View.GONE
        } else {
            hasLow = false
            binding.txtLowerCase.setTextColor(Color.GREEN)
            binding.ImgCheckLowerCase.visibility = View.VISIBLE
            binding.ImgCloseLowerCase.visibility = View.GONE
        }

        if (!password.matches(HAS_NUMBER.toRegex())) {
            hasNum = true
            binding.txtpasswordNotCharacteristics.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtpasswordFeature.visibility = View.GONE
            binding.txtNumber.setTextColor(Color.RED)
            binding.ImgCloseNumber.visibility = View.VISIBLE
            binding.ImgCheckNumber.visibility = View.GONE
        } else {
            hasNum = false
            binding.txtNumber.setTextColor(Color.GREEN)
            binding.ImgCloseNumber.visibility = View.GONE
            binding.ImgCheckNumber.visibility = View.VISIBLE
        }

        if (!isChar && !hasNum && !hasSpecialSymbol && !hasUpper && !hasLow) {
            binding.txtpasswordNotCharacteristics.visibility = View.GONE
            binding.linearLayout.visibility = View.GONE
            binding.txtpasswordFeature.visibility = View.VISIBLE
            correctPassword = true
            setBackgroundValid(binding.editTextPassword)
        } else {
            setBackgroundInvalid(binding.editTextPassword)
        }
    }

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
}
