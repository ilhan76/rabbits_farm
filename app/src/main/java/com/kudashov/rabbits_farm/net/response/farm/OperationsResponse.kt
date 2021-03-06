package com.kudashov.rabbits_farm.net.response.farm

import com.kudashov.rabbits_farm.data.dto.OperationDto

data class OperationsResponse(
    val results: List<OperationDto>?,
    val detail: String?
)