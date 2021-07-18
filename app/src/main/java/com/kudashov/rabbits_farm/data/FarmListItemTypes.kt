package com.kudashov.rabbits_farm.data


sealed class AboutFarmListItemType

data class Rabbit(
        val id: Int,
        val numberOfCage: String? = null,
        val age: String? = null,
        val type: String? = null
) : AboutFarmListItemType()

data class Cage(
        val numberOfCage: String? = null,
        val numberOfFarm: String? = null,
        val type: String? = null,
        val status: String? = null
) : AboutFarmListItemType()
