package com.example.pushtest.models

data class NotificationsModel(
    val id_users: String,
    val message: Message
)

data class Message(
    val title: String,
    val body: String
)

