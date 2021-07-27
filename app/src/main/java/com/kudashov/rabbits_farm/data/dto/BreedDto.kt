package com.kudashov.rabbits_farm.data.dto

data class BreedDto(
    val id: Int,
    val title: String
) {
    override fun toString(): String {
        return title
    }
}