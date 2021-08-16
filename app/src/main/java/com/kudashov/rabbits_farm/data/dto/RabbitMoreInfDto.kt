package com.kudashov.rabbits_farm.data.dto

import java.io.Serializable

data class RabbitMoreInfDto(
    val id: Int,
    val is_male: Boolean?,
    val birthday: String,
    val breed: String,
    val current_type: String,
    val cage: CageDto,
    val status: List<String>,
    val output: Int?,
    val output_efficiency: Double?,
    val weight: Double?
) : Serializable