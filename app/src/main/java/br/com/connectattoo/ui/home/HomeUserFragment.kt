package br.com.connectattoo.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.connectattoo.adapter.AdapterListOfNearbyTattooArtists
import br.com.connectattoo.adapter.AdapterListOfRandomTattoos
import br.com.connectattoo.adapter.AdapterListOfTattoosBasedOnTags
import br.com.connectattoo.data.NearbyTattooArtists
import br.com.connectattoo.data.RandomTattoos
import br.com.connectattoo.data.TagBasedTattoos
import br.com.connectattoo.databinding.FragmentHomeUserBinding
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.util.PermissionUtils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

class HomeUserFragment : BaseFragment<FragmentHomeUserBinding>() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var checkLocation = false
    private val enableLocationActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_CANCELED) {
                if (PermissionUtils.isLocationEnabled(requireContext())) {
                    checkLocation = true
                } else {
                    Toast.makeText(requireContext(), "Localização não ativada!", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }


    private lateinit var adapterListOfTattoosBasedOnTags: AdapterListOfTattoosBasedOnTags
    private val listOfTattoosBasedOnTags: MutableList<TagBasedTattoos> = mutableListOf()
    private val tag = "Colorida"

    private lateinit var adapterListOfNearbyTattooartists: AdapterListOfNearbyTattooArtists
    private val listOfNearbyTattooArtists: MutableList<NearbyTattooArtists> = mutableListOf()

    private val tattooUrl = mutableListOf(
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_tesoura.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_cartas.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_passaro_na_mao.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_calavera.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_borboleta.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_lion.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_tartaruga.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_Pezkoi.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Ftattoo_rosto_cobras.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Ftattoo_escorpiao.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Ftattoo_olho.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Ftattoo_tigre.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Ftattoo_cobra.png"
    )

    private lateinit var adapterListOfRandomTattoos: AdapterListOfRandomTattoos
    private val listOfRandomTattoos: MutableList<RandomTattoos> = mutableListOf()

    private val profileImage = mutableListOf(
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fsecond_carousel%2Favatar%2Favatar_larissa_dias.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/" +
            "home%2Fsecond_carousel%2Favatar%2Favatar_marcus_freites.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/" +
            "home%2Fsecond_carousel%2Favatar%2Favatar_tatiana_oliveira.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/" +
            "home%2Fsecond_carousel%2Favatar%2Favatar_diogo_almeida.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Favatar%2Favatar_maria_carla.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Favatar%2Favatar_sara_ferreira.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Favatar%2Favatar_maya_tattoo.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Favatar%2Favatar_jose_fernades.png"
    )

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeUserBinding {
        return FragmentHomeUserBinding.inflate(inflater, container, false)

    }


    override fun setupViews() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        PermissionUtils.getPermissionAndLocationUser(
            requireActivity(),
            requireContext(),
            enableLocationActivityResult
        )
        //getLocationUser()
        val recycleViewListOfTattoosBasedOnTags = binding.recycleListOfTattoosBasedOnTags
        recycleViewListOfTattoosBasedOnTags.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        recycleViewListOfTattoosBasedOnTags.setHasFixedSize(true)
        adapterListOfTattoosBasedOnTags =
            AdapterListOfTattoosBasedOnTags(requireContext(), listOfTattoosBasedOnTags)
        recycleViewListOfTattoosBasedOnTags.adapter = adapterListOfTattoosBasedOnTags
        listOfTattoosBasedOnTags()

        val recycleListOfNearbyTattooartists = binding.recycleListOfNearbyTattooartists
        recycleListOfNearbyTattooartists.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        recycleListOfNearbyTattooartists.setHasFixedSize(true)
        adapterListOfNearbyTattooartists =
            AdapterListOfNearbyTattooArtists(requireContext(), listOfNearbyTattooArtists)
        recycleListOfNearbyTattooartists.adapter = adapterListOfNearbyTattooartists
        listOfNearbyTattooArtists()

        val recycleListOfRandomTattoos = binding.recycleListOfRandomTattoos
        recycleListOfRandomTattoos.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, false
        )
        recycleListOfRandomTattoos.setHasFixedSize(true)
        adapterListOfRandomTattoos =
            AdapterListOfRandomTattoos(requireContext(), listOfRandomTattoos)
        recycleListOfRandomTattoos.adapter = adapterListOfRandomTattoos
        listOfRandomTattoos()
    }


    private fun listOfTattoosBasedOnTags() {

        val tattooBasedOnTags1 = TagBasedTattoos(tattooUrl[0], tag)
        listOfTattoosBasedOnTags.add(tattooBasedOnTags1)

        val tattooBasedOnTags2 = TagBasedTattoos(tattooUrl[1], tag)
        listOfTattoosBasedOnTags.add(tattooBasedOnTags2)

        val tattooBasedOnTags3 = TagBasedTattoos(tattooUrl[2], tag)
        listOfTattoosBasedOnTags.add(tattooBasedOnTags3)

        val tattooBasedOnTags4 = TagBasedTattoos(tattooUrl[3], tag)
        listOfTattoosBasedOnTags.add(tattooBasedOnTags4)

        val tattooBasedOnTags5 = TagBasedTattoos(tattooUrl[4], tag)
        listOfTattoosBasedOnTags.add(tattooBasedOnTags5)
    }


    private fun listOfNearbyTattooArtists() {

        val nearbyTattooArtists = NearbyTattooArtists(
            tattooUrl[1],
            "Larissa Dias",
            "4,7",
            "São Paulo, Rua Calixto da M...",
            profileImage[0]
        )
        listOfNearbyTattooArtists.add(nearbyTattooArtists)

        val nearbyTattooartists2 = NearbyTattooArtists(
            tattooUrl[2],
            "Marcus Freites",
            "4,9",
            "São Paulo, Rua Dr. Neto de Ara...",
            profileImage[1]
        )
        listOfNearbyTattooArtists.add(nearbyTattooartists2)

        val nearbyTattooartists3 = NearbyTattooArtists(
            tattooUrl[3],
            "Tatiana Oliveira",
            "4,8",
            "portugal, Rua Calixto da M...",
            profileImage[2]
        )
        listOfNearbyTattooArtists.add(nearbyTattooartists3)

        val nearbyTattooartists4 = NearbyTattooArtists(
            tattooUrl[4],
            "Maria Carla",
            "4,6",
            "fortaleza, Rua Dr. Neto de Ara...",
            profileImage[4]
        )
        listOfNearbyTattooArtists.add(nearbyTattooartists4)

    }

    private fun listOfRandomTattoos() {

        val randomTattoos = RandomTattoos(
            tattooUrl[0], "Maria Carla", profileImage[4],
            like = true, save = true
        )
        listOfRandomTattoos.add(randomTattoos)

        val randomTattoos2 =
            RandomTattoos(
                tattooUrl[1], "Sarah Ferreira",
                profileImage[5], like = true, save = true
            )
        listOfRandomTattoos.add(randomTattoos2)

        val randomTattoos3 = RandomTattoos(
            tattooUrl[2], "Maya Tattoo", profileImage[6],
            like = true, save = true
        )
        listOfRandomTattoos.add(randomTattoos3)

        val randomTattoos4 = RandomTattoos(
            tattooUrl[3], "José Fer", profileImage[7],
            like = true, save = true
        )
        listOfRandomTattoos.add(randomTattoos4)

        val randomTattoos5 = RandomTattoos(
            tattooUrl[5], "Diogo Almeida", profileImage[3],
            like = true, save = true
        )
        listOfRandomTattoos.add(randomTattoos5)
    }


    override fun onResume() {
        super.onResume()
        requestLocationUser()
    }

    @SuppressLint("MissingPermission")
    private fun requestLocationUser() {
        if (checkLocation) {
            if (PermissionUtils.isLocationEnabled(requireContext())) {
                val result = fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                    CancellationTokenSource().token
                )
                result.addOnCompleteListener {
                    val location = "Latitude: " + it.result.latitude + "\n" +
                        "Longitude: " + it.result.longitude
                    Log.i("location", location)
                }
            }
        }
    }

}
