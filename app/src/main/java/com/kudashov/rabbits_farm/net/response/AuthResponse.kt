package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.dto.PublicUserDto

data class AuthResponse(
    val user: PublicUserDto?,
    val token: String?,
    val detail: String?
)