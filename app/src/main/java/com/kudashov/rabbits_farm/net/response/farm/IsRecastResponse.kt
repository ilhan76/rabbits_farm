package com.kudashov.rabbits_farm.net.response.farm

import com.google.gson.annotations.SerializedName

class IsRecastResponse(
    @SerializedName("waiting_recast")
    val waitingRecast: Boolean?,
    @SerializedName("detail")
    val detail: String?
)