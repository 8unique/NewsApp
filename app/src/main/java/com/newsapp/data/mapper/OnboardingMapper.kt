package com.newsapp.data.mapper

import com.newsapp.data.local.entity.UserEntity
import com.newsapp.data.remote.response.UserDto
import com.newsapp.domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email
    )
}

fun User.toEntity(password: String): UserEntity {
    return UserEntity(
        id = this.id,
        firstName = this.firstName,
        lastName = this.lastName,
        email = this.email,
        password = password
    )
}