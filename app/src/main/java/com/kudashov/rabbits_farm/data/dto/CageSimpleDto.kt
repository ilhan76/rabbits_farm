package com.kudashov.rabbits_farm.data.dto

import com.google.gson.annotations.SerializedName

class CageSimpleDto (
    @SerializedName("farm_number")
    val farmNumber: Int?,
    @SerializedName("number")
    val cageNumber: Int?,
    @SerializedName("letter")
    val letter: String?
){
    override fun toString(): String {
        return "{" +
                "\"farm_number\": " + farmNumber.toString() + "," +
                "\"number\": " + cageNumber.toString() + "," +
                "\"letter\": " + letter.toString() +
                "}"
    }
}