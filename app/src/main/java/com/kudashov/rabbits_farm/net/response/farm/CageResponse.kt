package com.kudashov.rabbits_farm.net.response.farm

import com.kudashov.rabbits_farm.data.dto.CageDto

data class CageResponse(
    val detail: String?,
    val results: List<CageDto>?
)