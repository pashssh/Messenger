package com.pashssh.messenger.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


const val READ_CONTACTS = Manifest.permission.READ_CONTACTS

fun checkAndRequestPermission(activity: Activity, permission: String): Boolean {

    val permissionCheck = ContextCompat.checkSelfPermission(activity, permission)

    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), 123)
    }
    return permissionCheck == PackageManager.PERMISSION_GRANTED
}
