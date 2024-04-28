package br.com.connectattoo


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.connectattoo.databinding.ActivityHomeUserBinding


class HomeUserActivity  : AppCompatActivity() {
    private lateinit var binding: ActivityHomeUserBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_user_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener{controller, destination, arguments ->

            if (destination.id == R.id.homeUserFragment){
                binding.homeTrue.visibility = View.VISIBLE
                binding.chatTrue.visibility = View.INVISIBLE
                binding.explorarTrue.visibility = View.INVISIBLE
                binding.profileTrue.visibility = View.INVISIBLE
            }else if (destination.id == R.id.confirmationFragment4){
                binding.homeTrue.visibility = View.INVISIBLE
                binding.chatTrue.visibility = View.VISIBLE
                binding.explorarTrue.visibility = View.INVISIBLE
                binding.profileTrue.visibility = View.INVISIBLE

            }else  if (destination.id == R.id.tattooClientRegistrationFragment ){
                binding.homeTrue.visibility = View.INVISIBLE
                binding.chatTrue.visibility = View.INVISIBLE
                binding.explorarTrue.visibility = View.VISIBLE
                binding.profileTrue.visibility = View.INVISIBLE

            }else if (destination.id == R.id.clientUserProfileFragment){
                binding.homeTrue.visibility = View.INVISIBLE
                binding.chatTrue.visibility = View.INVISIBLE
                binding.explorarTrue.visibility = View.INVISIBLE
                binding.profileTrue.visibility = View.VISIBLE
            }
        }

        binding.homeFalse.setOnClickListener {
            navController.navigate(R.id.homeUserFragment)
            binding.homeTrue.visibility = View.VISIBLE
            binding.chatTrue.visibility = View.INVISIBLE
            binding.explorarTrue.visibility = View.INVISIBLE
            binding.profileTrue.visibility = View.INVISIBLE
        }

        binding.chatFalse.setOnClickListener{
            navController.navigate(R.id.confirmationFragment4)
            binding.homeTrue.visibility = View.INVISIBLE
            binding.chatTrue.visibility = View.VISIBLE
            binding.explorarTrue.visibility = View.INVISIBLE
            binding.profileTrue.visibility = View.INVISIBLE
        }

        binding.explorarFalse.setOnClickListener {
            navController.navigate(R.id.tattooClientRegistrationFragment)
            binding.homeTrue.visibility = View.INVISIBLE
            binding.chatTrue.visibility = View.INVISIBLE
            binding.explorarTrue.visibility = View.VISIBLE
            binding.profileTrue.visibility = View.INVISIBLE
        }

        binding.profileFalse.setOnClickListener {
            navController.navigate(R.id.clientUserProfileFragment)
            binding.homeTrue.visibility = View.INVISIBLE
            binding.chatTrue.visibility = View.INVISIBLE
            binding.explorarTrue.visibility = View.INVISIBLE
            binding.profileTrue.visibility = View.VISIBLE

        }

    }

}
