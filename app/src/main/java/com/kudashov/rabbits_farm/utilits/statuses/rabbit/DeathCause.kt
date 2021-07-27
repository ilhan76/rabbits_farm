package com.kudashov.rabbits_farm.utilits.statuses.rabbit

class DeathCause {
    companion object {
        const val slaughter: String = "S"
        const val mother: String = "M"
        const val disease: String = "I"
        const val weakness: String = "D"
        const val overheating: String = "H"
        const val cold: String = "C"
        const val other: String = "E"

        val deathCauses = hashMapOf(
            slaughter to "Убой",
            mother to "Мать",
            disease to "Болезнь",
            weakness to "Слабость (Генетика)",
            overheating to "Перегрев",
            cold to "Холод",
            other to "Другое"
        )
    }
}