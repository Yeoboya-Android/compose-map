package kr.co.inforexseoul.compose_map.map

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kr.co.inforexseoul.common_model.test_model.BusStationInfo
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

    // default - 완도
    var presentLocation = Pair(34.28, 126.74)
    // 정류장 리스트
    var stationList : List<StationInfo> = listOf()
    // 위도 경도 리스트
    val stationMap : HashMap<Int, Pair<Double, Double>> = HashMap()

    fun stationListToMap() {
        stationList.forEachIndexed { index, info ->
            stationMap[index] = Pair(info.latitude, info.longitude)
        }
    }

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
            presentLocation = Pair(location.latitude, location.longitude)
        }
    }

    val busStationState = getBusStationDataUseCase.invoke(BuildConfig.BUS_KEY).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = Result.Loading
        )

}