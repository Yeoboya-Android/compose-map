package kr.co.inforexseoul.common_model.test_model

data class WeatherDataModel(
    var condition: WeatherCondition = WeatherCondition.Clear,
    var date: String = "",
    var time: String = "",
    var temperature: Int = 0,
)

sealed class WeatherCondition {
    object Clear : WeatherCondition()
    object Rain : WeatherCondition()
    object Clouds : WeatherCondition()
    object Snow : WeatherCondition()
}
