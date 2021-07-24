package com.kudashov.rabbits_farm.data.item

sealed class BirthListItemTypes

data class BirthListItem(
        var countOfDay: String,
        var numberOfCage: String,
        var status: String
): BirthListItemTypes()