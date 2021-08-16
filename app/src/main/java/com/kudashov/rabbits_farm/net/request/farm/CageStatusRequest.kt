package com.kudashov.rabbits_farm.net.request.farm

import com.google.gson.annotations.SerializedName

data class CageStatusRequest(
    @SerializedName("status")
    val statuses: List<String>
)