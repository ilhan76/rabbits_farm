package com.kudashov.rabbits_farm.data.dto

import com.google.gson.annotations.SerializedName

data class BirthDto (
    val id: Int,
    val cage: CageDto,
    @SerializedName("last_fertilization")
    val last_fertilisation: String,
    @SerializedName("is_confirmed")
    val is_confirmed: Boolean
)