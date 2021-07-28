package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.dto.CageDto

data class CageResponse(
    val detail: String?,
    val results: List<CageDto>?
)