package br.com.connectattoo.ui.registration

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.widget.EditText
import androidx.viewbinding.ViewBinding
import br.com.connectattoo.R
import br.com.connectattoo.ui.BaseFragment
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

abstract class UserRegistration<T: ViewBinding> : BaseFragment<T>() {

    protected lateinit var name : EditText
    protected lateinit var email : EditText
    protected lateinit var password : EditText
    protected lateinit var confirmPassword : EditText
    protected lateinit var birthDay : EditText

	protected var isChar = false
	protected var hasUpper = false
	protected var hasLow = false
	protected var hasNum = false
	protected var hasSpecialSymbol = false
	protected var incorrectConfirmPassword = true
	protected var correctPassword = false
	protected var incorrectDate = false
	protected var incorrectEmail = true

    protected fun validateName() {
        val name = this.name.text.toString()
        if (this.isNameValid(name)) {
            this.setBackgroundValid(this.name)
        } else {
            this.setBackgroundInvalid(this.name)
        }
    }

    protected fun validateEmail() {
        val email = this.email.text.toString()
        if (this.isEmailValid(email)) {
            this.incorrectEmail = false
            this.setBackgroundValid(this.email)
        } else {
            this.incorrectEmail = true
            this.setBackgroundInvalid(this.email)
        }
    }

    protected fun validateBirthDay() {
        val birthDay = this.birthDay.text.toString()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
        dateFormat.isLenient = false
        try {
            val parsedDate = dateFormat.parse(birthDay)
            if (parsedDate != null) {
                val currentDate = Date()
                val calendar = Calendar.getInstance()
                calendar.time = parsedDate
                val year = calendar[Calendar.YEAR]

                if (year >= 1923 && year <= (year + 100)) {
                    this.incorrectDate = false
                    this.setBackgroundValid(this.birthDay)
                } else {
                    throw ParseException("", 0)
                }

                if (!parsedDate.after(currentDate)) {
                    this.incorrectDate = false
                    this.setBackgroundValid(this.birthDay)
                } else {
                    this.incorrectDate = true
                    this.setBackgroundInvalid(this.birthDay)
                }
            }
        } catch (e: ParseException) {
            this.incorrectDate = true
            this.setBackgroundInvalid(this.birthDay)
        }
    }

    protected fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    protected fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    protected fun isPasswordValid(password : String): Boolean {
        return password.isNotEmpty() && password.length >= MIN_PASSWORD_LENGTH
    }

    protected fun setBackgroundInvalid(editText : EditText) {
        editText.setBackgroundResource(R.drawable.bg_edit_input_invalid)
    }

    protected fun setBackgroundValid(editText : EditText) {
        editText.setBackgroundResource(R.drawable.bg_edit_input_valid)
    }

    protected fun onTextChanged(editText : EditText, function : () -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                function()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
        const val HAS_SPECIAL_SYMBOL = "^(?=.*[_.*=!%()$&@+-/]).*$"
        const val HAS_UPPER_CASE = ".*[A-Z].*"
        const val HAS_LOWER_CASE = ".*[a-z].*"
        const val HAS_NUMBER = ".*[0-9].*"
    }
}
