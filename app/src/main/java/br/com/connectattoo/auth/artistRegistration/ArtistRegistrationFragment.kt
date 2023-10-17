package br.com.connectattoo.auth.artistRegistration

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import br.com.connectattoo.databinding.FragmentArtistRegistrationBinding
import com.github.rtoshiro.util.format.SimpleMaskFormatter
import com.github.rtoshiro.util.format.text.MaskTextWatcher
import com.google.android.material.snackbar.Snackbar

class ArtistRegistrationFragment : Fragment() {

    private var _binding: FragmentArtistRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var senha : EditText
    private lateinit var confirmPassword : EditText
    private lateinit var data : EditText

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
        _binding = FragmentArtistRegistrationBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        senha = binding.SenhaEditText
        confirmPassword = binding.editconfirmPassword

        inputPassword()
        inputPasswordconfirm()
        dateMask()



        binding.btCriarConta.setOnClickListener {
            val nome = binding.editNome.text.toString()
            val email = binding.editEmail.text.toString()
            val Password = binding.SenhaEditText.text.toString()
            val confirmPassword = binding.editconfirmPassword.text.toString()
            val data = binding.editData.text.toString()

            val checkBox = binding.checkBox
            var checked:Boolean

            if (checkBox.isChecked) {checked = true}
            else {  checked = false }

            validatingDate()


            if ((nome.isEmpty()) || (email.isEmpty()) || (Password.isEmpty()) || (confirmPassword.isEmpty()) || (data.isEmpty()) || ( correctPassword == false) || (checked == false) || (Password != confirmPassword) || (incorrectDate == true)){
                val snackbar =
                    Snackbar.make(it, "verificar se todos os campos foram preenchidos corretamente!", Snackbar.LENGTH_SHORT)
                snackbar.setTextColor(Color.WHITE)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            } else {

            }
        }
    }

    private fun validatingDate(){
        val data = binding.editData.text.toString()
        if (data.length <10){
            binding.txtInforErrorData.visibility = View.VISIBLE
            incorrectDate = true
        }else{
            binding.txtInforErrorData.visibility = View.GONE
            incorrectDate = false
        }
    }


    private fun dateMask(){
        data = binding.editData
        val smf = SimpleMaskFormatter("NN/NN/NNNN")
        val mtw = MaskTextWatcher(data, smf)
        data.addTextChangedListener(mtw)
    }


    @SuppressLint("SuspiciousIndentation")
    private fun validPassword()
    {

        val password = senha.text.toString()
        if(password.length <8)
        {
            is8char = true
            binding.txtInforError.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtInforPadrao.visibility = View.GONE
            binding.txtCaracteresMinimo.setTextColor(Color.RED)
            binding.ImgCloseCaracteresMinimmo.visibility = View.VISIBLE
            binding.ImgCheckCaracteresMinimmo.visibility = View.GONE

        }else {
            is8char = false
            binding.txtCaracteresMinimo.setTextColor(Color.GREEN)
            binding.ImgCheckCaracteresMinimmo.visibility = View.VISIBLE
            binding.ImgCloseCaracteresMinimmo.visibility = View.GONE
        }
        if(!password.matches(Regex("^(?=.*[_.*=!%()$&@+-/]).*$"))) {
            hasSpecialSymbol = true
            binding.txtInforError.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtInforPadrao.visibility = View.GONE
            binding.txtSimboloEspecial.setTextColor(Color.RED)
            binding.ImgCloseSimboloEspecial.visibility = View.VISIBLE
            binding.ImgCheckSimboloEspecial.visibility = View.GONE
        }else  {
            hasSpecialSymbol = false
            binding.txtSimboloEspecial.setTextColor(Color.GREEN)
            binding.ImgCheckSimboloEspecial.visibility = View.VISIBLE
            binding.ImgCloseSimboloEspecial.visibility = View.GONE
        }
        if (!password.matches(".*[A-Z].*".toRegex())) {
            hasUpper = true
            binding.txtInforError.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtInforPadrao.visibility = View.GONE
            binding.txtLetraMaiuscula.setTextColor(Color.RED)
            binding.ImgCloseLetraMaiuscula.visibility = View.VISIBLE
            binding.ImgCheckLetraMaiuscula.visibility = View.GONE
        }  else {
            hasUpper = false
            binding.txtLetraMaiuscula.setTextColor(Color.GREEN)
            binding.ImgCheckLetraMaiuscula.visibility = View.VISIBLE
            binding.ImgCloseLetraMaiuscula.visibility = View.GONE
        }
        if(!password.matches(".*[a-z].*".toRegex())) {
            haslow = true
            binding.txtInforError.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtInforPadrao.visibility = View.GONE
            binding.txtLetraMinuscula.setTextColor(Color.RED)
            binding.ImgCloseLetraMinuscula.visibility = View.VISIBLE
            binding.ImgCheckLetraMinuscula.visibility = View.GONE
        } else {
            haslow = false
            binding.txtLetraMinuscula.setTextColor(Color.GREEN)
            binding.ImgCheckLetraMinuscula.visibility = View.VISIBLE
            binding.ImgCloseLetraMinuscula.visibility = View.GONE
        }
        if (!password.matches(".*[0-9].*".toRegex())){
            hasNum = true
            binding.txtInforError.visibility = View.VISIBLE
            binding.linearLayout.visibility = View.VISIBLE
            binding.txtInforPadrao.visibility = View.GONE
            binding.txtNumero.setTextColor(Color.RED)
            binding.ImgCloseNumero.visibility = View.VISIBLE
            binding.ImgCheckNumero.visibility = View.GONE
        } else {
            hasNum = false
            binding.txtNumero.setTextColor(Color.GREEN)
            binding.ImgCloseNumero.visibility = View.GONE
            binding.ImgCheckNumero.visibility = View.VISIBLE
        }
        if(is8char == false &&  hasNum == false && hasSpecialSymbol == false && hasUpper == false && haslow == false ){

            binding.txtInforError.visibility = View.GONE
            binding.linearLayout.visibility = View.GONE
            binding.txtInforPadrao.visibility = View.VISIBLE
            correctPassword = true
        }

    }

    private fun inputPassword()  {
        senha.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                validPassword()
                if(is8char &&  hasNum && hasSpecialSymbol && hasUpper && haslow ){
                    binding.txtInforError.visibility = View.GONE
                    binding.linearLayout.visibility = View.GONE
                    binding.txtInforPadrao.visibility = View.VISIBLE
                }

            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }


    private fun inputPasswordconfirm()  {
        confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                confirmPassword()
            }

            override fun afterTextChanged(s: Editable?) { }
        })
    }

    private fun confirmPassword(){
        val  confirmPassword = binding.editconfirmPassword.text.toString()
        val senha = binding.SenhaEditText.text.toString()
        if (confirmPassword.isEmpty()){
            inforPassword = false
            binding.txtconfirmPasswordInfor.visibility = View.GONE

        }else    if (senha != confirmPassword){
            inforPassword = true
            binding.txtconfirmPasswordInfor.visibility = View.VISIBLE

        }else{
            inforPassword = false
            binding.txtconfirmPasswordInfor.visibility = View.GONE
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}