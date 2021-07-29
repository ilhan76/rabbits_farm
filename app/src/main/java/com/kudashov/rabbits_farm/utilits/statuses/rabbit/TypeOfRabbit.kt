package com.kudashov.rabbits_farm.utilits.statuses.rabbit

class TypeOfRabbit {
    companion object{
        const val baby: String = "B"
        const val death: String = "D"
        const val fattening: String = "F"
        const val mather: String = "M"
        const val father: String = "P"

        val types = hashMapOf(
            baby to "Крольчонок",
            death to "Мертвый кролик",
            fattening to "Откормочный",
            mather to "Самка",
            father to "Самец"
        )
    }


}

const val RABBIT_TYPE_BABY: String = "B"
const val RABBIT_TYPE_DEATH: String = "D"
const val RABBIT_TYPE_FATTENING: String = "F"
const val RABBIT_TYPE_MATHER: String = "M"
const val RABBIT_TYPE_FATHER: String = "P"

val TYPES_PAIR = listOf<Pair<String, String>>(
    Pair(RABBIT_TYPE_BABY, "Крольчонок"),
    Pair(RABBIT_TYPE_DEATH, "Мертвый кролик"),
    Pair(RABBIT_TYPE_FATTENING, "Откормочный"),
    Pair(RABBIT_TYPE_FATHER, "Самка"),
    Pair(RABBIT_TYPE_MATHER, "Самец")
)