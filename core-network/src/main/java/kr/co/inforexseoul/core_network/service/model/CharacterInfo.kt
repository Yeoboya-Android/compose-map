package kr.co.inforexseoul.core_network.service.model

data class CharacterInfo(
    val id: Int = 0,
    val slug: String = "",
    val displayName: String = "",
    val fullName: String = "",
    val sex: String = "",
    private val quotes: List<String> = listOf(""),
    val sprite: String = ""
)