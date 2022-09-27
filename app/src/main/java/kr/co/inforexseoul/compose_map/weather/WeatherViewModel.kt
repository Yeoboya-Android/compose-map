package kr.co.inforexseoul.compose_map.weather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jxl.Workbook
import jxl.read.biff.BiffException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kr.co.inforexseoul.common_model.test_model.OpenWeatherForecastModel
import kr.co.inforexseoul.common_model.test_model.VillageForecastItems
import kr.co.inforexseoul.compose_map.BuildConfig
import kr.co.inforexseoul.core_data.state.Result
import kr.co.inforexseoul.core_data.usecase.*
import kr.co.inforexseoul.core_database.entity.District
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val app: Application,
    selectDistrictCountUseCase: SelectDistrictCountUseCase,
    insertDistrictUseCase: InsertDistrictUseCase,
    private val selectDistrictUseCase: SelectDistrictUseCase,
    private val getOpenWeatherForecastUseCase: GetOpenWeatherForecastUseCase,
    private val getVillageForecastUseCase: GetVillageForecastUseCase
) : ViewModel() {

    private val context: Context get() = app.applicationContext

    init {
        fetchDistrictList(selectDistrictCountUseCase, insertDistrictUseCase)
    }

    private val _districtState = MutableStateFlow<Result<District>>(Result.Loading)
    var districtState =_districtState

    private val _openWeatherForecastState = MutableStateFlow<Result<OpenWeatherForecastModel>>(Result.Loading)
    var openWeatherForecastState = _openWeatherForecastState

    private val _villageForecastState = MutableStateFlow<Result<VillageForecastItems>>(Result.Loading)
    var villageForecastState = _villageForecastState

    fun getDistrict(latitude: Double, longitude: Double) {
        Log.e("qwe123", "WeatherViewModel.setDistrict():::")
        selectDistrictUseCase.invoke(
            latitude = latitude,
            longitude = longitude,
        ).onEach {
            _districtState.value = it
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Result.Loading
        )
    }

    fun getOpenWeatherForecast(latitude: Double, longitude: Double) {
        Log.e("qwe123", "WeatherViewModel.getOpenWeatherForecast():::")
        getOpenWeatherForecastUseCase.invoke(
            appId = BuildConfig.OPEN_WEATHER_MAP_KEY,
            latitude = latitude,
            longitude = longitude,
            exclude = "daily,minutely,current"
        ).onEach {
            _openWeatherForecastState.value = it
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Result.Loading
        )
    }

    fun getVillageForecast(nx: Int, ny: Int) {
        Log.e("qwe123", "WeatherViewModel.getVillageForecast():::")
        getVillageForecastUseCase.invoke(
            serviceKey = BuildConfig.VILLAGE_FORECAST,
            numOfRows = 1000,
            pageNo = 1,
            baseDate = getBaseDateTime("yyyyMMdd"),
            baseTime = getBaseDateTime("HHmm"),
            nx = nx,
            ny = ny
        ).onEach {
            _villageForecastState.value = it
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Result.Loading
        )
    }

    private fun fetchDistrictList(
        selectDistrictCountUseCase: SelectDistrictCountUseCase,
        insertDistrictUseCase: InsertDistrictUseCase
    ) {
        viewModelScope.launch {
            selectDistrictCountUseCase.invoke().collect { count ->
                if (count <= 0) readDistrictExcel(insertDistrictUseCase)
                else Toast.makeText(context, "지역 DB 업데이트 완료", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun readDistrictExcel(insertDistrictUseCase: InsertDistrictUseCase) {
        try {
            val inputStream = context.resources.assets.open("district.xls")
            val workbook = Workbook.getWorkbook(inputStream)
            val sheet = workbook.getSheet(0)

            val colTotalCount = sheet.columns
            val rowTotalCount = sheet.getColumn(colTotalCount-1).size
            val list = arrayListOf<District>()
            for (i in 1 until rowTotalCount) {
                val districtId = sheet.getRow(i)[1].contents.toLong()
                val districtName = "${sheet.getRow(i)[2].contents} ${sheet.getRow(i)[3].contents} ${sheet.getRow(i)[4].contents}"
                val nx = sheet.getRow(i)[5].contents.toInt()
                val ny = sheet.getRow(i)[6].contents.toInt()
                val latitude = sheet.getRow(i)[14].contents.toDouble()
                val longitude = sheet.getRow(i)[13].contents.toDouble()

                val district = District(
                    districtId = districtId,
                    districtName = districtName.trim(),
                    nx = nx,
                    ny = ny,
                    latitude = latitude,
                    longitude = longitude
                )
                list.add(district)
            }
            insertDistrictUseCase.invoke(list)
        } catch (e: Exception) {
            if (e is IOException || e is BiffException) e.printStackTrace()
            else throw e
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getBaseDateTime(pattern: String): String {
        val baseTimeMillis = System.currentTimeMillis() - 3600000
        return SimpleDateFormat(pattern).format(Date(baseTimeMillis))
    }

    /*fun getDistrictState(latitude: Double, longitude: Double): StateFlow<Result<District>> {
        Log.d("qwe123", "WeatherViewModel.getDistrictState():::")
        return selectDistrictUseCase.invoke(latitude, longitude).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Result.Loading
        )
    }*/
}