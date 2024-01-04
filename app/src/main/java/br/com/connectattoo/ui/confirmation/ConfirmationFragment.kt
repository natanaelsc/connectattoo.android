package br.com.connectattoo.ui.confirmation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentConfirmationBinding
import br.com.connectattoo.ui.BaseFragment

class ConfirmationFragment : BaseFragment<FragmentConfirmationBinding>() {

    override fun setupViews() {
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentConfirmationBinding {
        return FragmentConfirmationBinding.inflate(inflater, container, false)
    }

}
