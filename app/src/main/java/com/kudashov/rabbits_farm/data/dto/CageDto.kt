package com.kudashov.rabbits_farm.data.dto

import com.google.gson.annotations.SerializedName

data class CageDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("farm_number")
    val farm_number: Int,
    @SerializedName("number")
    val number: Int,
    @SerializedName("letter")
    val letter: Char,
    @SerializedName("type")
    val type: String,
    @SerializedName("is_parallel")
    val is_parallel: Boolean,
    @SerializedName("number_rabbits")
    val number_rabbits: Int,
    @SerializedName("status")
    val statuses: MutableList<String>
)