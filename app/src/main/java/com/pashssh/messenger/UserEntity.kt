package com.pashssh.messenger

import com.google.firebase.auth.FirebaseUser

data class UserEntity (
    val uid: String,
    val username: String? = "",
    val email: String? = "",
    val phone: String? = "",
    val state: Boolean? = false
)


fun FirebaseUser.toUser() : UserEntity{
    return UserEntity(
        uid = this.uid,
        email = this.email,
        phone = this.phoneNumber,
        username = "",
        state = false
    )
}