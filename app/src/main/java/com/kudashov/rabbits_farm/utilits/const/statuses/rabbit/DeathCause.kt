package com.kudashov.rabbits_farm.utilits.const.statuses.rabbit

const val DEATH_CAUSE_SLAUGHTER: String = "S"
const val DEATH_CAUSE_MOTHER: String = "M"
const val DEATH_CAUSE_DISEASE: String = "I"
const val DEATH_CAUSE_WEAKNESS: String = "D"
const val DEATH_CAUSE_OVERHEATING: String = "H"
const val DEATH_CAUSE_COLD: String = "C"
const val DEATH_CAUSE_OTHER: String = "E"

val DEATH_CAUSES = hashMapOf(
    DEATH_CAUSE_SLAUGHTER to "Убой",
    DEATH_CAUSE_MOTHER to "Мать",
    DEATH_CAUSE_DISEASE to "Болезнь",
    DEATH_CAUSE_WEAKNESS to "Слабость (Генетика)",
    DEATH_CAUSE_OVERHEATING to "Перегрев",
    DEATH_CAUSE_COLD to "Холод",
    DEATH_CAUSE_OTHER to "Другое"
)