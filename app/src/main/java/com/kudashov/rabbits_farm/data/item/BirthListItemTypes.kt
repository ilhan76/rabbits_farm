package com.kudashov.rabbits_farm.data.item

sealed class BirthListItemTypes

data class BirthListItem(
        val id: Int,
        var durationPregnancy: String,
        var numberOfCage: String,
        var status: Boolean
): BirthListItemTypes()