package br.com.connectattoo.ui.customerprofile

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import br.com.connectattoo.adapter.AdapterListMyGalleries
import br.com.connectattoo.adapter.AdapterListTagsProfile
import br.com.connectattoo.data.MyGalleryProfile
import br.com.connectattoo.data.TagCustomerProfile
import br.com.connectattoo.databinding.FragmentCustomerProfileBinding
import br.com.connectattoo.ui.BaseFragment


class CustomerProfileFragment : BaseFragment<FragmentCustomerProfileBinding>() {
    private lateinit var adapterListTagsProfile: AdapterListTagsProfile
    private var adapterListMyGalleries = AdapterListMyGalleries()
    private val viewModel: CustomerProfileFragmentViewModel by viewModels()
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCustomerProfileBinding {
        return FragmentCustomerProfileBinding.inflate(inflater, container, false)
    }

    override fun setupViews() {
        setupRecyclerView()
        dataFake()
        insertInformationCustomerInitial()
        setupBtnClicks()
    }

    private fun setupRecyclerView() {
        adapterListTagsProfile = AdapterListTagsProfile()
        binding.rvTagsInterests.run {
            setHasFixedSize(true)
            adapter = adapterListTagsProfile
        }
        adapterListTagsProfile.listenerTagProfile = { tagCustomerProfile ->

            Toast.makeText(requireContext(), tagCustomerProfile.tag, Toast.LENGTH_SHORT).show()
        }
        binding.rvMyGalleries.run {
            setHasFixedSize(true)
            adapter = adapterListMyGalleries
        }


    }

    private fun insertInformationCustomerInitial() {
        viewModel.getUserInformation()
        binding.run {
            //btnUserImage.setImageBitmap()
            txtNameUser.text = "João Silva"
            txtAgeAndEmail.text = "32 Anos | @joaosilva@gmail.com"
            txtNameTattooArtist.text = "Larissa Diniz"
            txtTattoArtistProfile.text = "@lari_tattoo"
            txtScheduleTomorrow.text = "Amanhã"
            txtScheduleHour.text = "11:45"
            //ivImageTattooArtist.setBackgroundResource()
        }
    }

    private fun setupBtnClicks() {
        binding.run {

            btnUserImage.setOnClickListener {
            }
            btnSettings.setOnClickListener {
            }
            btnEditProfile.setOnClickListener {
            }
            btnManageInterests.setOnClickListener {
            }
            btnManageNextAppointment.setOnClickListener {
            }
            btnMoreInfoNextScheduling.setOnClickListener {
            }
            btnManageGallery.setOnClickListener {
            }
        }

    }

    private fun dataFake() {
        val listTags = listOf(
            TagCustomerProfile(tag = "Preto e branco"),
            TagCustomerProfile(tag = "Tag 2"),
            TagCustomerProfile(tag = "Tag e branco"),
            TagCustomerProfile(tag = "Tag e sinza"),
            TagCustomerProfile(tag = "Tag e 3")
        )

        adapterListTagsProfile.submitList(listTags)

        val listImages = listOf(
            MyGalleryProfile(1,
                title = "Galeria 1",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png"

            ),
            MyGalleryProfile(2,
                title = "Galeria 2",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png"

            ),
            MyGalleryProfile(3,
                title = "Galeria 3",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png"

            ),
            MyGalleryProfile(4,
                title = "Galeria 4",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png",
                "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst" +
                    "_carousel%2Ftattoo_cartas.png"

            ),
        )
        adapterListMyGalleries.submitList(listImages)
    }
}
