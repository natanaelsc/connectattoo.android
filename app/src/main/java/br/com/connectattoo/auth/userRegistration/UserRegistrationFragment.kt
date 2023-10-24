package br.com.connectattoo.auth.userRegistration

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentUserRegistrationBinding
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.snackbar.Snackbar
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date


class UserRegistrationFragment : Fragment() {

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var password : EditText
    private lateinit var confirmPassword : EditText
    private lateinit var date : EditText
    private lateinit var confirmEmail : EditText
    private lateinit var name : EditText

    private var isChar = false
    private var hasUpper = false
    private var hasLow = false
    private var hasNum = false
    private var hasSpecialSymbol = false
    private var incorrectConfirmPassword = true
    private var correctPassword = false
    private var incorrectDate = false
    private var incorrectEmail = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRegistrationBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        password = binding.editTextPassword
        confirmPassword = binding.editTexConfirmPassword
        confirmEmail = binding.editTexEmail
        date = binding.editTexDate
        name = binding.editTextName

        inputPassword()
        inputPasswordconfirm()
        dateMask()
        validateEmail()
        validatingDate()
        nameFocusListener()



        binding.btCreateAccount.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val checkBox = binding.checkBox
            var checked:Boolean

            if (checkBox.isChecked) {checked = true}
            else {  checked = false }

           isValidatingDate()
            confirmPassword()
            isEmailValid()
            validPassword()
            isValidatName()



            if ((name.isEmpty()) ||  (incorrectEmail == true)  || (correctPassword == false ) ||  (checked == false) || (incorrectConfirmPassword == true) || (incorrectDate == true)){


                val snackbar =
                    Snackbar.make(it, "verificar se todos os campos foram preenchidos corretamente!", Snackbar.LENGTH_SHORT)
                snackbar.setTextColor(Color.WHITE)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {

            }
        }

        binding.btCancel.setOnClickListener {
            findNavController().navigate(R.id.action_userRegistrationFragment_to_welcomeFragment)
        }


    }


    private fun nameFocusListener(){
        binding.editTextName.setOnFocusChangeListener{ _,focused ->
            if (!focused){
                isValidatName()
            }

        }
    }

    private fun isValidatName() {
        val name = binding.editTextName.text.toString()
        if (name.isEmpty()){
            binding.editTextName.setBackgroundResource(R.drawable.bg_edit_input_invalid)
        }else{
            binding.editTextName.setBackgroundResource(R.drawable.bg_edit_input_valid)
        }
    }


    private fun validatingDate(){
        date.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                isValidatingDate()
            }

            override fun afterTextChanged(s: Editable?) { }
        })

    }

    private fun isValidatingDate(){

        val date = binding.editTexDate.text.toString()

        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        dateFormat.isLenient = false

        try {
            val parsedDate = dateFormat.parse(date)
            if (parsedDate != null) {
                val currentDate = Date()
                val calendar = Calendar.getInstance()
                calendar.time = parsedDate
                val year = calendar.get(Calendar.YEAR)

                if (year >= 1000 && year <= currentDate.year + 1900) {
                    binding.txtInforErrorDate.visibility = View.GONE
                    incorrectDate = false
                    binding.editTexDate.setBackgroundResource(R.drawable.bg_edit_input_valid)
                } else {
                    throw ParseException("", 0)
                }

                if (!parsedDate.after(currentDate)) {
                    binding.txtInforErrorDate.visibility = View.GONE
                    incorrectDate = false
                    binding.editTexDate.setBackgroundResource(R.drawable.bg_edit_input_valid)
                } else {
                    binding.txtInforErrorDate.visibility = View.VISIBLE
                    incorrectDate = true
                    binding.editTexDate.setBackgroundResource(R.drawable.bg_edit_input_invalid)
                }
            }
        } catch (e: ParseException) {
            binding.txtInforErrorDate.visibility = View.VISIBLE
            incorrectDate = true
            binding.editTexDate.setBackgroundResource(R.drawable.bg_edit_input_invalid)
        }

    }


    private fun dateMask(){
         date = binding.editTexDate

        val SimpleMaskFormatter = SimpleMaskFormatter("NN/NN/NNNN")
        val MaskTextWatcher = MaskTextWatcher(date, SimpleMaskFormatter)
        date.addTextChangedListener(MaskTextWatcher)
    }


    @SuppressLint("SuspiciousIndentation")
    private fun validPassword()
    {
        val password = password.text.toString()
        if(password.length <8)
        {
            isChar = true
            binding.txtConditionsPassword.visibility = View.VISIBLE
            binding.txtpasswordNotCharacteristics.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtpasswordFeature.visibility = View.GONE
            binding.txtMinimumCharacters.setTextColor(Color.RED)
            binding.ImgCloseMinimumCharacters.visibility = View.VISIBLE
            binding.ImgCheckMinimumCharacters.visibility = View.GONE

        }else {
            isChar = false
            binding.txtMinimumCharacters.setTextColor(Color.GREEN)
            binding.ImgCheckMinimumCharacters.visibility = View.VISIBLE
            binding.ImgCloseMinimumCharacters.visibility = View.GONE
        }
        if(!password.matches(Regex("^(?=.*[_.*=!%()$&@+-/]).*$"))) {
            hasSpecialSymbol = true
            binding.txtpasswordNotCharacteristics.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtpasswordFeature.visibility = View.GONE
            binding.txtSpecialSymbol.setTextColor(Color.RED)
            binding.ImgCloseSpecialSymbol.visibility = View.VISIBLE
            binding.ImgCheckSpecialSymbol.visibility = View.GONE
        }else  {
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
        }  else {
            hasUpper = false
            binding.txtCapitalLetter.setTextColor(Color.GREEN)
            binding.ImgCheckCapitalLetter.visibility = View.VISIBLE
            binding.ImgCloseCapitalLetter.visibility = View.GONE
        }
        if(!password.matches(".*[a-z].*".toRegex())) {
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
        if (!password.matches(".*[0-9].*".toRegex())){
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
        if(isChar == false &&  hasNum == false && hasSpecialSymbol == false && hasUpper == false && hasLow == false ){

            binding.txtpasswordNotCharacteristics.visibility = View.GONE
            binding.linearLayout.visibility = View.GONE
            binding.txtpasswordFeature.visibility = View.VISIBLE
            correctPassword = true
            binding.editTextPassword.setBackgroundResource(R.drawable.bg_edit_input_valid)

        }else{
            binding.editTextPassword.setBackgroundResource(R.drawable.bg_edit_input_invalid)
        }

    }

    private fun inputPassword()  {
        password.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validPassword()


            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun inputPasswordconfirm()  {
        confirmPassword.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                confirmPassword()
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }

    private fun confirmPassword(){
        val  confirmPassword = binding.editTexConfirmPassword.text.toString()
        val password = binding.editTextPassword.text.toString()
        if (confirmPassword.isEmpty()){
            incorrectConfirmPassword = true
            binding.txtconfirmPasswordError.visibility = View.GONE
            binding.editTexConfirmPassword.setBackgroundResource(R.drawable.bg_edit_input_invalid)

        }else    if (password != confirmPassword){
            incorrectConfirmPassword = true
            binding.txtconfirmPasswordError.visibility = View.VISIBLE
            binding.editTexConfirmPassword.setBackgroundResource(R.drawable.bg_edit_input_invalid)

        }else{
            incorrectConfirmPassword = false
            binding.txtconfirmPasswordError.visibility = View.GONE
            binding.editTexConfirmPassword.setBackgroundResource(R.drawable.bg_edit_input_valid)
        }
    }

    private fun validateEmail(){
        confirmEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                isEmailValid()
            }

            override fun afterTextChanged(s: Editable?) { }
        })

    }

    fun isEmailValid() {
        val  email = binding.editTexEmail.text.toString()

        if(email.isEmpty()){
            binding.editTexEmail.setBackgroundResource(R.drawable.bg_edit_input_invalid)
            incorrectEmail = true
        }else  if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editTexEmail.setBackgroundResource(R.drawable.bg_edit_input_invalid)
           incorrectEmail = true
        }else{
            binding.editTexEmail.setBackgroundResource(R.drawable.bg_edit_input_valid)
           incorrectEmail = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}