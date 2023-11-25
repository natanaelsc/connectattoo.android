package br.com.connectattoo.ui.registration

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.viewbinding.ViewBinding
import br.com.connectattoo.R
import br.com.connectattoo.ui.BaseFragment

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

    private fun isPasswordValid(password : String): Boolean {
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
