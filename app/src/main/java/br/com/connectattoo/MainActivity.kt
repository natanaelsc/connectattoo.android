package br.com.connectattoo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.connectattoo.util.Constants
import br.com.connectattoo.util.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verifyTokenApi()
    }

    private fun verifyTokenApi() {
        CoroutineScope(Dispatchers.IO).launch {
            val token = DataStoreManager.getStringToken(this@MainActivity, Constants.API_TOKEN)
            if (token.isNotEmpty()) {
                startActivity(Intent(this@MainActivity, HomeUserActivity::class.java))
                finish()
            }
        }
    }
}
