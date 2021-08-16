package com.kudashov.rabbits_farm.utilits.const.sort

const val SORT_TASKS_REPRODUCTION = "M"
const val SORT_TASKS_DEPOSITION_FROM_MOTHER = "B"
const val SORT_TASKS_VACCINATION = "V"
const val SORT_TASKS_INSPECTION = "I"
const val SORT_TASKS_SLAUGHTER = "S"
const val SORT_TASKS_DEPOSITION = "R"

val TYPES_SORT_TASK = hashMapOf(
    SORT_TASKS_REPRODUCTION to "Сначала \"Размножение\"",
    SORT_TASKS_DEPOSITION_FROM_MOTHER to "Сначала \"Отсадка от матери\"",
    SORT_TASKS_VACCINATION to "Сначала \"Вакцинация\"",
    SORT_TASKS_INSPECTION to "Сначала \"Отсмотр\"",
    SORT_TASKS_SLAUGHTER to "Сначала \"Убой\"",
    SORT_TASKS_DEPOSITION to "Сначала \"Отсадка\""
)