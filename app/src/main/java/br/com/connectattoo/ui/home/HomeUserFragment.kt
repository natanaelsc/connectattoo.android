package br.com.connectattoo.ui.home

import android.content.ContentValues.TAG
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.io.IOException

class HomeUserFragment : BaseFragment<FragmentHomeUserBinding>() {

    private lateinit var adapterListOfTattoosBasedOnTags: AdapterListOfTattoosBasedOnTags
    private val listOfTattoosBasedOnTags: MutableList<TagBasedTattoos> = mutableListOf()
    private val tag = "Colorida"

    private lateinit var adapterListOfNearbyTattooartists: AdapterListOfNearbyTattooArtists
    private val listOfNearbyTattooArtists: MutableList<NearbyTattooArtists> = mutableListOf()
    private lateinit var userRepository: UserRepository
    private val tattooUrl = mutableListOf(
        "https://s3-alpha-sig.figma.com/img/f5bc/bd49/a9723f878130f017973e9a92b5c4fc28?Expires=" +
            "1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=SIrz~DVdxLfDEIfz1~YhhWnDjii~" +
            "VhFetuEYi4YciGTpkisxaItbq6vGTwJH2xYhhZb-hn~lu5yLXJ8nZoFIO7wuDkRdRL6pbQhvHld1SgRT4EO" +
            "NDmMwxtvAghW0QVpJM2JErkaqImLQoExyOyXdHg~Mb3s2GCWOWFs3QB3WWdXQ9buT5GfrXWS6-FQuUmEw3OQ" +
            "xGeY2rru03GQDVO9--crXol5dAxh0vlWrD6DMBmpUxzrOx4A-cwoMnFAWDjxo7QJx3YhKZL1Cx9L7O52YoCX" +
            "yQqRHUymvQuk5VBVNThLtVXANd3ahpUXdANELwPCstL2oQF470fP1CLCW0kHUqg__",
        "https://s3-alpha-sig.figma.com/img/2047/1b67/25cbef44cc3082afc346f8f4b5f7e26f?Expires=" +
            "1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Y54rQhQawFE5oW3bQKQ3Av~a6oS-g7" +
            "OXls8NReUoE7~AK8dVjwOyddIswWyub9~zLxV1bCuz~wY9lNgICZynM1t0xAD9lIhmc9ejSroKmLLVkl9Jujp" +
            "7TZDteqrEj9xNyeatCoFZe4-q1chK3eMVbKwonBiHHiJBWiSYW2nn2vuhFkdyJw~FZiyMDYt9qh8h9vHz2Ghj" +
            "dEpb4hd3a7rannO3FQNYIFWFv3OD1KWimP5nh5jd8trz9GStnmkvfxDCtGfY9nUYWYJxohJF~DWr2T6UXnB69" +
            "T4OONVfQiXX2ak3QXaMzYyKCfPAqi-PyTuO2nnoyeiuhsqnEwO2PXbb6w__",
        "https://s3-alpha-sig.figma.com/img/1aa9/dc09/0d59edeccb8c79b67dc40b6629a42c46?Expires=" +
            "1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=LULgB3bCRUKAiNB~NieL7dMcQt4L6Z" +
            "nwSTjfCcKiiJ6vCeycZ8oMUaWs2wDvA9Cl9BoBrbiQjCIube8yqQ4NHN8mDsJ0vl75Lz6CY1fkPVtgpBL5oE" +
            "FWdCK-P3xJmmlJuHlanZEXPGdOj5BKCoLcGDtNhf4VxO~EN-KK~Lqc9YOjFmieO8koaK24ryOCH94T2KRS5m" +
            "tRHV815GYhROh2Kbi1iF9xrhFCHN2fZNAw5zdqoYcme0gqcgnPOwOO3xK~AAPhSh54-6WZq-09CDW1T5bXB9" +
            "iiVsFYJNiuO7qhpbHwnfRa0w3m02k3WhWjuCMCS2PPu4AzL9kdW6lILEO3aQ__",
        "https://s3-alpha-sig.figma.com/img/19d7/20ab/1d9a013eab27809957f0ff62b3be5223?Expires=" +
            "1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=fJWSBg8ImN-MkK4Kdnrgm~lmvbTtw" +
            "kr5RoNFX2Aft~Te0pi0bRAMrP0YDg09NdhH7vystNlIv9U0z4F1j8QTl9uCgGTSeOsyuaZMuwoeICRbPkcQz" +
            "k0cfcADnDD6tXH4X1VY-zOB8RiYq~0-TVnKFlj9oNhRPxZgEKTXeSGfb91K4zIMYzOBz9Mb8NZvt9eXytZY" +
            "qh8fKfXs-noAP~HCqufHUsZXSpa56nuPKOCjZh7lYxxn5Ebb7N9b-yOr8cTE-KaH35XYD~gK4Yi5G3eZLWSv" +
            "VJUpIPMKlQr050lTOoQMOlFwABzS8HHHwQYC4IWGsI-xF8hQswvligYjf4WnZw__",
        "https://s3-alpha-sig.figma.com/img/e486/b093/be0e43ff116ed1cef753f0a03cf8d90c?Expires=" +
            "1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=GEh925InrUia~yo9P1nCG6pkFowQkw" +
            "o1RreeJIlxH6Nt1d56noxQi7SkfKwycqMIX2~YnrRoAXzTgGkZ1bswVIAO1QFKacvJ79I5wSIUdlIWTlReu" +
            "w3nPrJVsper2Xzk8UgZ01WdfJdCROZPs-jJq95NFKa5Jz9QQtGS8iczgeu44r40gbReL-xOXcgsdcxTMAvI" +
            "rV8di3~~RKZHqSKd2VfFqYn4OIdg53gH7TyMQopSqREgdi3C59CT9USvIPOIGNe1McPNhcyHBEQLfttb9~X" +
            "GdErWUNE6kxtr4UmloRnja7PlmpRbJXFhZN9A1dhstD-CpLxY7sI0tOgUNIKXMA__"
    )
    private lateinit var adapterListOfRandomTattoos: AdapterListOfRandomTattoos
    private val listOfRandomTattoos: MutableList<RandomTattoos> = mutableListOf()

    private val profileImage = mutableListOf(
        "https://s3-alpha-sig.figma.com/img/6090/ae16/268a0c3f24ae8c3bf1ad6f0bac7cbd79?Expires=" +
            "1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=N-j4UegxorxDeEToWwmSuW~drOxb" +
            "AEvrijImeSN1HyRW~IaTWbQ7xqzRQS-1IlbY8QWz7Bu9qPPY7zei43x2q6UplBEGg5Rf4lMeaSCAMxxZx" +
            "NBdZr80Axezriv35Yqg3ibNVWh2txkcIXqaXm7X3SKA0dBq52-Sw92Tu5qoDveLSEEETL7bSmeaRV85W1" +
            "hw91g~4DjMYpb70-4BXb~DAhP1trTN-BisAai9H1EQecfZ7CeHLhIPOw-XjK1ihcDD5uJS58vhWkCgEZ2" +
            "vYVIZujE6I-TfIjG-ZYXUyrwNGCnE0OAE2E4n3eHkoA~rD8QOjeD4BdxjMrpOoXwvfD5RxQ__",
        "https://s3-alpha-sig.figma.com/img/eb3c/6be2/11db8a98f2353624477e310200a4e401?Expires=" +
            "1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=B448DRSAXb6gw2ZqTja1ssX0eBs" +
            "gwo9uxg4cLHDlBL9eeQsYlQ9S~Sh3s5lQ91fnhhkIGvLImJOk39nffwn0jAjpvjiagqNB6nCW4xrLQX8O" +
            "wf5c0r0TYbTIH7iWtRbyScKJlk6IH7BbShzpDuRMp7Qp9ak65oTxQd6ujphncPeqa35Wqd6jzVfGC5CTQ" +
            "t2-5SFD3B3t0T3WSg4Bh94U9b7jjx1wWZHMZkgkTyABBul9Vkl7e9yyARki2hHKPTAdkU27K3KsSmU9E" +
            "cAX3P742~o~ETsEAA5Ia2L5OnBxxfqJcgTSMa77kl6IacNeDKmlRQXw5gTF-~~26lPjRrYqrA__",
        "https://s3-alpha-sig.figma.com/img/76a2/c62e/be70d16d697e187131884dce35888e36?Expires=" +
            "1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=gM1QgIEaTzI9sQIIKWmP5Zc-oa8" +
            "wX7p-31ei~8oX56G5qjKus9OwsjfGV4LIOLneu3UExL6fQoKTtnVUuMRdj3pAjWTzFOyLMF-WdZn2yF" +
            "juRPeUpEEsmjlIzjMPBmoPIELVwndZim6FXC7DV2P1loMbOVy3in21j4lH7asc19ojFJGzLYhuNtEWx" +
            "0lcEUzIU1oE~jfmsfcULV93EdqtMEtZ20NbvRRtGsbkaMCKVCzZvU37ISTSyW6WR91aqnaqa8UI40F" +
            "c4A02WftIvUEez6gvHdcijceDa5M5SHkl4x0z5Gv0IlygLmaI2rsF7HQ8HuOFo6ExZRudkRZS~~JP3A__",
        "https://s3-alpha-sig.figma.com/img/38ab/233e/d540c618577c001b3a4a83f67e35158a?Expires=" +
            "1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=QVNXzJNYu7PO9-OruBrQl" +
            "260yAU8iIu~jkB4T8R8vvs7yTmqtU4aEiMm-KLem8N6jgowvVGqV0UkaCaIqPPhKD-FH7hrdGFx2" +
            "1OKMe57hcrY1S2HEtgGZzu0wxBNNIlWJjJXzfWe0k9EJS5LXGgCI3knoLCoY1oGwdwyRD4dE3ZXZ" +
            "edPgE-B9X9gEns7znXcHGbpzAysZ5~h1xN2fu9w~SRKLm6rM8Vgo0Xw1YSZS275pRIKsOimm7SmYj" +
            "jD03OJlH1He~2kdAlOQRGgEIdkCwb9Dlntjy1pJCsdp69UzKDDXA4tC8kcPpgnSh2nF2aVQ8RvfY~" +
            "ndq4W8iK5vQPQPw__",
        "https://s3-alpha-sig.figma.com/img/613e/697c/19a9e525817c00a0f009e8cd39f955d4?Expires=" +
            "1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=eHBEGuMPpjim35m4PYOPCQ5k0" +
            "Yk8eL2lcZUtoxf1sBVCXTXRKmFkalvVyETbaKUOnz61Mu6A5YmhMEXffLdwumzQFS6seZXdmf55rOV8J" +
            "s2z-GRY4ZfN9SJmfMy6smX0DsGQ2JngwlpgkohlQ0maZ7qt2~iSUvfJnvxt7Ud5Qz8f2BX~C2~YHyAKS" +
            "4nzvnkXQWu~yPYYgreICHbDycsEwUuYWeD1DGxt-iqbFQZjy9XNKuizdyGwWcPGDHs0~d8~obZNT-8" +
            "znEQR6pxibacyioE4AQiG1jf9PF~LBqi-LsL7MKD6v7iFof2Cm4RG87sKlx1oRyQaqfKeKQDubS4Idg__"
    )

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeUserBinding {
        return FragmentHomeUserBinding.inflate(inflater, container, false)
    }

    override fun setupViews() {
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
                    DataStoreManager.saveUserSettings(
                        requireContext(), API_USER_NAME,
                        profileUser.displayName
                    )

                    showUserName(profileUser.displayName)
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
            tattooUrl[0],
            "Larissa Dias",
            "4,7",
            "São Paulo, Rua Calixto da M...",
            profileImage[0]
        )
        listOfNearbyTattooArtists.add(nearbyTattooArtists)

        val nearbyTattooartists2 = NearbyTattooArtists(
            tattooUrl[1],
            "Marcus Freites",
            "4,9",
            "São Paulo, Rua Dr. Neto de Ara...",
            profileImage[2]
        )
        listOfNearbyTattooArtists.add(nearbyTattooartists2)

        val nearbyTattooartists3 = NearbyTattooArtists(
            tattooUrl[2],
            "Tatiana Oliveira",
            "4,8",
            "portugal, Rua Calixto da M...",
            profileImage[2]
        )
        listOfNearbyTattooArtists.add(nearbyTattooartists3)

        val nearbyTattooartists4 = NearbyTattooArtists(
            tattooUrl[3],
            "Maria Carla   ",
            "4,6",
            "fortaleza, Rua Dr. Neto de Ara...",
            profileImage[3]
        )
        listOfNearbyTattooArtists.add(nearbyTattooartists4)

        val nearbyTattooartists5 = NearbyTattooArtists(
            tattooUrl[4],
            "Sarah Ferreira",
            "4,5",
            "Rio, Rua Calixto da M...",
            profileImage[4]
        )
        listOfNearbyTattooArtists.add(nearbyTattooartists5)
    }

    private fun listOfRandomTattoos() {

        val randomTattoos = RandomTattoos(
            tattooUrl[0], "Maria Carla", profileImage[0],
            like = true, save = true
        )
        listOfRandomTattoos.add(randomTattoos)

        val randomTattoos2 =
            RandomTattoos(
                tattooUrl[1], "Sarah Ferreira",
                profileImage[1], like = true, save = true
            )
        listOfRandomTattoos.add(randomTattoos2)

        val randomTattoos3 = RandomTattoos(
            tattooUrl[2], "Maya Tattoo", profileImage[2],
            like = true, save = true
        )
        listOfRandomTattoos.add(randomTattoos3)

        val randomTattoos4 = RandomTattoos(
            tattooUrl[3], "José Fer", profileImage[3],
            like = true, save = true
        )
        listOfRandomTattoos.add(randomTattoos4)
    }

}
