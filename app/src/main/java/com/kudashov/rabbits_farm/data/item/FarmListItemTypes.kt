package com.kudashov.rabbits_farm.data.item

sealed class AboutFarmListItemType

data class Rabbit(
        val id: Int,
        val numberOfCage: String,
        val age: String,
        val isMale: Boolean,
        val type: String
) : AboutFarmListItemType()

data class Cage(
        val id: Int,
        val numberOfCage: String,
        val numberOfFarm: String,
        val type: String,
        val status: String
) : AboutFarmListItemType()
