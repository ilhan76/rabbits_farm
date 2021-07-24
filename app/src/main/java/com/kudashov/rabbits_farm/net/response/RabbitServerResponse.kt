package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.dto.RabbitDto

data class RabbitServerResponse(
    val respError: String?,
    val count: Int,
    val results: List<RabbitDto>?
)