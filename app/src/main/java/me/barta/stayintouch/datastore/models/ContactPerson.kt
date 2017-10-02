package me.barta.stayintouch.datastore.models

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime

/**
 * DataClass representing a Contact person
 */
data class ContactPerson(
        val id: Int,
        val firstName: String,
        val lastName: String,
        val category: ContactCategory,
        val lastContact: LocalDateTime,
        val nextContact: LocalDateTime,
        val birthday: LocalDate,
        val photo: String
)