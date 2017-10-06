package me.barta.stayintouch.datastore.database

import me.barta.stayintouch.datastore.models.ContactCategory
import me.barta.stayintouch.datastore.models.ContactPerson
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import java.util.*

/**
 * DB mock providing application data
 */
class DatabaseHelper {

    companion object {

        @JvmStatic private val CATEGORIES = arrayListOf(
                ContactCategory(0, "Friends"),
                ContactCategory(1, "Family"),
                ContactCategory(2, "Work"),
                ContactCategory(3, "Yoga"),
                ContactCategory(4, "Golf"),
                ContactCategory(5, "School")
        )

        @JvmStatic private val CONTACTS = arrayListOf(
                ContactPerson(0, "Kate", "Snow", CATEGORIES[0],
                        LocalDateTime.of(2017, 9, 23, 14, 55),
                        LocalDateTime.of(2017, 10, 2, 11, 21),
                        LocalDate.of(1982, 1, 1), "file:///android_asset/photos/01.jpg"),
                ContactPerson(0, "Rebeca", "Day", CATEGORIES[1],
                        LocalDateTime.of(2017, 9, 10, 11, 21),
                        LocalDateTime.of(2017, 10, 10, 11, 4),
                        LocalDate.of(1974, 5, 5), "file:///android_asset/photos/02.jpg"),
                ContactPerson(0, "Paul", "Johnson", CATEGORIES[0],
                        LocalDateTime.of(2017, 9, 9, 11, 21),
                        LocalDateTime.of(2017, 10, 11, 11, 21),
                        LocalDate.of(1993, 1, 11), "file:///android_asset/photos/03.jpg"),
                ContactPerson(0, "Richard", "King", CATEGORIES[1],
                        LocalDateTime.of(2017, 9, 23, 11, 2),
                        LocalDateTime.of(2017, 10, 10, 11, 1),
                        LocalDate.of(1994, 4, 22), "file:///android_asset/photos/04.jpg"),
                ContactPerson(0, "Monica", "Brown", CATEGORIES[2],
                        LocalDateTime.of(2017, 9, 5, 22, 21),
                        LocalDateTime.of(2017, 10, 22, 11, 3),
                        LocalDate.of(1988, 2, 3), "file:///android_asset/photos/05.jpg")
        )
    }


    fun loadCategories(): List<ContactCategory> = CATEGORIES

    fun loadContactsForCategory(categoryId: Int): List<ContactPerson> = CONTACTS.shuffle()

    fun loadContactById(contactId: Int): ContactPerson = CONTACTS.single { it.id == contactId }


    private fun <T> Iterable<T>.shuffle(seed: Long? = null): List<T> {
        val list = this.toMutableList()
        val random = if (seed != null) Random(seed) else Random()
        Collections.shuffle(list, random)
        return list
    }

}