package com.kudashov.rabbits_farm.net.response.auth

import com.google.gson.annotations.SerializedName
import com.kudashov.rabbits_farm.data.dto.PublicUserDto
import com.kudashov.rabbits_farm.net.response.ApiWarning

data class AuthResponse(
    @SerializedName("user")
    val user: PublicUserDto?,
    @SerializedName("token")
    val token: String?,
    @SerializedName("warning")
    val warning: ApiWarning?
    //val detail: String?
)