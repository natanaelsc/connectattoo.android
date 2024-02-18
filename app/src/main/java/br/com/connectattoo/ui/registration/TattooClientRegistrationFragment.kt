package br.com.connectattoo.ui.registration

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.R
import br.com.connectattoo.data.ClientData
import br.com.connectattoo.data.TokenData
import br.com.connectattoo.databinding.FragmentTattooClientRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TattooClientRegistrationFragment : UserRegistration<FragmentTattooClientRegistrationBinding>() {

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

    override fun conditionChecking(view: View) {
        val name = this.name.text.toString()
        val termsChecked = this.terms.isChecked
        fieldsComplete = !(name.isEmpty() || incorrectEmail || !correctPassword || !termsChecked || incorrectConfirmPassword || incorrectDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createRegisterUserData() : ClientData {
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
