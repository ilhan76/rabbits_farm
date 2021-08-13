package com.kudashov.rabbits_farm.data.converters.implementation

import android.os.Build
import com.kudashov.rabbits_farm.data.converters.FarmConverter
import com.kudashov.rabbits_farm.data.domain.CageDomain
import com.kudashov.rabbits_farm.data.domain.OperationDomain
import com.kudashov.rabbits_farm.data.domain.RabbitDomain
import com.kudashov.rabbits_farm.data.domain.RabbitMoreInfDomain
import com.kudashov.rabbits_farm.data.dto.CageDto
import com.kudashov.rabbits_farm.data.dto.OperationDto
import com.kudashov.rabbits_farm.data.dto.RabbitDto
import com.kudashov.rabbits_farm.data.dto.RabbitMoreInfDto
import com.kudashov.rabbits_farm.utilits.const.OPERATIONS
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.CAGE_STATUS_NEED_CLEAN
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.CAGE_STATUS_NEED_REPAIR
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.CAGE_TYPE_FATTENING
import com.kudashov.rabbits_farm.utilits.const.statuses.cage.CAGE_TYPE_MOTHER
import com.kudashov.rabbits_farm.utilits.const.statuses.rabbit.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class FarmConverterImpl : FarmConverter {
    override fun convertRabbitFromApiToDomain(rabbitDto: RabbitDto): RabbitDomain =
        RabbitDomain(
        rabbitDto.id,
        getNumberOfCage(rabbitDto.cage),
        getAge(rabbitDto.birthday),
        rabbitDto.is_male,
        getType(rabbitDto.current_type)
    )
    override fun convertRabbitMoreInfFromApiToDomain(rabbit: RabbitMoreInfDto): RabbitMoreInfDomain =
        RabbitMoreInfDomain(
            rabbit.id,
            rabbit.is_male,
            getBirthday(rabbit.birthday),
            getAge(rabbit.birthday),
            rabbit.breed,
            rabbit.current_type,
            getType(rabbit.current_type),
            rabbit.cage,
            getStatuses(rabbit.status),
            rabbit.output,
            rabbit.output_efficiency,
            rabbit.weight
        )
    override fun convertCageFromApiToDomain(cageDto: CageDto): CageDomain =
        CageDomain(
            cageDto.id,
            getNumberOfCage(cageDto),
            cageDto.farm_number.toString(),
            getCageType(cageDto),
            getCageStatus(cageDto.status)
        )
    override fun convertOperationFromApiToDomain(operationDto: OperationDto): OperationDomain =
        OperationDomain(
        operationDto.rabbit_id,
        operationDto.time.substring(0, 10),
        getOperation(operationDto.type)
    )

    private fun getNumberOfCage(cage: CageDto): String {
        return cage.farm_number.toString() + "-" + cage.number.toString() + "(${cage.letter})"
    }
    private fun getType(currentType: String): String {
        return when (currentType) {
            RABBIT_TYPE_BABY -> RABBIT_TYPE_UI_BABY
            RABBIT_TYPE_DEATH -> RABBIT_TYPE_UI_DEATH
            RABBIT_TYPE_FATTENING -> RABBIT_TYPE_UI_FATTENING
            RABBIT_TYPE_MATHER -> RABBIT_TYPE_UI_MATHER
            RABBIT_TYPE_FATHER -> RABBIT_TYPE_UI_FATHER
            else -> "???"
        }
    }

    private fun getAge(birthday: String?): String {
        return if (birthday != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val today = dateFormat.format(Date())

            val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val difference = ChronoUnit.DAYS.between(
                LocalDate.parse(birthday.substring(0, 10), format),
                LocalDate.parse(today.substring(0, 10), format)
            )

            "$difference"
        } else "???"
    }
    private fun getBirthday(birthdayIso: String?): String {
        return if (birthdayIso != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val birthday = LocalDate.parse(birthdayIso.substring(0, 10), format)

            "$birthday"
        } else "???"
    }
    private fun getStatuses(statuses: List<String>): String {
        var res = ""
        for (i in STATUSES_RABBIT) {
            if (statuses.contains(i.first)) res += "\n" + i.second
        }
        if (res.isEmpty()) res + "Статус не определен"
        return res
    }

    private fun getCageType(cageDto: CageDto): String {
        return when(cageDto.type){
            CAGE_TYPE_MOTHER -> {
                if (cageDto.is_parallel) "МАТ ||"
                else "МАТ |"
            }
            CAGE_TYPE_FATTENING -> "ОТКОРМ"
            else -> "???"
        }
    }
    private fun getCageStatus(list: List<String>): String {
        var status = ""

        if (list.contains(CAGE_STATUS_NEED_CLEAN)) status += "Уб."
        if (list.contains(CAGE_STATUS_NEED_REPAIR)) status += " Рем."
        if (status.isEmpty()) status += "-----"
        return status
    }

    private fun getOperation(type: String): String {
        return OPERATIONS[type] ?: "???"
    }
}