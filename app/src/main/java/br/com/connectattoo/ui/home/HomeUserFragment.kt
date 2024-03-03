package br.com.connectattoo.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.connectattoo.R
import br.com.connectattoo.adapter.AdapterListOfTattoosBasedOnTags
import br.com.connectattoo.data.TagBasedTattoos
import br.com.connectattoo.databinding.FragmentHomeUserBinding
import br.com.connectattoo.databinding.FragmentWelcomeBinding
import br.com.connectattoo.ui.BaseFragment
import com.bumptech.glide.Glide


class HomeUserFragment : BaseFragment<FragmentHomeUserBinding>() {



    private lateinit var adapterListOfTattoosBasedOnTags:AdapterListOfTattoosBasedOnTags
    private val listOfTattoosBasedOnTags:MutableList<TagBasedTattoos> = mutableListOf()
    override fun setupViews() {

        val recycleViewListOfTattoosBasedOnTags = binding.recycleListOfTattoosBasedOnTags
        recycleViewListOfTattoosBasedOnTags.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL,false)
        recycleViewListOfTattoosBasedOnTags.setHasFixedSize(true)
        adapterListOfTattoosBasedOnTags = AdapterListOfTattoosBasedOnTags(requireContext(),listOfTattoosBasedOnTags)
        recycleViewListOfTattoosBasedOnTags.adapter = adapterListOfTattoosBasedOnTags
        listOfTattoosBasedOnTags()
    }

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeUserBinding {
        return FragmentHomeUserBinding.inflate(inflater, container, false)
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



}
