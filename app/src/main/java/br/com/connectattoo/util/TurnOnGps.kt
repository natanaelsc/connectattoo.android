package br.com.connectattoo.util

import android.content.Context
import android.content.IntentSender
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import br.com.connectattoo.util.Constants.INTERVAL_TIME_MILLIS_3000
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority


private const val TAG = "TurnOnGps"

class TurnOnGps(private val context: Context) {

    fun startGps(resultLauncher: ActivityResultLauncher<IntentSenderRequest>) {

        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, INTERVAL_TIME_MILLIS_3000)
                .build()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val task =
            LocationServices.getSettingsClient(context).checkLocationSettings(builder.build())

        task.addOnFailureListener {

            if (it is ResolvableApiException) {
                try {
                    val intentSenderRequest = IntentSenderRequest.Builder(it.resolution).build()
                    resultLauncher.launch(intentSenderRequest)
                } catch (exception: IntentSender.SendIntentException) {
                    Log.e(TAG, "startGps: ${exception.message}")
                }
            }
        }

    }
}
