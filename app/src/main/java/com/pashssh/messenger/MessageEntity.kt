package com.pashssh.messenger

data class MessageEntity (
    val from: String = "",
    val textMessage: String = "",
    val timeStamp: Map<String, String> = emptyMap(),
    val type : String = "",
    val status: Boolean = false,
)