package com.kudashov.rabbits_farm.data.dto

data class CageDto(
    val id: Int,
    val farm_number: Int,
    val number: Int,
    val letter: Char,
    val type: String,
    val is_parallel: Boolean,
    val number_rabbits: Int,
    val status: List<String>
)