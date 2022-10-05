package kr.co.inforexseoul.common_util.permission

import android.Manifest

val locationPermissions = listOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)

val recordPermissions = listOf(
    Manifest.permission.RECORD_AUDIO
)

val storagePermissions = listOf(
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)