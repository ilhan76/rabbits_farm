package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.dto.OperationDto

data class OperationsResponse(
    val detail: String?,
    val list: List<OperationDto>
)