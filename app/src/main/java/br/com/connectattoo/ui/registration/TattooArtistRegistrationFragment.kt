package br.com.connectattoo.ui.registration

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentTattooArtistRegistrationBinding
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TattooArtistRegistrationFragment : UserRegistration<FragmentTattooArtistRegistrationBinding>() {

	override fun setupViews() {
		name = binding.editTextName
		email = binding.editTextEmail
		password = binding.editTextPassword
		confirmPassword = binding.editTextConfirmPassword
		birthDay = binding.editTextDate

		inputPassword()
		inputPasswordconfirm()
		dateMask()
		validateEmail()
		validatingDate()
		nameFocusListener()

		binding.btCreateAccount.setOnClickListener {
			val name = binding.editTextName.text.toString()
			val checkBox = binding.checkBox
			val checked: Boolean = checkBox.isChecked

			isValidatingDate()
			confirmPassword()
			isEmailValid()
			validPassword()
			isValidatName()

			if (name.isEmpty() ||  (incorrectEmail == true)  || (correctPassword == false ) ||  (checked == false) || (incorrectConfirmPassword == true) || (incorrectDate == true)) {
				val snackBar = Snackbar.make(it, "Todos os campos devem ser preenchidos!", Snackbar.LENGTH_SHORT)
				snackBar.setTextColor(Color.WHITE)
				snackBar.setBackgroundTint(Color.RED)
				snackBar.show()
			}
		}

		binding.btCancel.setOnClickListener {
			findNavController().navigate(R.id.action_artistRegistrationFragment_to_welcomeFragment)
		}
	}

    override fun inflateBinding(
		inflater: LayoutInflater,
		container: ViewGroup?
	): FragmentTattooArtistRegistrationBinding {
		return FragmentTattooArtistRegistrationBinding.inflate(inflater, container, false)
	}

	private fun nameFocusListener() {
        binding.editTextName.setOnFocusChangeListener{ _,focused ->
            if (!focused) isValidatName()
        }
    }

    private fun isValidatName() {
        val name = binding.editTextName.text.toString()
        if (name.isEmpty()){
            binding.editTextName.setBackgroundResource(R.drawable.bg_edit_input_invalid)
        } else {
            binding.editTextName.setBackgroundResource(R.drawable.bg_edit_input_valid)
        }
    }

    private fun validatingDate() {
        birthDay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isValidatingDate()
            }
            override fun afterTextChanged(s: Editable?) { }
        })
    }

    private fun isValidatingDate() {
		val birthDay = binding.editTextDate.text.toString()
		val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        dateFormat.isLenient = false

        try {
            val parsedDate = dateFormat.parse(birthDay)
            if (parsedDate != null) {
                val currentDate = Date()
                val calendar = Calendar.getInstance()
                calendar.time = parsedDate
                val year = calendar[Calendar.YEAR]

                if (year >= 1000 && year <= (year + 1900)) {
                    binding.txtInforErrorDate.visibility = View.GONE
                    incorrectDate = false
                    binding.editTextDate.setBackgroundResource(R.drawable.bg_edit_input_valid)
                } else {
                    throw ParseException("", 0)
                }

                if (!parsedDate.after(currentDate)) {
                    binding.txtInforErrorDate.visibility = View.GONE
                    incorrectDate = false
                    binding.editTextDate.setBackgroundResource(R.drawable.bg_edit_input_valid)
                } else {
                    binding.txtInforErrorDate.visibility = View.VISIBLE
                    incorrectDate = true
                    binding.editTextDate.setBackgroundResource(R.drawable.bg_edit_input_invalid)
                }
            }
        } catch (e: ParseException) {
            binding.txtInforErrorDate.visibility = View.VISIBLE
            incorrectDate = true
            binding.editTextDate.setBackgroundResource(R.drawable.bg_edit_input_invalid)
        }
    }

    private fun dateMask() {
        birthDay = binding.editTextDate
        val smf = SimpleMaskFormatter("NN/NN/NNNN")
        val mtw = MaskTextWatcher(birthDay, smf)
		birthDay.addTextChangedListener(mtw)
    }

    private fun validPassword() {
        val password = password.text.toString()
        if (password.length <8) {
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

        if (!password.matches(Regex("^(?=.*[_.*=!%()$&@+-/]).*$"))) {
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

        if (!password.matches(".*[A-Z].*".toRegex())) {
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

        if (!password.matches(".*[a-z].*".toRegex())) {
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

        if (!password.matches(".*[0-9].*".toRegex())) {
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

        if (isChar == false &&  hasNum == false && hasSpecialSymbol == false && hasUpper == false && hasLow == false ) {
            binding.txtpasswordNotCharacteristics.visibility = View.GONE
            binding.linearLayout.visibility = View.GONE
            binding.txtpasswordFeature.visibility = View.VISIBLE
            correctPassword = true
            binding.editTextPassword.setBackgroundResource(R.drawable.bg_edit_input_valid)
        } else {
            binding.editTextPassword.setBackgroundResource(R.drawable.bg_edit_input_invalid)
        }
    }

    private fun inputPassword() {
        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validPassword()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun inputPasswordconfirm() {
        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                confirmPassword()
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }

    private fun confirmPassword() {
        val confirmPassword = binding.editTextConfirmPassword.text.toString()
        val password = binding.editTextPassword.text.toString()
        if (confirmPassword.isEmpty()) {
            incorrectConfirmPassword = true
            binding.txtconfirmPasswordError.visibility = View.GONE
            binding.editTextConfirmPassword.setBackgroundResource(R.drawable.bg_edit_input_invalid)
        } else if (password != confirmPassword) {
            incorrectConfirmPassword = true
            binding.txtconfirmPasswordError.visibility = View.VISIBLE
            binding.editTextConfirmPassword.setBackgroundResource(R.drawable.bg_edit_input_invalid)
        } else {
            incorrectConfirmPassword = false
            binding.txtconfirmPasswordError.visibility = View.GONE
            binding.editTextConfirmPassword.setBackgroundResource(R.drawable.bg_edit_input_valid)
        }
    }

    private fun validateEmail() {
        email.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isEmailValid()
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }

    fun isEmailValid() {
        val email = binding.editTextEmail.text.toString()

		incorrectEmail = if (email.isEmpty()) {
			binding.editTextEmail.setBackgroundResource(R.drawable.bg_edit_input_invalid)
			true
		} else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			binding.editTextEmail.setBackgroundResource(R.drawable.bg_edit_input_invalid)
			true
		} else {
			binding.editTextEmail.setBackgroundResource(R.drawable.bg_edit_input_valid)
			false
		}
    }
}
