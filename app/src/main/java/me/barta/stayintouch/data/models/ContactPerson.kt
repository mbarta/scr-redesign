package me.barta.stayintouch.data.models

import java.time.LocalDate
import java.time.LocalDateTime

data class ContactPerson(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val category: ContactCategory,
        val lastContact: LocalDateTime,
        val nextContact: LocalDateTime,
        val birthday: LocalDate,
        val photo: String,
        val contactFreq: String,
        val karma: Int
)