package com.kudashov.rabbits_farm.utilits.const.statuses.cage

const val CAGE_STATUS_NEED_CLEAN: String = "C"
const val CAGE_STATUS_NEED_REPAIR: String = "R"
const val CAGE_STATUS_CLEANED: String = "CLEANED"
const val CAGE_STATUS_REPAIRED: String = "REPAIRED"

val STATUSES_CAGE = listOf(
    Pair(CAGE_STATUS_NEED_CLEAN, "Нужна чистка"),
    Pair(CAGE_STATUS_NEED_REPAIR, "Нужен ремонт")
)