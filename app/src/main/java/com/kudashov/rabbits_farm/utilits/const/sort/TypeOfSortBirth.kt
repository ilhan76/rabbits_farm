package com.kudashov.rabbits_farm.utilits.const.sort

const val SORT_BIRTH_CAGE_NUMBER = "cage_number"
const val SORT_BIRTH_CAGE_NUMBER_INV = "-cage_number"
const val SORT_BIRTH_TIME = "birth_time"
const val SORT_BIRTH_TIME_INV = "-birth_time"

val TYPES_SORT_BIRTH = hashMapOf(
    SORT_BIRTH_CAGE_NUMBER to "По № клетки (возр)",
    SORT_BIRTH_CAGE_NUMBER_INV to "По № клтки (убыв)",
    SORT_BIRTH_TIME to "По времени беременности (возр)",
    SORT_BIRTH_TIME_INV to "По времени беременности (убыв)"
)