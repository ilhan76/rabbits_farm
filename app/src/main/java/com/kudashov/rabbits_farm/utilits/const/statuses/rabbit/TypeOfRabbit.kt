package com.kudashov.rabbits_farm.utilits.const.statuses.rabbit

const val RABBIT_TYPE_BABY: String = "B"
const val RABBIT_TYPE_DEATH: String = "D"
const val RABBIT_TYPE_FATTENING: String = "F"
const val RABBIT_TYPE_MATHER: String = "M"
const val RABBIT_TYPE_FATHER: String = "P"

val TYPES_RABBIT = listOf(
    Pair(RABBIT_TYPE_BABY, "Крольчонок"),
    Pair(RABBIT_TYPE_DEATH, "Мертвый кролик"),
    Pair(RABBIT_TYPE_FATTENING, "Откормочный"),
    Pair(RABBIT_TYPE_FATHER, "Самка"),
    Pair(RABBIT_TYPE_MATHER, "Самец")
)