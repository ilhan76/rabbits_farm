package com.kudashov.rabbits_farm.screens.farm.filters


class RabbitFilter {
    companion object{
        var page: Int = 1
        var pageSize: Int = 100
        var farmNumber: Int? = null
        var type: MutableList<String> = ArrayList()
        var breed: MutableList<Int> = ArrayList()
        var status: MutableList<String> = ArrayList()
        var ageFrom: Int? = null
        var ageTo: Int? = null
        var weightFrom: Double? = null
        var weightTo: Double? = null
        var isMale: Int? = null
        var orderBy: String? = null

        fun throwOff(){
            farmNumber = null
            type.clear()
            breed.clear()
            status.clear()
            ageFrom = null
            ageTo = null
            weightFrom = null
            weightTo = null
            isMale = null
        }
    }
}