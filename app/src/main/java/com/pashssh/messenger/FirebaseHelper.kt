package com.pashssh.messenger

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

lateinit var AUTH: FirebaseAuth

fun initFirebase() {
    AUTH = Firebase.auth
}