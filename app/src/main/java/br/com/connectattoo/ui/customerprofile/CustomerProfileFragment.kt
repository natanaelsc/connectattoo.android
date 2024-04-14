package br.com.connectattoo.ui.customerprofile

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import br.com.connectattoo.adapter.AdapterListMyGalleries
import br.com.connectattoo.adapter.AdapterListTagsProfile
import br.com.connectattoo.databinding.FragmentCustomerProfileBinding
import br.com.connectattoo.ui.BaseFragment
import kotlinx.coroutines.launch


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
            Log.i(TAG, tagCustomerProfile.toString())
        }
        binding.rvMyGalleries.run {
            setHasFixedSize(true)
            adapter = adapterListMyGalleries
        }

    }

    private fun insertInformationCustomerInitial() {
        viewLifecycleOwner.lifecycleScope.launch {
            val listTagsProfile = viewModel.state.listTagsCustomerProfile

            adapterListTagsProfile.submitList(listTagsProfile)

        }
        viewLifecycleOwner.lifecycleScope.launch {
            val listMyGalleries = viewModel.state.listGalleriesCustomerProfile
            adapterListMyGalleries.submitList(listMyGalleries)

        }

        binding.run {
            txtNameUser.text = viewModel.state.txtNameUser
            txtAgeAndEmail.text = viewModel.state.txtAgeAndEmail
            txtNameTattooArtist.text = viewModel.state.txtNameTattooArtist
            txtTattoArtistProfile.text = viewModel.state.txtTattooArtistProfile
            txtScheduleTomorrow.text = viewModel.state.txtScheduleTomorrow
            txtScheduleHour.text = viewModel.state.txtScheduleHour
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

}
