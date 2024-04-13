package br.com.connectattoo.ui.customerprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.connectattoo.databinding.FragmentCustomerProfileBinding
import br.com.connectattoo.ui.BaseFragment


class CustomerProfileFragment : BaseFragment<FragmentCustomerProfileBinding>() {
    @Suppress("EmptyFunctionBlock")
    override fun setupViews() {

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCustomerProfileBinding {
        return FragmentCustomerProfileBinding.inflate(inflater, container, false)
    }

}
