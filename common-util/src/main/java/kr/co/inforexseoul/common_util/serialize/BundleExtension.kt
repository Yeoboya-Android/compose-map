package kr.co.inforexseoul.common_util.serialize

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

fun Bundle?.string(key: String, def: String = ""): String =
    this?.getString(key, def) ?: def

fun Bundle?.boolean(key: String, def: Boolean = false): Boolean =
    this?.getBoolean(key, def) ?: def

fun Bundle?.long(key: String, def: Long = 0L): Long =
    this?.getLong(key, def) ?: def

fun Bundle?.int(key: String, def: Int = 0): Int =
    this?.getInt(key, def) ?: def

fun Bundle?.stringList(key: String, def: List<String> = emptyList()): List<String> =
    this?.getStringArrayList(key) ?: def

fun Bundle?.intList(key: String, def: List<Int> = emptyList()): List<Int> =
    this?.getIntegerArrayList(key) ?: def

inline fun <reified T> Bundle?.parcelable(key: String): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this?.getParcelable(key, T::class.java)
    else
        this?.getParcelable(key)

inline fun <reified T : Parcelable> Bundle?.parcelableList(key: String, def: List<T> = emptyList()): List<T> =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this?.getParcelableArrayList(key, T::class.java) ?: def
    else
        this?.getParcelableArrayList(key) ?: def
