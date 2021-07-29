package com.kudashov.rabbits_farm.utilits.const.statuses.rabbit

// MALE
const val RABBIT_STATUS_READY_TO_BREED: String = "RF"
const val RABBIT_STATUS_REST: String = "R"
// FEMALE
const val RABBIT_STATUS_UNCONFIRMED_PREGNANCY: String = "UP"
const val RABBIT_STATUS_NEED_PREGNANCY_CHECKUP: String = "NI"
const val RABBIT_STATUS_CONFIRMED_PREGNANCY: String = "CP"
const val RABBIT_STATUS_FEEDS_BABY: String = "FB"
//FATTENING
const val RABBIT_STATUS_NEED_VACCINATION: String = "NV"
const val RABBIT_STATUS_NEED_INSPECTION_SLAUGHTER: String = "NI"
const val RABBIT_STATUS_FEEDS_WITHOUT_COCCIDIOSTATIC: String = "WC"
const val RABBIT_STATUS_SLAUGHTER: String = "RS"
// BABY
const val RABBIT_STATUS_NEED_JIGGING: String = "NJ"
const val RABBIT_STATUS_FED_BY_MOTHER: String = "RS"

val STATUSES_RABBIT = listOf(
    Pair(RABBIT_STATUS_READY_TO_BREED, "Готов к разможению"),
    Pair(RABBIT_STATUS_REST, "Отдыхает"),

    Pair(RABBIT_STATUS_READY_TO_BREED, "Готова к размножению"),
    Pair(RABBIT_STATUS_UNCONFIRMED_PREGNANCY, "Неподтвержденная беременность"),
    Pair(RABBIT_STATUS_NEED_PREGNANCY_CHECKUP, "Нужен осмотр на беременность"),
    Pair(RABBIT_STATUS_CONFIRMED_PREGNANCY, "Подтвержденная беременность"),
    Pair(RABBIT_STATUS_FEEDS_BABY, "Кормит крольчат"),

    Pair(RABBIT_STATUS_NEED_VACCINATION, "Нужна вакцинация"),
    Pair(RABBIT_STATUS_NEED_INSPECTION_SLAUGHTER, "Нужен осмотр перед убоем"),
    Pair(RABBIT_STATUS_FEEDS_WITHOUT_COCCIDIOSTATIC, "Кормится без кокцидиостатка"),
    Pair(RABBIT_STATUS_SLAUGHTER, "Готов к убою"),

    Pair(RABBIT_STATUS_NEED_JIGGING, "Нужна отсадка"),
    Pair(RABBIT_STATUS_FED_BY_MOTHER, "Кормится у матери")
)
