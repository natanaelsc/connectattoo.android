package br.com.connectattoo


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.connectattoo.databinding.ActivityHomeUserBinding
import br.com.connectattoo.util.PermissionUtils


class HomeUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeUserBinding
    private lateinit var navController: NavController

    private val enableLocationActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_CANCELED) {
            Log.i("result", activityResult.resultCode.toString())
        } else {
            if (activityResult.resultCode == RESULT_CANCELED) {
                Log.i("resultNotAccept", activityResult.resultCode.toString())
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermissionAndLocation()
        binding = ActivityHomeUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_user_fragment) as NavHostFragment
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.homeTrue.isVisible = destination.id == R.id.homeUserFragment
            binding.profileTrue.isVisible = destination.id == R.id.clientUserProfileFragment
        }

        binding.homeFalse.setOnClickListener {
            navController.navigate(R.id.homeUserFragment)
            binding.homeTrue.visibility = View.VISIBLE
            binding.chatTrue.visibility = View.INVISIBLE
            binding.explorarTrue.visibility = View.INVISIBLE
            binding.profileTrue.visibility = View.INVISIBLE
        }

        binding.chatFalse.setOnClickListener {
            navController.navigate(R.id.userChatFragment)
            binding.homeTrue.visibility = View.INVISIBLE
            binding.chatTrue.visibility = View.VISIBLE
            binding.explorarTrue.visibility = View.INVISIBLE
            binding.profileTrue.visibility = View.INVISIBLE
        }

        binding.explorarFalse.setOnClickListener {
             navController.navigate(R.id.userSearchFragment)
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
    override fun onStart() {
        super.onStart()
        if (PermissionUtils.isLocationEnabled(this)) {
            getPermissionAndLocation()
        }
    }

    private fun getPermissionAndLocation() {
        PermissionUtils.getPermissionAndLocationUser(
            this,
            this,
            enableLocationActivityResult
        )
    }
}
