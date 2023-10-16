package br.com.connectattoo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import br.com.connectattoo.auth.artistRegistration.ArtistRegistrationFragment
import br.com.connectattoo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        fragmentRender(R.id.containerFragment,ArtistRegistrationFragment())
    }

    private fun fragmentRender(containerId: Int, fragment: Fragment){
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(containerId,fragment)
        fragmentTransaction.commit()
    }
}