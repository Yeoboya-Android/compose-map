package kr.co.inforexseoul.compose_map.map

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.android.gms.location.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val app: Application
) : AndroidViewModel(app) {

    private val context: Context get() = app.applicationContext
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // default - 광주 시청
    var presentLocation = Pair(35.15, 126.85)

    fun requestLocation() {
        if(!this::fusedLocationProviderClient.isInitialized) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

            val locationRequest = LocationRequest.create().apply {
                interval = 1000
                priority = Priority.PRIORITY_HIGH_ACCURACY
            }

            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            val client = LocationServices.getSettingsClient(context)
            val task = client.checkLocationSettings(builder.build())
            task.addOnSuccessListener {
                Log.d("123123", "location client setting SUCCESS")
            }
            task.addOnFailureListener {
                Log.d("123123", "location client setting Failure")
            }
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            Log.d("123123", "latitude : ${location.latitude}, longitude : ${location.longitude}")
        }
    }

}