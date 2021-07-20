package me.barta.stayintouch.data.mappers

import me.barta.stayintouch.data.models.ContactCategory
import me.barta.stayintouch.network.dto.ContactCategoryDto
import javax.inject.Inject

class ContactCategoryMapper @Inject constructor() {
    fun toEntity(dto: ContactCategoryDto) = ContactCategory(
            id = dto.id,
            name = dto.name,
    )
}