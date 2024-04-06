package br.com.connectattoo.ui.confirmation


import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.connectattoo.databinding.FragmentConfirmationBinding
import br.com.connectattoo.ui.BaseFragment

class ConfirmationFragment : BaseFragment<FragmentConfirmationBinding>() {

    @Suppress("EmptyFunctionBlock")
    override fun setupViews() {
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConfirmationBinding {
        return FragmentConfirmationBinding.inflate(inflater, container, false)
    }

}
