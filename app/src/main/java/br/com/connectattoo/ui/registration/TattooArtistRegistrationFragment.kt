package br.com.connectattoo.ui.registration

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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

@Suppress("TooManyFunctions")
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

        binding.txtConditionsPassword.visibility =
            if (!isPasswordValid(password)) View.VISIBLE else View.GONE
        binding.txtpasswordNotCharacteristics.visibility =
            if (hasErrors) View.VISIBLE else View.GONE
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
