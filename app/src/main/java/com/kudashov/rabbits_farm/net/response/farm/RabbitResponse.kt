package com.kudashov.rabbits_farm.net.response.farm

import com.kudashov.rabbits_farm.data.dto.RabbitDto

data class RabbitResponse(
    val detail: String?,
    val count: Int,
    val results: List<RabbitDto>?
)