package br.com.connectattoo.ui.home.profiletattooartistdetails

import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import br.com.connectattoo.R
import br.com.connectattoo.adapter.AdapterListTattoosTattooArtistDetails
import br.com.connectattoo.data.Gallery
import br.com.connectattoo.data.NearbyTattooArtistsAndItemMore
import br.com.connectattoo.databinding.FragmentProfileTattooArtistDetailsBinding
import br.com.connectattoo.ui.BaseFragment
import com.bumptech.glide.Glide

class ProfileTattooArtistDetailsFragment :
    BaseFragment<FragmentProfileTattooArtistDetailsBinding>() {

    private lateinit var adapterListTattoosTattooArtistDetails: AdapterListTattoosTattooArtistDetails
    override fun setupViews() {
        setRecyclerView()
        setupDataTattooArtist()
        setupListeners()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileTattooArtistDetailsBinding {
        return FragmentProfileTattooArtistDetailsBinding.inflate(inflater, container, false)
    }

    private fun setupListeners() {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupDataTattooArtist() {
        val tattooArtist = arguments?.getSerializable("tattooArtist") as?
            NearbyTattooArtistsAndItemMore.NearbyTattooArtists
        binding.run {

            Glide.with(ivPhotoArtist).load(tattooArtist?.profileImage).circleCrop()
                .into(ivPhotoArtist)
            txtassessment.text = tattooArtist?.assessment
            txtNameArtist.text = tattooArtist?.name
            txtTypeArtist.text = "Artista de tatuagem"
            txtaddress.text = tattooArtist?.address

            val distance = "2,1"
            val formattedText = getString(R.string.txt_tattoo_artist_distance, distance)

            val colorStart = formattedText.indexOf(distance)
            val colorEnd = colorStart + distance.length + 3

            val spannableString = SpannableString(formattedText)

            val color = ContextCompat.getColor(requireContext(), R.color.purple500)
            spannableString.setSpan(
                ForegroundColorSpan(color),
                colorStart,
                colorEnd,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            txtDistanceArtist.text = spannableString
        }
    }

    private fun setRecyclerView() {
        val fakePhotosTattooArtist = listOf(
            Gallery(
                id = "1",
                name = "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_" +
                    "carousel%2Ftattoo_tesoura.png"
            ),
            Gallery(
                id = "2",
                name = "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_" +
                    "carousel%2Ftattoo_cartas.png"
            ),
            Gallery(
                id = "3",
                name = "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_" +
                    "carousel%2Ftattoo_passaro_na_mao.png"
            ),
            Gallery(
                id = "3",
                name = "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_" +
                    "carousel%2Ftattoo_calavera.png"
            )
        )
        binding.rvPhotosArtistWorks.run {
            setHasFixedSize(true)
            adapterListTattoosTattooArtistDetails = AdapterListTattoosTattooArtistDetails()
            adapter = adapterListTattoosTattooArtistDetails
            adapterListTattoosTattooArtistDetails.submitList(fakePhotosTattooArtist)

        }
    }

}
