package br.com.connectattoo.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
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
}
