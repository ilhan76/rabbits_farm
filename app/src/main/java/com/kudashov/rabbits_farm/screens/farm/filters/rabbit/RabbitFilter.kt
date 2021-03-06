package com.kudashov.rabbits_farm.screens.farm.filters.rabbit

class RabbitFilter {
    companion object {
        var farmNumber: Int? = null
        var type: MutableList<String> = ArrayList()
        var breed: Int? = null
        var status: String? = null
        var ageFrom: Int? = null
        var ageTo: Int? = null
        var cageNumberFrom: Int? = null
        var cageNumberTo: Int? = null
        var isMale: Int? = null
        var orderBy: String? = null

        override fun toString(): String {
            return farmNumber.toString() + "\n" +
                    type.toString() + "\n" +
                    breed.toString() + "\n" +
                    status.toString() + "\n" +
                    ageFrom.toString() + "\n" +
                    ageTo.toString() + "\n" +
                    cageNumberFrom.toString() + "\n" +
                    cageNumberTo.toString() + "\n" +
                    isMale.toString() + "\n" +
                    orderBy.toString()
        }

        fun throwOff() {
            farmNumber = null
            type.clear()
            breed = null
            status = null
            ageFrom = null
            ageTo = null
            cageNumberFrom = null
            cageNumberTo = null
            isMale = null
        }
    }
}