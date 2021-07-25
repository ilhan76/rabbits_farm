package com.kudashov.rabbits_farm.utilits.statuses.cage

class TypeOfCage {
    companion object {
        const val fattening = "fattening"
        const val mother = "mother"
        val types = hashMapOf(
            fattening to "Откормочная",
            mother to "Маточная"
        )
    }
}