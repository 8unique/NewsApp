package com.newsapp.data.remote.request

data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)