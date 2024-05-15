package br.com.connectattoo.ui.home

import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.connectattoo.R
import br.com.connectattoo.adapter.AdapterListOfNearbyTattooArtists
import br.com.connectattoo.adapter.AdapterListOfRandomTattoos
import br.com.connectattoo.adapter.AdapterListOfTattoosBasedOnTags
import br.com.connectattoo.data.NearbyTattooArtists
import br.com.connectattoo.data.RandomTattoos
import br.com.connectattoo.data.TagBasedTattoos
import br.com.connectattoo.databinding.FragmentHomeUserBinding
import br.com.connectattoo.repository.UserRepository
import br.com.connectattoo.ui.BaseFragment
import br.com.connectattoo.util.Constants
import br.com.connectattoo.util.Constants.API_TOKEN
import br.com.connectattoo.util.Constants.API_USER_NAME
import br.com.connectattoo.util.DataStoreManager
import br.com.connectattoo.util.GpsStatusListener
import br.com.connectattoo.util.PermissionUtils
import br.com.connectattoo.util.TurnOnGps
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.io.IOException

@Suppress("TooManyFunctions")
@RequiresApi(Build.VERSION_CODES.M)
class HomeUserFragment : BaseFragment<FragmentHomeUserBinding>() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var checkLocation = false


    private lateinit var adapterListOfTattoosBasedOnTags: AdapterListOfTattoosBasedOnTags
    private val listOfTattoosBasedOnTags: MutableList<TagBasedTattoos> = mutableListOf()
    private val tag = "Colorida"

    private lateinit var adapterListOfNearbyTattooartists: AdapterListOfNearbyTattooArtists
    private val listOfNearbyTattooArtists: MutableList<NearbyTattooArtists> = mutableListOf()

    private lateinit var userRepository: UserRepository

    private val tattooByTagsUrl = mutableListOf(
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_tesoura.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_cartas.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_passaro_na_mao.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Ffirst_carousel%2Ftattoo_calavera.png",
    )

    private val tattooByNearbyArtistsUrl = mutableListOf(
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fsecond_carousel%2Ftattoo_borboleta.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fsecond_carousel%2Ftattoo_lion.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fsecond_carousel%2Ftattoo_tartaruga.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fsecond_carousel%2Ftattoo_Pezkoi.png",
    )

    private val randomTattoosUrl = mutableListOf(
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Ftattoo_rosto_cobras.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Ftattoo_escorpiao.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Ftattoo_olho.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Ftattoo_tigre.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Ftattoo_cobra.png"
    )

    private lateinit var adapterListOfRandomTattoos: AdapterListOfRandomTattoos
    private val listOfRandomTattoos: MutableList<RandomTattoos> = mutableListOf()

    private val nearbyProfileImage = mutableListOf(
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fsecond_carousel%2Favatar%2Favatar_larissa_dias.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/" +
            "home%2Fsecond_carousel%2Favatar%2Favatar_marcus_freites.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home" +
            "%2Fsecond_carousel%2Favatar%2Favatar_tatiana_oliveira.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fsecond_carousel%2Favatar%2Favatar_diogo_almeida.png"
    )

    private val randomProfileImage = mutableListOf(
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Favatar%2Favatar_maria_carla.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Favatar%2Favatar_sara_ferreira.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Favatar%2Favatar_maya_tattoo.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fthird_carousel%2Favatar%2Favatar_jose_fernades.png",
        "https://pub-777ce89a8a3641429d92a32c49eac191.r2.dev/home%2Fsecond_carousel%2Favatar%2Favatar_diogo_almeida.png"
    )
    private val enableLocationActivityResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            Log.i("result", activityResult.resultCode.toString())
        } else if (activityResult.resultCode == RESULT_CANCELED) {
            Log.i("resultNotAccept", activityResult.resultCode.toString())
        }

    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeUserBinding {
        return FragmentHomeUserBinding.inflate(inflater, container, false)

    }

    override fun setupViews() {
        observerAndRequestGpsTurnOn()

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
        userRepository = UserRepository()
        setUserName()
    }

    private fun observerAndRequestGpsTurnOn() {
        val gpsStatusListener = GpsStatusListener(requireContext())
        val turnOnGps = TurnOnGps(requireContext())
        var isGpsStatusChanged: Boolean? = null
        gpsStatusListener.observe(this) { isGpsOn ->

            if (isGpsStatusChanged == null) {

                if (!isGpsOn) {
                    turnOnGps.startGps(enableLocationActivityResult)
                }
                isGpsStatusChanged = isGpsOn

            } else {
                if (isGpsStatusChanged != isGpsOn) {
                    if (!isGpsOn) {
                        turnOnGps.startGps(enableLocationActivityResult)

                    }
                    isGpsStatusChanged = isGpsOn
                }

            }
        }
    }

    private fun setUserName() {
        viewLifecycleOwner.lifecycleScope.launch {
            val nameUser = DataStoreManager.getUserSettings(requireContext(), API_USER_NAME)
            if (nameUser.isEmpty()) {
                val token = DataStoreManager.getUserSettings(requireContext(), API_TOKEN)
                getUserNameFromApi(token)
            } else {
                showUserName(nameUser)
            }
        }
    }

    private suspend fun getUserNameFromApi(token: String) {
        try {
            apiRequest(token)
        } catch (error: IOException) {
            Log.i(TAG, error.message.toString())
            showUserName("")
        }
    }

    private suspend fun apiRequest(token: String) {
        val result = userRepository.getProfileUser(token)

        if (result.isSuccessful) {
            result.body().let { profileUser ->
                if (profileUser != null) {
                    val firstName = profileUser.displayName.split(" ")[0]
                    DataStoreManager.saveUserSettings(
                        requireContext(), API_USER_NAME,
                        firstName
                    )

                    showUserName(firstName)
                }
            }
        } else {
            when (result.code()) {
                Constants.CODE_ERROR_404 -> showUserName("")
                Constants.CODE_ERROR_401 -> showUserName("")
                else -> {
                    showValidationError("Erro: ${result.code()}")
                    showUserName("")
                }
            }
        }
    }

    private fun showUserName(name: String) {

        if (name.isNotEmpty()) {
            binding.txtName.text = getString(R.string.txt_hello_user_home, name)
        } else {
            binding.txtName.text = getString(R.string.txt_hello_user_home_error)
        }
    }

    private fun showValidationError(message: String) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.RED)
                .show()
        }
    }

    private fun listOfTattoosBasedOnTags() {

        val tattooBasedOnTags1 = TagBasedTattoos(tattooByTagsUrl[0], tag)
        listOfTattoosBasedOnTags.add(tattooBasedOnTags1)

        val tattooBasedOnTags2 = TagBasedTattoos(tattooByTagsUrl[1], tag)
        listOfTattoosBasedOnTags.add(tattooBasedOnTags2)

        val tattooBasedOnTags3 = TagBasedTattoos(tattooByTagsUrl[2], tag)
        listOfTattoosBasedOnTags.add(tattooBasedOnTags3)

        val tattooBasedOnTags4 = TagBasedTattoos(tattooByTagsUrl[3], tag)
        listOfTattoosBasedOnTags.add(tattooBasedOnTags4)

    }


    private fun listOfNearbyTattooArtists() {

        val nearbyTattooArtists = NearbyTattooArtists(
            tattooByNearbyArtistsUrl[0],
            "Larissa Dias",
            "4,7",
            "São Paulo, Rua Calixto da M...",
            nearbyProfileImage[0]
        )
        listOfNearbyTattooArtists.add(nearbyTattooArtists)

        val nearbyTattooartists2 = NearbyTattooArtists(
            tattooByNearbyArtistsUrl[1],
            "Marcus Freites",
            "4,9",
            "São Paulo, Rua Dr. Neto de Ara...",
            nearbyProfileImage[1]
        )
        listOfNearbyTattooArtists.add(nearbyTattooartists2)

        val nearbyTattooartists3 = NearbyTattooArtists(
            tattooByNearbyArtistsUrl[2],
            "Tatiana Oliveira",
            "4,8",
            "portugal, Rua Calixto da M...",
            nearbyProfileImage[2]
        )
        listOfNearbyTattooArtists.add(nearbyTattooartists3)

        val nearbyTattooartists4 = NearbyTattooArtists(
            tattooByNearbyArtistsUrl[3],
            "Diogo Almeida",
            "4,5",
            "São Paulo, Rua Baltazar Lisb...",
            nearbyProfileImage[3]
        )
        listOfNearbyTattooArtists.add(nearbyTattooartists4)

    }

    private fun listOfRandomTattoos() {

        val randomTattoos = RandomTattoos(
            randomTattoosUrl[0], "Maria Carla",
            randomProfileImage[0], like = true, save = true
        )
        listOfRandomTattoos.add(randomTattoos)

        val randomTattoos2 =
            RandomTattoos(
                randomTattoosUrl[1], "Sarah Ferreira",
                randomProfileImage[1], like = true, save = true
            )
        listOfRandomTattoos.add(randomTattoos2)

        val randomTattoos3 = RandomTattoos(
            randomTattoosUrl[2], "Maya Tattoo",
            randomProfileImage[2], like = true, save = true
        )
        listOfRandomTattoos.add(randomTattoos3)

        val randomTattoos4 = RandomTattoos(
            randomTattoosUrl[3], "José Fer",
            randomProfileImage[3], like = true, save = true
        )
        listOfRandomTattoos.add(randomTattoos4)

        val randomTattoos5 = RandomTattoos(
            randomTattoosUrl[4], "Diogo Almeida",
            randomProfileImage[4], like = true, save = true
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
