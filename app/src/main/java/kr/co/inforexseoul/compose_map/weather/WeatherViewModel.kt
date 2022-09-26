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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
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
    selectDistrictUseCase: SelectDistrictUseCase,
    getOpenWeatherForecastUseCase: GetOpenWeatherForecastUseCase,
    getVillageForecastUseCase: GetVillageForecastUseCase
) : ViewModel() {

    private val context: Context get() = app.applicationContext

    val villageForecastState =
        getVillageForecastUseCase.invoke(
            serviceKey = BuildConfig.VILLAGE_FORECAST,
            numOfRows = 1000,
            pageNo = 1,
            baseDate = getBaseDate(),
            baseTime = getBaseTime(),
            nx = 58,
            ny = 74
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Result.Loading
        )

    val openWeatherForecastState =
        getOpenWeatherForecastUseCase.invoke(
            appId = BuildConfig.OPEN_WEATHER_MAP_KEY,
            latitude = 35.1470,
            longitude = 126.8452,
            exclude = "daily,minutely,current"
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Result.Loading
        )

    val districtState =
        selectDistrictUseCase.invoke(
            latitude = 35.1470,
            longitude = 126.8452,
        ).stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Result.Loading
        )

    init {
        fetchDistrictList(selectDistrictCountUseCase, insertDistrictUseCase)
    }

    private fun fetchDistrictList(
        selectDistrictCountUseCase: SelectDistrictCountUseCase,
        insertDistrictUseCase: InsertDistrictUseCase
    ) {
        viewModelScope.launch {
            selectDistrictCountUseCase.invoke().collect { count ->
                Log.d("qwe123", "WeatherViewModel.fetchDistrictList()::: count: $count")
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
    private fun getBaseDate(): String {
        val baseTimeMillis = System.currentTimeMillis() - 3600000
        return SimpleDateFormat("yyyyMMdd").format(Date(baseTimeMillis))
    }

    @SuppressLint("SimpleDateFormat")
    private fun getBaseTime(): String {
        val baseTimeMillis = System.currentTimeMillis() - 3600000
        return SimpleDateFormat("HHmm").format(Date(baseTimeMillis))
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