package com.kudashov.rabbits_farm.net.response

import com.kudashov.rabbits_farm.data.dto.CageDto

data class CageServerResponse(
    val respError: String?,
    val results: List<CageDto>?
)