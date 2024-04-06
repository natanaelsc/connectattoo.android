package br.com.connectattoo.ui.registration

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.R
import br.com.connectattoo.data.AddressData
import br.com.connectattoo.data.ArtistData
import br.com.connectattoo.data.TokenData
import br.com.connectattoo.databinding.FragmentTattooArtistRegistrationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TattooArtistRegistrationFragment :
    UserRegistration<FragmentTattooArtistRegistrationBinding>() {

    private lateinit var cep: EditText
    private lateinit var street: EditText
    private lateinit var number: EditText
    private lateinit var city: EditText
    private lateinit var state: EditText


    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooArtistRegistrationBinding {
        return FragmentTattooArtistRegistrationBinding.inflate(inflater, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
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

        mask(this.cep, "NNNNN-NNN")

        onTextChanged(cep) { validateCep() }

        onTextChanged(street) { validateStreet() }

        onTextChanged(number) { validateNumber() }

        onTextChanged(city) { validateCity() }

        onTextChanged(state) { validateState() }

        btnCreateAccount(R.id.action_artistRegistrationFragment_to_confirmationFragment)

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

    private fun validateCep() {
        val cep = this.cep.text.toString()
        if (isCepValid(cep)) {
            this.setBackgroundValid(this.cep)
        } else {
            this.setBackgroundInvalid(this.cep)
        }
    }

    private fun validateStreet() {
        val street = this.street.text.toString()
        if (isStreetValid(street)) {
            this.setBackgroundValid(this.street)
        } else {
            this.setBackgroundInvalid(this.street)
        }
    }

    private fun validateNumber() {
        val number = this.number.text.toString()
        if (isNumberValid(number)) {
            this.setBackgroundValid(this.number)
        } else {
            this.setBackgroundInvalid(this.number)
        }
    }

    private fun validateCity() {
        val city = this.city.text.toString()
        if (isCityValid(city)) {
            this.setBackgroundValid(this.city)
        } else {
            this.setBackgroundInvalid(this.city)
        }
    }

    private fun validateState() {
        val state = this.state.text.toString()
        if (isStateValid(state)) {
            this.setBackgroundValid(this.state)
        } else {
            this.setBackgroundInvalid(this.state)
        }
    }

    private fun isCepValid(cep: String): Boolean {
        return cep.isNotEmpty()
    }

    private fun isStreetValid(street: String): Boolean {
        return street.isNotEmpty()
    }

    private fun isNumberValid(number: String): Boolean {
        return number.isNotEmpty()
    }

    private fun isCityValid(city: String): Boolean {
        return city.isNotEmpty()
    }

    private fun isStateValid(state: String): Boolean {
        return state.isNotEmpty()
    }

    override fun conditionChecking(view: View) {
        val name = this.name.text.toString()
        val termsChecked = this.terms.isChecked
        fieldsComplete =
            !(name.isEmpty() || incorrectEmail || !correctPassword || !termsChecked
                || incorrectConfirmPassword || incorrectDate
                || !isCepValid(cep.text.toString()) || !isStreetValid(street.text.toString())
                || !isNumberValid(
                number.text.toString()
            )
                || !isCityValid(city.text.toString()) || !isStateValid(state.text.toString()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createArtistRegisterData(): ArtistData {
        val address = AddressData(
            street = this.street.text.toString(),
            number = this.number.text.toString(),
            city = this.city.text.toString(),
            state = this.state.text.toString(),
            zipCode = this.cep.text.toString()
        )
        return ArtistData(
            name = this.name.text.toString(),
            email = this.email.text.toString(),
            birthDate = formatBirthDate(this.birthDay.text.toString()),
            password = this.password.text.toString(),
            termsAccepted = this.terms.isChecked,
            address = address
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun validateUserRegistration(action: Int) {
        val artistRegisterData = createArtistRegisterData()

        apiService.registerArtist(artistRegisterData).enqueue(object : Callback<TokenData> {
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
