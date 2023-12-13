package br.com.connectattoo.ui.registration

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import br.com.connectattoo.R
import br.com.connectattoo.ui.BaseFragment
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.snackbar.Snackbar
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
    protected lateinit var cep : EditText
    protected lateinit var street : EditText
    protected lateinit var number : EditText
    protected lateinit var city : EditText
    protected lateinit var state : EditText
    protected lateinit var birthDay : EditText
    protected lateinit var terms : CheckBox

    protected lateinit var btnCreateAccount : View
    protected lateinit var btnCancel : View

	protected var isChar = false
	protected var hasUpper = false
	protected var hasLow = false
	protected var hasNum = false
	protected var hasSpecialSymbol = false
	protected var incorrectConfirmPassword = true
	protected var correctPassword = false
    private var incorrectDate = false
    private var incorrectEmail = true

    abstract fun setupSpecificViews()

    override fun setupViews() {
        setupSpecificViews()

        name.setOnFocusChangeListener{ _, focused ->
            if (!focused) validateName()
        }

        mask(this.birthDay, "NN/NN/NNNN")

        onTextChanged(email) { validateEmail() }

        onTextChanged(password) { validatePassword() }

        onTextChanged(confirmPassword) { validateConfirmPassword() }

        onTextChanged(cep) { validateCep() }

        onTextChanged(street) { validateStreet() }

        onTextChanged(number) { validateNumber() }

        onTextChanged(city) { validateCity() }

        onTextChanged(state) { validateState() }

        onTextChanged(birthDay) { validateBirthDay() }

        btnCreateAccount()
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

    private fun validateCep(){
        val cep = this.cep.text.toString()
        if(isCepValid(cep)){
            this.setBackgroundValid(this.cep)
        } else {
            this.setBackgroundInvalid(this.cep)
        }
    }

    private fun validateStreet(){
        val street = this.street.text.toString()
        if(isStreetValid(street)){
            this.setBackgroundValid(this.street)
        }else{
            this.setBackgroundInvalid(this.street)
        }
    }

    private fun validateNumber(){
        val number = this.number.text.toString()
        if(isNumberValid(number)){
            this.setBackgroundValid(this.number)
        }else{
            this.setBackgroundInvalid(this.number)
        }
    }

    private fun validateCity(){
        val city = this.city.text.toString()
        if(isCityValid(city)){
            this.setBackgroundValid(this.city)
        }else{
            this.setBackgroundInvalid(this.city)
        }
    }

    private fun validateState(){
        val state = this.state.text.toString()
        if(isStateValid(state)){
            this.setBackgroundValid(this.state)
        }else{
            this.setBackgroundInvalid(this.state)
        }
    }

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

    private fun mask(editText: EditText, mask: String) {
        val simpleMaskFormatter = SimpleMaskFormatter(mask)
        val maskTextWatcher = MaskTextWatcher(editText, simpleMaskFormatter)

        editText.addTextChangedListener(maskTextWatcher)
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

    private fun isCepValid(cep: String): Boolean {
        return cep.isNotEmpty()
    }

    private fun isStreetValid(street: String): Boolean{
        return street.isNotEmpty()
    }

    private fun isNumberValid(number: String): Boolean{
        return number.isNotEmpty()
    }

    private fun  isCityValid(city: String): Boolean{
        return city.isNotEmpty()
    }

    private fun isStateValid(state: String): Boolean{
        return state.isNotEmpty()
    }

    protected fun setBackgroundInvalid(editText: EditText) {
        editText.setBackgroundResource(R.drawable.bg_edit_input_invalid)
    }

    protected fun setBackgroundValid(editText: EditText) {
        editText.setBackgroundResource(R.drawable.bg_edit_input_valid)
    }

    private fun onTextChanged(editText: EditText, function : () -> Unit) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { return }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                function()
            }
            override fun afterTextChanged(s: Editable?) { return }
        })
    }

    private fun btnCreateAccount() {
        this.btnCreateAccount.setOnClickListener {
            val name = this.name.text.toString()
            val termsChecked = this.terms.isChecked
            if (name.isEmpty() || incorrectEmail || !correctPassword || !termsChecked || incorrectConfirmPassword || incorrectDate) {
                val snackBar = Snackbar.make(it, "Todos os campos devem ser preenchidos!", Snackbar.LENGTH_SHORT)
                snackBar.setTextColor(Color.WHITE)
                snackBar.setBackgroundTint(Color.RED)
                snackBar.show()
            }
        }
    }

    protected fun btnCancel(action : Int) {
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
