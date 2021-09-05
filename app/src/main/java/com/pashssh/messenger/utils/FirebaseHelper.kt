package com.pashssh.messenger

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE: DatabaseReference

const val NODE_USERS = "users"
const val NODE_CHATS = "chats"


fun initFirebase() {
    AUTH = Firebase.auth
    REF_DATABASE = FirebaseDatabase.getInstance("https://messenger-b4392-default-rtdb.europe-west1.firebasedatabase.app/").reference


}