package kr.co.inforexseoul.common_model.test_model

import com.google.gson.annotations.SerializedName

class VillageForecastResponseModel {
    @SerializedName("response")
    var response: Response = Response()
}

data class Response(
    @SerializedName("body") var body: Body = Body()
)

data class Body(
    @SerializedName("dataType") var dataType: String = "",
    @SerializedName("items") var items: VillageForecastItems = VillageForecastItems(),
    @SerializedName("pageNo") var pageNo: Int = 0,
    @SerializedName("numOfRows") var numOfRows: Int = 0,
    @SerializedName("totalCount") var totalCount: Int = 0
)

data class VillageForecastItems(
    @SerializedName("item") var item: ArrayList<Item> = arrayListOf()
) {
    fun getWeatherDataList(): List<WeatherDataModel> {
        val returnList = mutableListOf<WeatherDataModel>()

        val ptyRainRange = listOf(1, 5)
        val ptySnowRange = listOf(2, 3, 6, 7)
        val skyCloudsRange = listOf(3, 4)

        val ptyList = item.sortItemList("PTY")
        val skyList = item.sortItemList("SKY")
        val t1hList = item.sortItemList("T1H")
        ptyList.takeIf {
            it.size == skyList.size && it.size == t1hList.size
        }?.forEachIndexed { index, pty ->
            val ptyValue = pty.fcstValue.toInt()
            val skyValue = skyList[index].fcstValue.toInt()
            val condition = when {
                ptyValue > 0 && ptyValue in ptyRainRange -> WeatherCondition.Rain
                ptyValue > 0 && ptyValue in ptySnowRange -> WeatherCondition.Snow
                skyValue > 0 && skyValue in skyCloudsRange -> WeatherCondition.Clouds
                else -> WeatherCondition.Clear
            }
            val date = String.format(
                "%2s.%2s",
                pty.fcstDate.substring(4,6),
                pty.fcstDate.substring(6,8)
            )
            val time = String.format(
                "%2s:%2s",
                pty.fcstTime.substring(0,2),
                pty.fcstTime.substring(2,4)
            )
            val temperature = t1hList[index].fcstValue.toInt()

            returnList.add(WeatherDataModel(
                condition = condition,
                date = date,
                time = time,
                temperature = temperature,
            ))
        }
        return returnList
    }

    private fun ArrayList<Item>.sortItemList(category: String) =
        this.filter { it.category  == category }.sortedWith(
            compareBy({ it.fcstDate }, { it.fcstTime})
        )
}

data class Item(
    @SerializedName("baseDate") var baseDate: String = "",
    @SerializedName("baseTime") var baseTime: String = "",
    @SerializedName("category") var category: String = "",
    @SerializedName("fcstDate") var fcstDate: String = "",
    @SerializedName("fcstTime") var fcstTime: String = "",
    @SerializedName("fcstValue") var fcstValue: String = "",
    @SerializedName("nx") var nx: Int? = null,
    @SerializedName("ny") var ny: Int? = null

)