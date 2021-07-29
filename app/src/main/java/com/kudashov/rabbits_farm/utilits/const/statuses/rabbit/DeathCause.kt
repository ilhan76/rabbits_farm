package com.kudashov.rabbits_farm.utilits.const.statuses.rabbit

const val DEATH_CAUSE_SLAUGHTER: String = "S"
const val DEATH_CAUSE_MOTHER: String = "M"
const val DEATH_CAUSE_DISEASE: String = "I"
const val DEATH_CAUSE_WEAKNESS: String = "D"
const val DEATH_CAUSE_OVERHEATING: String = "H"
const val DEATH_CAUSE_COLD: String = "C"
const val DEATH_CAUSE_OTHER: String = "E"

val DEATH_CAUSES = listOf(
    Pair(DEATH_CAUSE_SLAUGHTER, "Убой"),
    Pair(DEATH_CAUSE_MOTHER, "Мать"),
    Pair(DEATH_CAUSE_DISEASE, "Болезнь"),
    Pair(DEATH_CAUSE_WEAKNESS, "Слабость (Генетика)"),
    Pair(DEATH_CAUSE_OVERHEATING, "Перегрев"),
    Pair(DEATH_CAUSE_COLD, "Холод"),
    Pair(DEATH_CAUSE_OTHER, "Другое")
)