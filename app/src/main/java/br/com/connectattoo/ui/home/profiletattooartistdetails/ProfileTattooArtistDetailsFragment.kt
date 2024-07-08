package br.com.connectattoo.ui.home.profiletattooartistdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.connectattoo.databinding.FragmentProfileTattooArtistDetailsBinding
import br.com.connectattoo.ui.BaseFragment

class ProfileTattooArtistDetailsFragment : BaseFragment<FragmentProfileTattooArtistDetailsBinding>() {
    override fun setupViews() {

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileTattooArtistDetailsBinding {
        return FragmentProfileTattooArtistDetailsBinding.inflate(inflater, container, false)
    }
}
