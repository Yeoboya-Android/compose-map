package kr.co.inforexseoul.compose_map.map

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kr.co.inforexseoul.common_model.test_model.StationInfo
import kr.co.inforexseoul.compose_map.BuildConfig
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_data.usecase.GetBusStationDataUseCase
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val app: Application,
    getBusStationDataUseCase: GetBusStationDataUseCase
) : AndroidViewModel(app) {

    private val context: Context get() = app.applicationContext
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    val list = listOf("Google Map", "Naver Map")
    // default - 완도
    var presentLocation = Pair(34.28, 126.74)
    // 정류장 리스트
    var stationList : List<StationInfo> = listOf()
    // 위도 경도 리스트
    val stationMap : HashMap<Int, Pair<Double, Double>> = HashMap()

    private val _cameraPositionState = MutableStateFlow(getCameraPosition(presentLocation))
    val cameraPositionState = _cameraPositionState.asStateFlow()

    fun setCameraPositionState(x: Double, y: Double) {
        _cameraPositionState.value = getCameraPosition(Pair(x, y))
    }

    fun setCameraPositionState(pair: Pair<Double, Double>) {
        _cameraPositionState.value = getCameraPosition(pair)
    }

    fun stationListToMap() {
        stationList.forEachIndexed { index, info ->
            stationMap[index] = Pair(info.latitude, info.longitude)
        }
    }

    fun getCameraPosition(location : Pair<Double, Double>) : CameraPosition {
        return CameraPosition.fromLatLngZoom(LatLng(location.first, location.second), 15f)
    }

    @SuppressLint("MissingPermission")
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
            if (location != null) {
                Log.d("123123", "latitude : ${location.latitude}, longitude : ${location.longitude}")
                presentLocation = Pair(location.latitude, location.longitude)
            } else {
                val locationRequest = LocationRequest.create().apply {
                    interval = 1000
                    priority = Priority.PRIORITY_HIGH_ACCURACY
                }
                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        val lastLocation = locationResult.lastLocation ?: return
                        Log.d("123123", "v2 latitude : ${lastLocation.latitude}, longitude : ${lastLocation.longitude}")
                        presentLocation = Pair(lastLocation.latitude, lastLocation.longitude)
                    }
                }
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
            }
        }
    }

    val busStationState = getBusStationDataUseCase.invoke(BuildConfig.BUS_KEY).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Result.Loading
        )

}