package com.pashssh.messenger.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.pashssh.messenger.R
import java.text.SimpleDateFormat
import java.util.*

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity) {
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

//fun AppCompatActivity.replaceFragment(fragment: Fragment, addBackStack: Boolean = true) {
//    if (addBackStack) {
//        supportFragmentManager.beginTransaction()
//            .addToBackStack(null)
//            .replace(R.id.data_container, fragment)
//            .commit()
//    } else {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.data_container, fragment)
//            .commit()
//    }
//}

fun String.asTime(): String {
    val time = Date(this.toLong())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return timeFormat.format(time)
}

fun Boolean.isChecked(): Int {
    return if (this) R.drawable.ic_checked_all else R.drawable.ic_checked
}