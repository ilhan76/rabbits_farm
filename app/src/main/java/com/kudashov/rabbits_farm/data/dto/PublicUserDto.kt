package com.kudashov.rabbits_farm.data.dto

data class PublicUserDto(
    val first_name: String,
    val last_name: String,
    val email: String,
    val groups: List<String>
)