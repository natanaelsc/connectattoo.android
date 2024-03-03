package br.com.connectattoo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.connectattoo.R
import br.com.connectattoo.databinding.FragmentHomeUserBinding
import br.com.connectattoo.ui.BaseFragment


class HomeUserFragment : BaseFragment<FragmentHomeUserBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeUserBinding {
        return FragmentHomeUserBinding.inflate(inflater, container, false)
    }

    override fun setupViews() {}







}
