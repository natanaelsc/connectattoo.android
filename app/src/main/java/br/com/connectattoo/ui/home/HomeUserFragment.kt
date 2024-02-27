package br.com.connectattoo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.connectattoo.R
import br.com.connectattoo.adapter.AdapterListOfNearbyTattooartists
import br.com.connectattoo.adapter.AdapterListOfTattoosBasedOnTags
import br.com.connectattoo.data.NearbyTattooArtists
import br.com.connectattoo.data.TagBasedTattoos
import br.com.connectattoo.databinding.FragmentHomeUserBinding
import com.bumptech.glide.Glide


class HomeUserFragment : Fragment() {

    private var _binding: FragmentHomeUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapterListOfTattoosBasedOnTags:AdapterListOfTattoosBasedOnTags
    private val listOfTattoosBasedOnTags:MutableList<TagBasedTattoos> = mutableListOf()

    private lateinit var adapterListOfNearbyTattooartists:AdapterListOfNearbyTattooartists
    private val listOfNearbyTattooartists:MutableList<NearbyTattooArtists> = mutableListOf()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeUserBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recycleViewListOfTattoosBasedOnTags = binding.recycleListOfTattoosBasedOnTags
        recycleViewListOfTattoosBasedOnTags.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        recycleViewListOfTattoosBasedOnTags.setHasFixedSize(true)
        adapterListOfTattoosBasedOnTags = AdapterListOfTattoosBasedOnTags(requireContext(),listOfTattoosBasedOnTags)
        recycleViewListOfTattoosBasedOnTags.adapter = adapterListOfTattoosBasedOnTags
        listOfTattoosBasedOnTags()


        val recycleListOfNearbyTattooartists = binding.recycleListOfNearbyTattooartists
        recycleListOfNearbyTattooartists.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        recycleListOfNearbyTattooartists.setHasFixedSize(true)
        adapterListOfNearbyTattooartists = AdapterListOfNearbyTattooartists(requireContext(),listOfNearbyTattooartists)
        recycleListOfNearbyTattooartists.adapter = adapterListOfNearbyTattooartists
        listOfNearbyTattooartists()
    }



    fun listOfTattoosBasedOnTags(){

        val tattoUrl = "https://s3-alpha-sig.figma.com/img/f5bc/bd49/a9723f878130f017973e9a92b5c4fc28?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=SIrz~DVdxLfDEIfz1~YhhWnDjii~VhFetuEYi4YciGTpkisxaItbq6vGTwJH2xYhhZb-hn~lu5yLXJ8nZoFIO7wuDkRdRL6pbQhvHld1SgRT4EONDmMwxtvAghW0QVpJM2JErkaqImLQoExyOyXdHg~Mb3s2GCWOWFs3QB3WWdXQ9buT5GfrXWS6-FQuUmEw3OQxGeY2rru03GQDVO9--crXol5dAxh0vlWrD6DMBmpUxzrOx4A-cwoMnFAWDjxo7QJx3YhKZL1Cx9L7O52YoCXyQqRHUymvQuk5VBVNThLtVXANd3ahpUXdANELwPCstL2oQF470fP1CLCW0kHUqg__"
        val tattoUrl2 = "https://s3-alpha-sig.figma.com/img/2047/1b67/25cbef44cc3082afc346f8f4b5f7e26f?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Y54rQhQawFE5oW3bQKQ3Av~a6oS-g7OXls8NReUoE7~AK8dVjwOyddIswWyub9~zLxV1bCuz~wY9lNgICZynM1t0xAD9lIhmc9ejSroKmLLVkl9Jujp7TZDteqrEj9xNyeatCoFZe4-q1chK3eMVbKwonBiHHiJBWiSYW2nn2vuhFkdyJw~FZiyMDYt9qh8h9vHz2GhjdEpb4hd3a7rannO3FQNYIFWFv3OD1KWimP5nh5jd8trz9GStnmkvfxDCtGfY9nUYWYJxohJF~DWr2T6UXnB69T4OONVfQiXX2ak3QXaMzYyKCfPAqi-PyTuO2nnoyeiuhsqnEwO2PXbb6w__"
        val tattoUrl3 = "https://s3-alpha-sig.figma.com/img/1aa9/dc09/0d59edeccb8c79b67dc40b6629a42c46?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=LULgB3bCRUKAiNB~NieL7dMcQt4L6ZnwSTjfCcKiiJ6vCeycZ8oMUaWs2wDvA9Cl9BoBrbiQjCIube8yqQ4NHN8mDsJ0vl75Lz6CY1fkPVtgpBL5oEFWdCK-P3xJmmlJuHlanZEXPGdOj5BKCoLcGDtNhf4VxO~EN-KK~Lqc9YOjFmieO8koaK24ryOCH94T2KRS5mtRHV815GYhROh2Kbi1iF9xrhFCHN2fZNAw5zdqoYcme0gqcgnPOwOO3xK~AAPhSh54-6WZq-09CDW1T5bXB9iiVsFYJNiuO7qhpbHwnfRa0w3m02k3WhWjuCMCS2PPu4AzL9kdW6lILEO3aQ__"
        val tattoUrl4 = "https://s3-alpha-sig.figma.com/img/19d7/20ab/1d9a013eab27809957f0ff62b3be5223?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=fJWSBg8ImN-MkK4Kdnrgm~lmvbTtwkr5RoNFX2Aft~Te0pi0bRAMrP0YDg09NdhH7vystNlIv9U0z4F1j8QTl9uCgGTSeOsyuaZMuwoeICRbPkcQzk0cfcADnDD6tXH4X1VY-zOB8RiYq~0-TVnKFlj9oNhRPxZgEKTXeSGfb91K4zIMYzOBz9Mb8NZvt9eXytZYqh8fKfXs-noAP~HCqufHUsZXSpa56nuPKOCjZh7lYxxn5Ebb7N9b-yOr8cTE-KaH35XYD~gK4Yi5G3eZLWSvVJUpIPMKlQr050lTOoQMOlFwABzS8HHHwQYC4IWGsI-xF8hQswvligYjf4WnZw__"
        val tattoUrl5 = "https://s3-alpha-sig.figma.com/img/e486/b093/be0e43ff116ed1cef753f0a03cf8d90c?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=GEh925InrUia~yo9P1nCG6pkFowQkwo1RreeJIlxH6Nt1d56noxQi7SkfKwycqMIX2~YnrRoAXzTgGkZ1bswVIAO1QFKacvJ79I5wSIUdlIWTlReuw3nPrJVsper2Xzk8UgZ01WdfJdCROZPs-jJq95NFKa5Jz9QQtGS8iczgeu44r40gbReL-xOXcgsdcxTMAvIrV8di3~~RKZHqSKd2VfFqYn4OIdg53gH7TyMQopSqREgdi3C59CT9USvIPOIGNe1McPNhcyHBEQLfttb9~XGdErWUNE6kxtr4UmloRnja7PlmpRbJXFhZN9A1dhstD-CpLxY7sI0tOgUNIKXMA__"

        val tattoBasedOnTags1 = TagBasedTattoos(tattoUrl,"Colorida")
        listOfTattoosBasedOnTags.add(tattoBasedOnTags1)

        val tattoBasedOnTags2 = TagBasedTattoos(tattoUrl2,"Colorida")
        listOfTattoosBasedOnTags.add(tattoBasedOnTags2)

        val tattoBasedOnTags3 = TagBasedTattoos(tattoUrl3,"Colorida")
        listOfTattoosBasedOnTags.add(tattoBasedOnTags3)

        val tattoBasedOnTags4 = TagBasedTattoos(tattoUrl4,"Colorida")
        listOfTattoosBasedOnTags.add(tattoBasedOnTags4)

        val tattoBasedOnTags5 = TagBasedTattoos(tattoUrl5,"Colorida")
        listOfTattoosBasedOnTags.add(tattoBasedOnTags5)
    }


    fun listOfNearbyTattooartists(){

        val tattoUrl = "https://s3-alpha-sig.figma.com/img/12fd/f78c/7b522f8818516656e08c0ed9919b438f?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=EFuVislRyCcj23rYJZ8e9wObupbh-jKPJgVE6iBDY~xvV-vrkxHL6vLj-fM8rDcSitW8dMVRgEY~pL5kCvYoK~TII2rCRA1mAms2SPyXxpIXUrSQOnEqisGBxnPArBTNqt0Jp7oSaxSPOm8HqnIo7sNMUb4WYNJYVneJkWksxpCxxBCC7v80NNNpJ98Z8S3UxRKawVJhtXGJATRslvJEM3mHWIfSK7JY0GIpmbWcnMcI78racuDEZzwzPvahgftp4OYBx78Xduoq9XuIsILn4t81k~MP4SXHVT0NkaE5~YmzQS-yirTiP2w7os9PTqOI7tpJ~3tbKoYAybEonAYO1w__"
        val tattoUrl2 = "https://s3-alpha-sig.figma.com/img/2047/1b67/25cbef44cc3082afc346f8f4b5f7e26f?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=Y54rQhQawFE5oW3bQKQ3Av~a6oS-g7OXls8NReUoE7~AK8dVjwOyddIswWyub9~zLxV1bCuz~wY9lNgICZynM1t0xAD9lIhmc9ejSroKmLLVkl9Jujp7TZDteqrEj9xNyeatCoFZe4-q1chK3eMVbKwonBiHHiJBWiSYW2nn2vuhFkdyJw~FZiyMDYt9qh8h9vHz2GhjdEpb4hd3a7rannO3FQNYIFWFv3OD1KWimP5nh5jd8trz9GStnmkvfxDCtGfY9nUYWYJxohJF~DWr2T6UXnB69T4OONVfQiXX2ak3QXaMzYyKCfPAqi-PyTuO2nnoyeiuhsqnEwO2PXbb6w__"
        val tattoUrl3 = "https://s3-alpha-sig.figma.com/img/1aa9/dc09/0d59edeccb8c79b67dc40b6629a42c46?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=LULgB3bCRUKAiNB~NieL7dMcQt4L6ZnwSTjfCcKiiJ6vCeycZ8oMUaWs2wDvA9Cl9BoBrbiQjCIube8yqQ4NHN8mDsJ0vl75Lz6CY1fkPVtgpBL5oEFWdCK-P3xJmmlJuHlanZEXPGdOj5BKCoLcGDtNhf4VxO~EN-KK~Lqc9YOjFmieO8koaK24ryOCH94T2KRS5mtRHV815GYhROh2Kbi1iF9xrhFCHN2fZNAw5zdqoYcme0gqcgnPOwOO3xK~AAPhSh54-6WZq-09CDW1T5bXB9iiVsFYJNiuO7qhpbHwnfRa0w3m02k3WhWjuCMCS2PPu4AzL9kdW6lILEO3aQ__"
        val tattoUrl4 = "https://s3-alpha-sig.figma.com/img/19d7/20ab/1d9a013eab27809957f0ff62b3be5223?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=fJWSBg8ImN-MkK4Kdnrgm~lmvbTtwkr5RoNFX2Aft~Te0pi0bRAMrP0YDg09NdhH7vystNlIv9U0z4F1j8QTl9uCgGTSeOsyuaZMuwoeICRbPkcQzk0cfcADnDD6tXH4X1VY-zOB8RiYq~0-TVnKFlj9oNhRPxZgEKTXeSGfb91K4zIMYzOBz9Mb8NZvt9eXytZYqh8fKfXs-noAP~HCqufHUsZXSpa56nuPKOCjZh7lYxxn5Ebb7N9b-yOr8cTE-KaH35XYD~gK4Yi5G3eZLWSvVJUpIPMKlQr050lTOoQMOlFwABzS8HHHwQYC4IWGsI-xF8hQswvligYjf4WnZw__"
        val tattoUrl5 = "https://s3-alpha-sig.figma.com/img/e486/b093/be0e43ff116ed1cef753f0a03cf8d90c?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=GEh925InrUia~yo9P1nCG6pkFowQkwo1RreeJIlxH6Nt1d56noxQi7SkfKwycqMIX2~YnrRoAXzTgGkZ1bswVIAO1QFKacvJ79I5wSIUdlIWTlReuw3nPrJVsper2Xzk8UgZ01WdfJdCROZPs-jJq95NFKa5Jz9QQtGS8iczgeu44r40gbReL-xOXcgsdcxTMAvIrV8di3~~RKZHqSKd2VfFqYn4OIdg53gH7TyMQopSqREgdi3C59CT9USvIPOIGNe1McPNhcyHBEQLfttb9~XGdErWUNE6kxtr4UmloRnja7PlmpRbJXFhZN9A1dhstD-CpLxY7sI0tOgUNIKXMA__"

        val profileImage = "https://s3-alpha-sig.figma.com/img/613e/697c/19a9e525817c00a0f009e8cd39f955d4?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=eHBEGuMPpjim35m4PYOPCQ5k0Yk8eL2lcZUtoxf1sBVCXTXRKmFkalvVyETbaKUOnz61Mu6A5YmhMEXffLdwumzQFS6seZXdmf55rOV8Js2z-GRY4ZfN9SJmfMy6smX0DsGQ2JngwlpgkohlQ0maZ7qt2~iSUvfJnvxt7Ud5Qz8f2BX~C2~YHyAKS4nzvnkXQWu~yPYYgreICHbDycsEwUuYWeD1DGxt-iqbFQZjy9XNKuizdyGwWcPGDHs0~d8~obZNT-8znEQR6pxibacyioE4AQiG1jf9PF~LBqi-LsL7MKD6v7iFof2Cm4RG87sKlx1oRyQaqfKeKQDubS4Idg__"
        val profileImage2 = "https://s3-alpha-sig.figma.com/img/38ab/233e/d540c618577c001b3a4a83f67e35158a?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=QVNXzJNYu7PO9-OruBrQl260yAU8iIu~jkB4T8R8vvs7yTmqtU4aEiMm-KLem8N6jgowvVGqV0UkaCaIqPPhKD-FH7hrdGFx21OKMe57hcrY1S2HEtgGZzu0wxBNNIlWJjJXzfWe0k9EJS5LXGgCI3knoLCoY1oGwdwyRD4dE3ZXZedPgE-B9X9gEns7znXcHGbpzAysZ5~h1xN2fu9w~SRKLm6rM8Vgo0Xw1YSZS275pRIKsOimm7SmYjjD03OJlH1He~2kdAlOQRGgEIdkCwb9Dlntjy1pJCsdp69UzKDDXA4tC8kcPpgnSh2nF2aVQ8RvfY~ndq4W8iK5vQPQPw__"
        val profileImage3 = "https://s3-alpha-sig.figma.com/img/76a2/c62e/be70d16d697e187131884dce35888e36?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=gM1QgIEaTzI9sQIIKWmP5Zc-oa8wX7p-31ei~8oX56G5qjKus9OwsjfGV4LIOLneu3UExL6fQoKTtnVUuMRdj3pAjWTzFOyLMF-WdZn2yFjuRPeUpEEsmjlIzjMPBmoPIELVwndZim6FXC7DV2P1loMbOVy3in21j4lH7asc19ojFJGzLYhuNtEWx0lcEUzIU1oE~jfmsfcULV93EdqtMEtZ20NbvRRtGsbkaMCKVCzZvU37ISTSyW6WR91aqnaqa8UI40Fc4A02WftIvUEez6gvHdcijceDa5M5SHkl4x0z5Gv0IlygLmaI2rsF7HQ8HuOFo6ExZRudkRZS~~JP3A__"
        val profileImage4 = "https://s3-alpha-sig.figma.com/img/eb3c/6be2/11db8a98f2353624477e310200a4e401?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=B448DRSAXb6gw2ZqTja1ssX0eBsgwo9uxg4cLHDlBL9eeQsYlQ9S~Sh3s5lQ91fnhhkIGvLImJOk39nffwn0jAjpvjiagqNB6nCW4xrLQX8Owf5c0r0TYbTIH7iWtRbyScKJlk6IH7BbShzpDuRMp7Qp9ak65oTxQd6ujphncPeqa35Wqd6jzVfGC5CTQt2-5SFD3B3t0T3WSg4Bh94U9b7jjx1wWZHMZkgkTyABBul9Vkl7e9yyARki2hHKPTAdkU27K3KsSmU9EcAX3P742~o~ETsEAA5Ia2L5OnBxxfqJcgTSMa77kl6IacNeDKmlRQXw5gTF-~~26lPjRrYqrA__"
        val profileImage5 = "https://s3-alpha-sig.figma.com/img/6090/ae16/268a0c3f24ae8c3bf1ad6f0bac7cbd79?Expires=1710115200&Key-Pair-Id=APKAQ4GOSFWCVNEHN3O4&Signature=N-j4UegxorxDeEToWwmSuW~drOxbAEvrijImeSN1HyRW~IaTWbQ7xqzRQS-1IlbY8QWz7Bu9qPPY7zei43x2q6UplBEGg5Rf4lMeaSCAMxxZxNBdZr80Axezriv35Yqg3ibNVWh2txkcIXqaXm7X3SKA0dBq52-Sw92Tu5qoDveLSEEETL7bSmeaRV85W1hw91g~4DjMYpb70-4BXb~DAhP1trTN-BisAai9H1EQecfZ7CeHLhIPOw-XjK1ihcDD5uJS58vhWkCgEZ2vYVIZujE6I-TfIjG-ZYXUyrwNGCnE0OAE2E4n3eHkoA~rD8QOjeD4BdxjMrpOoXwvfD5RxQ__"

        val nearbyTattooartists = NearbyTattooArtists(tattoUrl,"Larissa Dias","4,7","São Paulo, Rua Calixto da M...",profileImage)
        listOfNearbyTattooartists.add(nearbyTattooartists)

        val nearbyTattooartists2 = NearbyTattooArtists(tattoUrl2,"Marcus Freites","4,9","São Paulo, Rua Dr. Neto de Ara...",profileImage2)
        listOfNearbyTattooartists.add(nearbyTattooartists2)

        val nearbyTattooartists3 = NearbyTattooArtists(tattoUrl3,"Tatiana Oliveira","4,8","São Paulo, Rua Calixto da M...",profileImage3)
        listOfNearbyTattooartists.add(nearbyTattooartists3)

        val nearbyTattooartists4 = NearbyTattooArtists(tattoUrl4,"Maria Carla   ","4,6","São Paulo, Rua Dr. Neto de Ara...",profileImage4)
        listOfNearbyTattooartists.add(nearbyTattooartists4)

        val nearbyTattooartists5 = NearbyTattooArtists(tattoUrl5,"Sarah Ferreira","4,5","São Paulo, Rua Calixto da M...",profileImage5)
        listOfNearbyTattooartists.add(nearbyTattooartists5)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
