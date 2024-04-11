package br.com.connectattoo.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import br.com.connectattoo.R
import br.com.connectattoo.util.Constants.INTERVAL_TIME_MILLIS_10000
import br.com.connectattoo.util.Constants.INTERVAL_TIME_MILLIS_5000
import br.com.connectattoo.util.Constants.REQUEST_CODE_100
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object PermissionUtils {
    fun getPermissionAndLocationUser(
        activity: FragmentActivity,
        context: Context,
        enableLocationActivityResult: ActivityResultLauncher<Intent>
    ) {
        val locationPermissionRequest = activity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                when {
                    permissions.getOrDefault(
                        Manifest.permission.ACCESS_FINE_LOCATION, false)
                        || permissions.getOrDefault(
                        Manifest.permission.ACCESS_COARSE_LOCATION, false
                    ) -> {
                        if (isLocationEnabled(activity)) {
                            if (ContextCompat.checkSelfPermission(activity,
                                    Manifest.permission.ACCESS_FINE_LOCATION)
                                == PackageManager.PERMISSION_GRANTED
                            ) {
                                val fusedLocationClient =
                                    LocationServices.getFusedLocationProviderClient(activity)
                                val result = fusedLocationClient.getCurrentLocation(
                                    Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                                    CancellationTokenSource().token
                                )
                                result.addOnCompleteListener {
                                    val location = "Latitude: " + it.result.latitude + "\n" +
                                        "Longitude: " + it.result.longitude
                                    Log.i("location", location)
                                }
                            } else {
                                createLocationRequest(activity)
                            }
                        } else {
                            showEnableLocationDialog(
                                context = context,
                                enableLocationActivityResult = enableLocationActivityResult)
                        }
                    }else -> {}
                }
            }
        }
        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun showEnableLocationDialog(
        context: Context, enableLocationActivityResult:
        ActivityResultLauncher<Intent>
    ) {
        val alertDialog = MaterialAlertDialogBuilder(
            context,
            com.google.android.material.R.style.AlertDialog_AppCompat
        )
            .setMessage(R.string.txt_request_location)
            .setNeutralButton(R.string.txt_calcel) { _, _ ->
            }
            .setPositiveButton(R.string.txt_enable_now) { _, _ ->
                val enableLocationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                enableLocationActivityResult.launch(enableLocationIntent)
            }.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            ?.setTextColor(Color.BLUE)
        alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            ?.setTextColor(Color.BLUE)
    }

    private fun createLocationRequest(context: Context) {
        val locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, INTERVAL_TIME_MILLIS_10000
        ).setMinUpdateIntervalMillis(INTERVAL_TIME_MILLIS_5000).build()

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            isLocationEnabled(context)
        }
        task.addOnFailureListener { exeption ->
            if (exeption is ResolvableApiException) {
                try {
                    exeption.startResolutionForResult(Activity(), REQUEST_CODE_100)
                } catch (sendEX: java.lang.Exception) {
                    Toast.makeText(context, sendEX.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
