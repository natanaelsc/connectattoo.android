package br.com.connectattoo.ui.profile.tattoclienttagfilter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import br.com.connectattoo.R
import br.com.connectattoo.adapter.AdapterListProfileFilterTags
import br.com.connectattoo.data.Tag
import br.com.connectattoo.databinding.FragmentTattooClientTagsFilterBinding
import br.com.connectattoo.ui.BaseFragment
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.snackbar.Snackbar


class TattooClientTagsFilterFragment : BaseFragment<FragmentTattooClientTagsFilterBinding>() {
    private lateinit var adapterListTagsProfile: AdapterListProfileFilterTags
    private val viewModel: TattooClientTagsFilterViewModel by viewModels()
    override fun setupViews() {
        setupRecyclerView()
        setListTags()
        viewModelObservers()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentTattooClientTagsFilterBinding {
        return FragmentTattooClientTagsFilterBinding.inflate(inflater, container, false)
    }

    private fun setupRecyclerView() {
        adapterListTagsProfile = AdapterListProfileFilterTags()

        val layoutManager = FlexboxLayoutManager(requireContext()).apply {
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.FLEX_START
        }

        binding.rvTagsInterests.apply {
            setHasFixedSize(true)
            this.layoutManager = layoutManager
            adapter = adapterListTagsProfile
        }

        adapterListTagsProfile.listenerTagProfile = { tag ->
            viewModel.selectTag(tag)
        }

    }

    private fun viewModelObservers() {
        viewModel.maximumTagsChecking.observe(viewLifecycleOwner) { checkMaximumTags ->
            if (checkMaximumTags) {
                Snackbar.make(
                    binding.root, getString(
                        R.string.txt_you_cannot_select_more_than_5_tags
                    ), Snackbar.LENGTH_LONG
                )
                    .setBackgroundTint(
                        ContextCompat.getColor(requireContext(), R.color.orange)
                    ).show()
            }
        }
    }

    private fun setListTags() {
        val list = listOf(
            Tag(id = "1", name = "Aquarela"),
            Tag(id = "2", name = "Biomecanica"),
            Tag(id = "3", name = "caligrafia"),
            Tag(id = "4", name = "geometrico"),
            Tag(id = "5", name = "horror"),
            Tag(id = "6", name = "mandala"),
            Tag(id = "6", name = "Minimalista"),
        )
        adapterListTagsProfile.submitList(list)
    }

}
