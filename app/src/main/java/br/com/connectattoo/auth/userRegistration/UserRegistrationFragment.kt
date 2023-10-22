package br.com.connectattoo.auth.userRegistration

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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


class UserRegistrationFragment : Fragment() {

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var password : EditText
    private lateinit var confirmPassword : EditText
    private lateinit var date : EditText
    private lateinit var confirmEmail : EditText

    private var is8char = false
    private var hasUpper = false
    private var haslow = false
    private var hasNum = false
    private var hasSpecialSymbol = false
    private var inforPassword = false
    private var correctPassword = false
    private var incorrectDate = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserRegistrationBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        password = binding.EditTextpassword
        confirmPassword = binding.editconfirmPassword
        confirmEmail = binding.editEmail

        inputPassword()
        inputPasswordconfirm()
        dateMask()
        validateEmail()



        binding.btCreateAccount.setOnClickListener {
            val name = binding.editName.text.toString()
            val email = binding.editEmail.text.toString()
            val Password = binding.EditTextpassword.text.toString()
            val confirmPassword = binding.editconfirmPassword.text.toString()
            val date = binding.editDate.text.toString()

            val checkBox = binding.checkBox
            var checked:Boolean

            if (checkBox.isChecked) {checked = true}
            else {  checked = false }

            validatingDate()


            if ((name.isEmpty()) || (email.isEmpty()) || (Password.isEmpty()) || (confirmPassword.isEmpty()) || (date.isEmpty()) || ( correctPassword == false) || (checked == false) || (Password != confirmPassword) || (incorrectDate == true)){
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

    private fun validatingDate(){
        val data = binding.editDate.text.toString()
        if (data.length <10){
            binding.txtInforErrorDate.visibility = View.VISIBLE
            incorrectDate = true
        }else{
            binding.txtInforErrorDate.visibility = View.GONE
            incorrectDate = false
        }
    }


    private fun dateMask(){
         date = binding.editDate
        val smf = SimpleMaskFormatter("NN/NN/NNNN")
        val mtw = MaskTextWatcher(date, smf)
        date.addTextChangedListener(mtw)
    }


    @SuppressLint("SuspiciousIndentation")
    private fun validPassword()
    {
        val password = password.text.toString()
        if(password.length <8)
        {
            is8char = true
            binding.txtConditionsPassword.visibility = View.VISIBLE
            binding.txtpasswordNotCharacteristics.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtpasswordFeature.visibility = View.GONE
            binding.txtMinimumCharacters.setTextColor(Color.RED)
            binding.ImgCloseMinimumCharacters.visibility = View.VISIBLE
            binding.ImgCheckMinimumCharacters.visibility = View.GONE

        }else {
            is8char = false
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
            haslow = true
            binding.txtpasswordNotCharacteristics.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtpasswordFeature.visibility = View.GONE
            binding.txtLowerCase.setTextColor(Color.RED)
            binding.ImgCloseLowerCase.visibility = View.VISIBLE
            binding.ImgCheckLowerCase.visibility = View.GONE
        } else {
            haslow = false
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
        if(is8char == false &&  hasNum == false && hasSpecialSymbol == false && hasUpper == false && haslow == false ){

            binding.txtpasswordNotCharacteristics.visibility = View.GONE
            binding.linearLayout.visibility = View.GONE
            binding.txtpasswordFeature.visibility = View.VISIBLE
            correctPassword = true
        }

    }

    private fun inputPassword()  {
        password.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validPassword()
                if(is8char &&  hasNum && hasSpecialSymbol && hasUpper && haslow ){
                    binding.txtpasswordNotCharacteristics.visibility = View.GONE
                    binding.linearLayout.visibility = View.GONE
                    binding.txtpasswordFeature.visibility = View.VISIBLE
                }

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
        val  confirmPassword = binding.editconfirmPassword.text.toString()
        val password = binding.EditTextpassword.text.toString()
        if (confirmPassword.isEmpty()){
            inforPassword = false
            binding.txtconfirmPasswordError.visibility = View.GONE

        }else    if (password != confirmPassword){
            inforPassword = true
            binding.txtconfirmPasswordError.visibility = View.VISIBLE

        }else{
            inforPassword = false
            binding.txtconfirmPasswordError.visibility = View.GONE
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
        val  email = binding.editEmail.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.editEmail.setBackgroundResource(R.drawable.bg_edit_input_invalid)
        }else{
            binding.editEmail.setBackgroundResource(R.drawable.bg_edit_input_valid)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}