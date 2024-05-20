package br.com.connectattoo.ui.chat


import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.connectattoo.databinding.FragmentUserChatBinding
import br.com.connectattoo.ui.BaseFragment


class UserChatFragment : BaseFragment<FragmentUserChatBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserChatBinding {
        return FragmentUserChatBinding.inflate(inflater, container, false)
    }

    override fun setupViews() = Unit



}
