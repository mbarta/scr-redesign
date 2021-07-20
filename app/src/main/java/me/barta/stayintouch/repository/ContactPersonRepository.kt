package me.barta.stayintouch.repository

import me.barta.stayintouch.data.mappers.ContactPersonMapper
import me.barta.stayintouch.data.models.ContactPerson
import me.barta.stayintouch.network.FakeApi
import javax.inject.Inject

class ContactPersonRepository @Inject constructor(
        private val api: FakeApi,
        private val mapper: ContactPersonMapper,
) {
    suspend fun loadContactsForCategory(categoryId: Int): List<ContactPerson> {
        return api.loadContactsForCategory(categoryId).map { mapper.toEntity(it) }
    }

    suspend fun loadContactById(contactId: Int): ContactPerson {
        return mapper.toEntity(api.loadContactById(contactId))
    }
}