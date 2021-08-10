package com.kudashov.rabbits_farm.data.domain

sealed class AboutFarmListItemType

data class RabbitDomain(
        val id: Int,
        val numberOfCage: String,
        val age: String,
        val isMale: Boolean,
        val type: String
) : AboutFarmListItemType()

data class CageDomain(
        val id: Int,
        val numberOfCage: String,
        val numberOfFarm: String,
        val type: String,
        val status: String
) : AboutFarmListItemType()
