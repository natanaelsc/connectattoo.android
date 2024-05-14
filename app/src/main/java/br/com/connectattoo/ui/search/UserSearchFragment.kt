package br.com.connectattoo.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import br.com.connectattoo.databinding.FragmentUserSearchBinding
import br.com.connectattoo.ui.BaseFragment

class UserSearchFragment : BaseFragment<FragmentUserSearchBinding>() {
    private lateinit var viewModel: UserSearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun setupViews() {
        val searchView = binding.fragmentUserSearch
        searchView.visibility = View.VISIBLE

        val recyclerSearch = binding.recyclerImagesSearch

        recyclerSearch.layoutManager = GridLayoutManager(context,3)

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentUserSearchBinding {
        viewModel = ViewModelProvider(this).get(UserSearchViewModel::class.java)
        return FragmentUserSearchBinding.inflate(inflater, container, false)
    }



}
