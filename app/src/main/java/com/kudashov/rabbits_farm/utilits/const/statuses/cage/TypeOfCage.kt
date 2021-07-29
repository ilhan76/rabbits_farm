package com.kudashov.rabbits_farm.utilits.const.statuses.cage

const val CAGE_TYPE_FATTENING = "F"
const val CAGE_TYPE_MOTHER = "M"
const val CAGE_TYPE_MOTHER_PARALLEL = "M"

val TYPES_CAGE = listOf(
    Pair(CAGE_TYPE_FATTENING, "Откормочная"),
    Pair(CAGE_TYPE_MOTHER, "Маточная |"),
    Pair(CAGE_TYPE_MOTHER_PARALLEL, "Маточная ||")
)