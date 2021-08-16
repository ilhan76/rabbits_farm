package com.kudashov.rabbits_farm.data.dto

import com.google.gson.annotations.SerializedName

data class TaskDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("type")
    val type: String,
    @SerializedName("created_at")
    val date: String,
    @SerializedName("user")
    val userId: Int,
    @SerializedName("cage_from")
    val cageFrom: CageSimpleDto? = null,
    @SerializedName("cage_to")
    val cageTo: CageSimpleDto? = null,
    @SerializedName("male_cage_to")
    val maleCageTo: CageSimpleDto? = null,
    @SerializedName("female_cage_to")
    val femaleCageTo: CageSimpleDto? = null,
    @SerializedName("number_bunnies")
    val countBunnies: Int? = null,
    @SerializedName("cage")
    val cage: CageSimpleDto? = null,
    @SerializedName("number_rabbits")
    val countRabbit: Int? = null,
    @SerializedName("weight")
    val weight: Double? = null
)
