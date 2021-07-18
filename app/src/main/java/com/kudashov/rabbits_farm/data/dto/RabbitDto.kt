package com.kudashov.rabbits_farm.data.dto

data class RabbitDto(
    val id: Int,
    val cage: CageDto,
    val birthday: String,
    val is_male: Boolean,
    val breed: String,
    val current_type: String,
    val weight: Double,
    val status: List<String>
)