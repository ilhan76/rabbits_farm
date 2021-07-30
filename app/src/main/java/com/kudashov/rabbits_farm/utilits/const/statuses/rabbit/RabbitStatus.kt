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
    Pair(RABBIT_STATUS_READY_TO_BREED, "Г. к размн."),
    Pair(RABBIT_STATUS_REST, "Отдыхает"),

    Pair(RABBIT_STATUS_READY_TO_BREED, "Г. к размн."),
    Pair(RABBIT_STATUS_UNCONFIRMED_PREGNANCY, "Непод. бер."),
    Pair(RABBIT_STATUS_NEED_PREGNANCY_CHECKUP, "Нуж. осм. на бер."),
    Pair(RABBIT_STATUS_CONFIRMED_PREGNANCY, "Подтв. бер."),
    Pair(RABBIT_STATUS_FEEDS_BABY, "Корм. кр."),

    Pair(RABBIT_STATUS_NEED_VACCINATION, "Н. вакц."),
    Pair(RABBIT_STATUS_NEED_INSPECTION_SLAUGHTER, "Н. осм. пер. уб."),
    Pair(RABBIT_STATUS_FEEDS_WITHOUT_COCCIDIOSTATIC, "Корм. без кокц."),
    Pair(RABBIT_STATUS_SLAUGHTER, "Г. к уб."),

    Pair(RABBIT_STATUS_NEED_JIGGING, "Н. отс."),
    Pair(RABBIT_STATUS_FED_BY_MOTHER, "Корм. у мат.")
)
