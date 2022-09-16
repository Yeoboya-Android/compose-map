package kr.co.inforexseoul.common_model.test_model

/**
 * TODO 테스트용
 * */
data class TestDataModel(
    val id: Int = 0,
    val slug: String = "",
    val displayName: String = "",
    val fullName: String = "",
    val sex: String = "",
    private val quotes: List<String> = listOf(""),
    val sprite: String = ""
)