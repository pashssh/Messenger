package com.pashssh.messenger.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE: DatabaseReference
lateinit var CURRENT_UID: String

const val USERS_CHILD = "users"
const val PHONES_CHILD = "phones"
const val CONTACTS_CHILD = "contacts"
const val MESSAGE_CHILD = "message"
const val CHATS_CHILD = "chats"

const val MESSAGE_MAIN = "main_list"


fun initFirebase() {
    AUTH = Firebase.auth
    CURRENT_UID = AUTH.currentUser?.uid.toString()
    REF_DATABASE = FirebaseDatabase.getInstance("https://messenger-b4392-default-rtdb.europe-west1.firebasedatabase.app/").reference
}