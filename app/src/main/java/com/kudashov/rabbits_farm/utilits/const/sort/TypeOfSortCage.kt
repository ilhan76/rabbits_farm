package com.kudashov.rabbits_farm.utilits.const.sort

const val SORT_CAGE_FARM_NUMBER = "farm_number"
const val SORT_CAGE_NUMBER = "number"
const val SORT_CAGE_NUMBER_RABBITS = "number_rabbits"
const val SORT_CAGE_STATUS = "status"
const val SORT_CAGE_FARM_NUMBER_INV = "-farm_number"
const val SORT_CAGE_NUMBER_INV = "-number"
const val SORT_CAGE_NUMBER_RABBITS_INV = "-number_rabbits"
const val SORT_CAGE_STATUS_INV = "-status"

val TYPES_SORT_CAGE = listOf(
    Pair(SORT_CAGE_NUMBER, "cage_number"),
    Pair(SORT_CAGE_FARM_NUMBER, "farm_number"),
    Pair(SORT_CAGE_NUMBER_RABBITS, "age"),
    Pair(SORT_CAGE_STATUS, "status"),
    Pair(SORT_CAGE_FARM_NUMBER_INV, "-farm_number"),
    Pair(SORT_CAGE_NUMBER_INV, "-number"),
    Pair(SORT_CAGE_NUMBER_RABBITS_INV, "-number_rabbits"),
    Pair(SORT_CAGE_STATUS_INV, "-status")
)