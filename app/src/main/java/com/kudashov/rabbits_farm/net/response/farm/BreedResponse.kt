package com.kudashov.rabbits_farm.net.response.farm

import com.google.gson.annotations.SerializedName
import com.kudashov.rabbits_farm.data.dto.BreedDto

data class BreedResponse(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("results")
    val breeds: List<BreedDto>?,
    @SerializedName("detail")
    val detail: String?
)