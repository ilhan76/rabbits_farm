package com.kudashov.rabbits_farm.utilits.statuses.cage

class StatusOfCage {
    companion object {
        const val needClean: String = "C"
        const val needRepair: String = "R"

        val statuses = hashMapOf(
            needClean to "Нужна чистка",
            needRepair to "Нужен ремонт"
        )
    }
}