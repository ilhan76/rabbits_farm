package com.kudashov.rabbits_farm.net.response.birth

import com.kudashov.rabbits_farm.data.dto.BirthDto

data class BirthResponse(
    val detail: String?,
    val results: List<BirthDto>?
)