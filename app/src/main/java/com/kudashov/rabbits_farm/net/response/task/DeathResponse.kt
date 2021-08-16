package com.kudashov.rabbits_farm.net.response.task

import com.google.gson.annotations.SerializedName
import com.kudashov.rabbits_farm.net.response.ApiWarning

data class DeathResponse(
    @SerializedName("warning")
    val warning: ApiWarning?
)