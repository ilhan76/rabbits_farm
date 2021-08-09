package com.kudashov.rabbits_farm.data.domain

sealed class BirthListItemTypes

data class BirthListItem(
        val id: Int,
        var numberOfCage: String,
        var durationPregnancy: String,
        var isConfirmed: Boolean
): BirthListItemTypes()