package com.kudashov.rabbits_farm.net.response.farm

import com.google.gson.annotations.SerializedName
import com.kudashov.rabbits_farm.data.dto.CageDto

data class CageResponse(
    @SerializedName("count")
    val count: Int,
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("results")
    val results: List<CageDto>?
)