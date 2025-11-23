package com.newsapp.data.remote.response

data class SignUpResponse(
    val success: Boolean,
    val message: String?,
    val token: String?,
    val user: UserDto?
)
data class UserDto(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String
)