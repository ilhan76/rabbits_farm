package com.kudashov.rabbits_farm.data.converters

import android.os.Build
import com.kudashov.rabbits_farm.data.dto.BirthDto
import com.kudashov.rabbits_farm.data.dto.CageDto
import com.kudashov.rabbits_farm.data.domain.BirthListItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList

class BirthMapper {
    companion object {
        fun fromApiToListItem(list: List<BirthDto>): List<BirthListItem> {
            val newList: MutableList<BirthListItem> = ArrayList()
            for (element in list) {
                newList.add(
                    fromApiToItem(element)
                )
            }
            return newList
        }

        private fun fromApiToItem(birthDto: BirthDto): BirthListItem = BirthListItem(
            birthDto.id,
            getNumberOfCage(birthDto.cage),
            getDurationPregnancy(birthDto.last_fertilisation),
            birthDto.is_confirmed
        )

        private fun getNumberOfCage(cage: CageDto): String {
            return cage.farm_number.toString() + cage.number.toString() + cage.letter
        }
        private fun getDurationPregnancy(birthday: String?): String {
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
    }
}