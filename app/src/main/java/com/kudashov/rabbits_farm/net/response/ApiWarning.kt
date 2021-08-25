package com.kudashov.rabbits_farm.net.response

import com.google.gson.annotations.SerializedName

data class ApiWarning(
    @SerializedName("codes")
    val codes: List<String?>
)