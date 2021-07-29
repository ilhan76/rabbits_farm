package com.kudashov.rabbits_farm.utilits.statuses.rabbit

class RabbitStatus {
    companion object {
        const val readyToBreed: String = "RF"
        const val rest: String = "R"
        val male = hashMapOf(
            readyToBreed to "Готов к разможению",
            rest to "Отдыхает"
        )

        const val unconfirmedPregnancy: String = "UP"
        const val needPregnancyCheckup: String = "NI"
        const val confirmedPregnancy: String = "CP"
        const val feedsBaby: String = "FB"
        val female = hashMapOf(
            readyToBreed to "Готова к размножению",
            unconfirmedPregnancy to "Неподтвержденная беременность",
            needPregnancyCheckup to "Нужен осмотр на беременность",
            confirmedPregnancy to "Подтвержденная беременность",
            feedsBaby to "Кормит крольчат"
        )

        const val needVaccination: String = "NV"
        const val needInspectionBeforeSlaughter: String = "NI"
        const val feedsWithoutCoccidiostatic: String = "WC"
        const val readySlaughter: String = "RS"
        val fattening = hashMapOf(
            needVaccination to "Нужна вакцинация",
            needInspectionBeforeSlaughter to "Нужен осмотр перед убоем",
            feedsWithoutCoccidiostatic to "Кормится без кокцидиостатка",
            readySlaughter to "Готов к убою"
        )

        const val needJigging: String = "NJ"
        const val fedByMother: String = "RS"
        val baby = hashMapOf(
            needJigging to "Нужна отсадка",
            fedByMother to "Кормится у матери"
        )
    }
}


