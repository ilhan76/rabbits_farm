package com.kudashov.rabbits_farm.data

sealed class AboutFarmListItemType()

data class Rabbit(
        var numberOfCage: String? = null,
        var age: String? = null,
        var type: String? = null
) : AboutFarmListItemType()

data class Cage(
        var numberOfCage: String? = null,
        var numberOfFarm: String? = null,
        var type: String? = null,
        var status: String? = null
) : AboutFarmListItemType()