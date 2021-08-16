package com.kudashov.rabbits_farm.data.domain

data class BreedDomain(
    val id: Int,
    val title: String
) {
    override fun toString(): String {
        return title
    }
}