package me.barta.stayintouch.network.dto

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * DataClass representing a Contact person DTO
 */
data class ContactPersonDto(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val category: ContactCategoryDto,
        val lastContact: LocalDateTime,
        val nextContact: LocalDateTime,
        val birthday: LocalDate,
        val photo: String,
        val contactFreq: String,
        val karma: Int
)