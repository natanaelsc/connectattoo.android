package br.com.connectattoo.ui.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class UserRegistration<T: ViewBinding> : Fragment() {

    private var _binding: T? = null
    protected val binding: T get() = _binding!!

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    	setupViews()
        setupSpecificViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected abstract fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T

    protected abstract fun setupSpecificViews()

    private fun setupViews() {}
}
