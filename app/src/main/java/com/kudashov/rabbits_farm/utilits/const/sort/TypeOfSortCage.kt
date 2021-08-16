package com.kudashov.rabbits_farm.utilits.const.sort

const val SORT_CAGE_FARM_NUMBER = "farm_number"
const val SORT_CAGE_FARM_NUMBER_INV = "-farm_number"
const val SORT_CAGE_NUMBER = "number"
const val SORT_CAGE_NUMBER_INV = "-number"
const val SORT_CAGE_NUMBER_RABBITS = "number_rabbits"
const val SORT_CAGE_NUMBER_RABBITS_INV = "-number_rabbits"
const val SORT_CAGE_STATUS = "status"
const val SORT_CAGE_STATUS_INV = "-status"

val TYPES_SORT_CAGE = hashMapOf(
    SORT_CAGE_FARM_NUMBER to "По № фермы (возр)",
    SORT_CAGE_FARM_NUMBER_INV to "По № фермы (убыв)",
    SORT_CAGE_NUMBER to "По № клетки (возр)",
    SORT_CAGE_NUMBER_INV to  "По № клетки (убыв)",
    SORT_CAGE_NUMBER_RABBITS to "По кол-ву кролей(возр)",
    SORT_CAGE_NUMBER_RABBITS_INV to "По кол-ву кролей(убыв)",
    SORT_CAGE_STATUS to "По статусу (возр)",
    SORT_CAGE_STATUS_INV to  "По статусу (убыв)"
)