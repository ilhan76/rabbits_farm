package com.kudashov.rabbits_farm.utilits.const.sort

const val SORT_RABBIT_AGE = "age"
const val SORT_RABBIT_AGE_INV = "-age"
const val SORT_RABBIT_SEX = "sex"
const val SORT_RABBIT_FARM_NUMBER = "farm_number"
const val SORT_RABBIT_CAGE_NUMBER = "cage_number"
const val SORT_RABBIT_TYPE = "type"
const val SORT_RABBIT_BREED = "breed"
const val SORT_RABBIT_STATUS = "status"
const val SORT_RABBIT_WEIGHT = "weight"
const val SORT_RABBIT_WEIGHT_INV = "-weight"

val TYPES_SORT_RABBIT = hashMapOf(
    SORT_RABBIT_AGE to "По возрасту (возр)",
    SORT_RABBIT_AGE_INV to "По возрасту (убыв)",
    SORT_RABBIT_SEX to "По полу",
    SORT_RABBIT_FARM_NUMBER to "По номеру фермы",
    SORT_RABBIT_CAGE_NUMBER to "По номеру клетки",
    SORT_RABBIT_TYPE to "По типу",
    SORT_RABBIT_BREED to "По породе",
    SORT_RABBIT_STATUS to "По статусу",
    SORT_RABBIT_WEIGHT to "По весу (возр)",
    SORT_RABBIT_WEIGHT_INV to "По весу (убыв)"
)