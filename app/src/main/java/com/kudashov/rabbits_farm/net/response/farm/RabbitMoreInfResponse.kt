package com.kudashov.rabbits_farm.net.response.farm

import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto

data class RabbitMoreInfResponse (
    val rabbit: RabbitMoreInfDto?,
    val detail: String?
)