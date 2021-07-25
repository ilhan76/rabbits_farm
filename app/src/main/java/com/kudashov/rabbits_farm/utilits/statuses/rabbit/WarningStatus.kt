package com.kudashov.rabbits_farm.utilits.statuses.rabbit

class WarningStatus {
    companion object {
        const val notEat: String = "NE"
        const val notDrink: String = "ND"
        const val gotSick: String = "GS"
        val warningStatuses = hashMapOf(
            notEat to "Не ест",
            notDrink to "Не пьет",
            gotSick to "Заболел"
        )
    }
}