package com.kudashov.rabbits_farm.data.domain

sealed class BirthListItemTypes

data class BirthDomain(
        val id: Int,
        var numberOfCage: String,
        var durationPregnancy: String,
        var isConfirmed: Boolean
): BirthListItemTypes()