package me.barta.stayintouch.repository

import me.barta.stayintouch.data.mappers.ContactCategoryMapper
import me.barta.stayintouch.data.models.ContactCategory
import me.barta.stayintouch.network.FakeApi
import javax.inject.Inject

class ContactCategoryRepository @Inject constructor(
        private val api: FakeApi,
        private val mapper: ContactCategoryMapper,
) {
    suspend fun loadContactCategories(): List<ContactCategory> {
        return api.loadCategories().map { mapper.toEntity(it) }
    }
}