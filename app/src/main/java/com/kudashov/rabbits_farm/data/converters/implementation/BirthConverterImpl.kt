package com.kudashov.rabbits_farm.data.converters.implementation

import android.os.Build
import android.util.Log
import com.kudashov.rabbits_farm.data.converters.BirthConverter
import com.kudashov.rabbits_farm.data.domain.BirthDomain
import com.kudashov.rabbits_farm.data.dto.BirthDto
import com.kudashov.rabbits_farm.data.dto.CageDto
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class BirthConverterImpl : BirthConverter{

    override fun convertBirthFromApiToDomain(birthDto: BirthDto): BirthDomain = BirthDomain(
        birthDto.id,
        getNumberOfCage(birthDto.cage),
        getDurationPregnancy(birthDto.last_fertilisation),
        birthDto.is_confirmed
    )

    private fun getNumberOfCage(cage: CageDto): String {
        return cage.farm_number.toString() + cage.number.toString() + cage.letter
    }

    private fun getDurationPregnancy(birthday: String?): String {
        Log.d("TAG", "getDurationPregnancy: $birthday")
        return if (birthday != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val today = dateFormat.format(Date())

            val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            val difference = ChronoUnit.DAYS.between(
                LocalDate.parse(birthday.substring(0, 10), format),
                LocalDate.parse(today.substring(0, 10), format)
            )

            "$difference дн"
        } else "???"
    }
}