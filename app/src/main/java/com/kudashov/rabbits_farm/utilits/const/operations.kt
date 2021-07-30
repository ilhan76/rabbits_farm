package com.kudashov.rabbits_farm.utilits.const

const val OPERATION_BIRTH = "B"
const val OPERATION_SLAUGHTER = "S"
const val OPERATION_VACCINATION = "V"
const val OPERATION_MATING = "M"
const val OPERATION_JIGGING = "J"

val OPERATIONS = hashMapOf(
    OPERATION_BIRTH to "Рождение",
    OPERATION_SLAUGHTER to "Убой",
    OPERATION_VACCINATION to "Вакцинация",
    OPERATION_MATING to "Спаривание",
    OPERATION_JIGGING to "Отсадка"
)