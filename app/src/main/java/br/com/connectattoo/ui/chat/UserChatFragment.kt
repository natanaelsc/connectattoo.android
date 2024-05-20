package br.com.connectattoo.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentUserChatBinding
import br.com.connectattoo.databinding.FragmentUserSearchBinding
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.ui.search.UserSearchViewModel

class UserChatFragment : BaseFragment<FragmentUserChatBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserChatBinding {
        return FragmentUserChatBinding.inflate(inflater, container, false)
    }

    override fun setupViews(){}



}
