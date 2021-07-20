package me.barta.stayintouch.data.mappers

import me.barta.stayintouch.data.models.ContactPerson
import me.barta.stayintouch.network.dto.ContactPersonDto
import javax.inject.Inject

class ContactPersonMapper @Inject constructor(private val categoryMapper: ContactCategoryMapper) {
    fun toEntity(dto: ContactPersonDto) = ContactPerson(
            id = dto.id,
            firstName = dto.firstName,
            lastName = dto.lastName,
            category = categoryMapper.toEntity(dto.category),
            lastContact = dto.lastContact,
            nextContact = dto.nextContact,
            birthday = dto.birthday,
            photo = dto.photo,
            contactFreq = dto.contactFreq,
            karma = dto.karma,
    )
}