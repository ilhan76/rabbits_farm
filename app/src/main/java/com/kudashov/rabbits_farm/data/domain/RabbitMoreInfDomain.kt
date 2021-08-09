package com.kudashov.rabbits_farm.data.domain

import com.kudashov.rabbits_farm.data.dto.CageDto
import java.io.Serializable

data class RabbitMoreInfDomain (
    val id: Int,
    val is_male: Boolean?,
    val birthday: String,
    val age: String,
    val breed: String,
    val currentType: String,
    val currentTypeString: String,
    val cage: CageDto,
    val status: String,
    val output: Int?,
    val output_efficiency: Double?,
    val weight: Double?
) : Serializable