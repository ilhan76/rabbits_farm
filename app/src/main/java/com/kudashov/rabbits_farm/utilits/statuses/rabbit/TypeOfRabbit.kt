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