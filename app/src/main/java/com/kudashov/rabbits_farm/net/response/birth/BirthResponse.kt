package com.kudashov.rabbits_farm.net.response.birth

import com.google.gson.annotations.SerializedName
import com.kudashov.rabbits_farm.data.dto.BirthDto

data class BirthResponse(
    @SerializedName("detail")
    val detail: String?,
    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    val results: List<BirthDto>?
)