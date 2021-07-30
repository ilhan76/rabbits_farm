package com.kudashov.rabbits_farm.screens.farm.filters.cage

class CageFilter {
    companion object {
        var farmNumber: Int? = null
        var type: String? = null
        var isParallel: Int? = null
        var countOfRabbitFrom: Int? = null
        var countOfRabbitTo: Int? = null
        var status: MutableList<String> = ArrayList()
        var orderBy: String? = null

        override fun toString(): String {
            return "farmNumber " + farmNumber.toString() + "\n" +
                    "type " + type + "\n" +
                    "isParallel " + isParallel + "\n" +
                    "countOfRabbitFrom " + countOfRabbitFrom.toString() + "\n" +
                    "countOfRabbitTo " + countOfRabbitTo.toString() + "\n" +
                    "status " + status.toString() + "\n" +
                    "orderBy" + orderBy.toString()
        }

        fun throwOff() {
            farmNumber = null
            status.clear()
            type = null
            isParallel = null
            countOfRabbitFrom = null
            countOfRabbitTo = null
        }
    }
}