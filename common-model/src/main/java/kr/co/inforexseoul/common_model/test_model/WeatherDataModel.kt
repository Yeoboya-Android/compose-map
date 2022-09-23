package kr.co.inforexseoul.common_model.test_model

data class WeatherDataModel(
    var type: WeatherType = WeatherType.Clear,
    var date: String = "",
    var time: String = "",
    var temperature: Int = 0,
)

sealed class WeatherType {
    object Clear : WeatherType()
    object Rain : WeatherType()
    object Clouds : WeatherType()
    object Snow : WeatherType()
}
