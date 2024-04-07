package br.com.connectattoo.ui.registration

import android.graphics.Color
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import br.com.connectattoo.R
import br.com.connectattoo.api.ApiService
import br.com.connectattoo.api.ApiUrl
import br.com.connectattoo.data.TokenData
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.util.Constants.API_TOKEN
import br.com.connectattoo.util.DataStoreManager
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Response
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

abstract class UserRegistration<T : ViewBinding> : BaseFragment<T>() {

    protected lateinit var name: EditText
    protected lateinit var email: EditText
    protected lateinit var password: EditText
    protected lateinit var confirmPassword: EditText
    protected lateinit var birthDay: EditText
    protected lateinit var terms: CheckBox

    protected lateinit var btnCreateAccount: View
    protected lateinit var btnCancel: View

    protected val apiService: ApiService = ApiUrl.instance.create(ApiService::class.java)

    protected var isChar = false
    protected var hasUpper = false
    protected var hasLow = false
    protected var hasNum = false
    protected var hasSpecialSymbol = false
    protected var incorrectConfirmPassword = true
    protected var correctPassword = false
    protected var incorrectDate = false
    protected var incorrectEmail = true
    protected var fieldsComplete = false

    abstract fun setupSpecificViews()

    override fun setupViews() {
        setupSpecificViews()

        name.setOnFocusChangeListener { _, focused ->
            if (!focused) validateName()
        }

        mask(this.birthDay, "NN/NN/NNNN")

        onTextChanged(email) { validateEmail() }

        onTextChanged(password) { validatePassword() }

        onTextChanged(confirmPassword) { validateConfirmPassword() }

        onTextChanged(birthDay) { validateBirthDay() }

    }

    private fun validateName() {
        val name = this.name.text.toString()
        if (this.isNameValid(name)) {
            this.setBackgroundValid(this.name)
        } else {
            this.setBackgroundInvalid(this.name)
        }
    }

    private fun validateEmail() {
        val email = this.email.text.toString()
        if (isEmailValid(email)) {
            this.incorrectEmail = false
            this.setBackgroundValid(this.email)
        } else {
            this.incorrectEmail = true
            this.setBackgroundInvalid(this.email)
        }
    }

    abstract fun validatePassword()

    abstract fun validateConfirmPassword()

    private fun validateBirthDay() {
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

    protected fun mask(editText: EditText, mask: String) {
        val simpleMaskFormatter = SimpleMaskFormatter(mask)
        val maskTextWatcher = MaskTextWatcher(editText, simpleMaskFormatter)

        editText.addTextChangedListener(maskTextWatcher)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    protected fun formatBirthDate(birthDate: String): String {
        val sourceFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val date = LocalDate.parse(birthDate, sourceFormatter)

        val targetFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return date.format(targetFormatter)
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    protected fun isPasswordValid(password: String): Boolean {
        return password.isNotEmpty() && password.length >= MIN_PASSWORD_LENGTH
    }

    protected fun setBackgroundInvalid(editText: EditText) {
        editText.setBackgroundResource(R.drawable.bg_edit_input_invalid)
    }

    protected fun setBackgroundValid(editText: EditText) {
        editText.setBackgroundResource(R.drawable.bg_edit_input_valid)
    }

    protected fun onTextChanged(editText: EditText, function: () -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                return
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                function()
            }

            override fun afterTextChanged(s: Editable?) {
                return
            }
        })
    }

    abstract fun conditionChecking(view: View)

    protected fun showValidationError(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.RED)
                .show()
        }
    }

    protected fun registrationResponse(
        action: Int,
        response: Response<TokenData>,
        onSuccess: () -> Unit
    ) {
        if (response.isSuccessful) {
            val responseBody = response.body()
            Log.d("Response", "Retorno: $responseBody")
            if (responseBody != null) {
                val token = responseBody.accessToken
                Log.d("Token", "Token: $token")
                response.body()?.let {
                    saveTokenApi(token)
                    findNavController().navigate(action)
                }
            }
        } else {
            when (response.code()) {
                404 -> showValidationError("A URL de destino não foi encontrada.")
                409 -> showValidationError("Email já cadastrado!!!")
                else -> showValidationError("Erro: ${response.code()}")
            }
        }
    }

    private fun saveTokenApi(token: String?) {
        viewLifecycleOwner.lifecycleScope.launch {
            if (token != null) {
                DataStoreManager.saveToken(requireContext(), API_TOKEN, token)
            }
        }
    }

    abstract fun validateUserRegistration(action: Int)

    @RequiresApi(Build.VERSION_CODES.O)
    protected fun btnCreateAccount(action: Int) {
        this.btnCreateAccount.setOnClickListener {

            conditionChecking(it)

            if (fieldsComplete) {
                validateUserRegistration(action)
            } else {
                showValidationError("Todos os campos devem ser preenchidos!")
            }
        }
    }

    protected fun btnCancel(action: Int) {
        this.btnCancel.setOnClickListener {
            findNavController().navigate(action)
        }
    }

    companion object {
        const val MIN_PASSWORD_LENGTH = 8
        const val HAS_SPECIAL_SYMBOL = "^(?=.*[_.*=!%()$&@+-/]).*$"
        const val HAS_UPPER_CASE = ".*[A-Z].*"
        const val HAS_LOWER_CASE = ".*[a-z].*"
        const val HAS_NUMBER = ".*[0-9].*"
    }
}
