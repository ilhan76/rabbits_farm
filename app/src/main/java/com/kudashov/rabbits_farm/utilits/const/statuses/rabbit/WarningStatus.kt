package com.kudashov.rabbits_farm.utilits.const.statuses.rabbit

const val RABBIT_WARNING_STATUS_NOT_EAT: String = "NE"
const val RABBIT_WARNING_STATUS_NOT_DRINK: String = "ND"
const val RABBIT_WARNING_STATUS_GOT_SICK: String = "GS"

val WARNING_STATUSES = listOf(
    Pair(RABBIT_WARNING_STATUS_NOT_EAT, "Не ест"),
    Pair(RABBIT_WARNING_STATUS_NOT_DRINK, "Не пьет"),
    Pair(RABBIT_WARNING_STATUS_GOT_SICK, "Заболел")
)