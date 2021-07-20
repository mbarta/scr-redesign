package me.barta.stayintouch.network

import me.barta.stayintouch.network.dto.ContactCategoryDto
import me.barta.stayintouch.network.dto.ContactPersonDto
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * API mock providing application data
 */
class FakeApi @Inject constructor(private val delayHelper: NetworkDelayHelper) {

    suspend fun loadCategories(): List<ContactCategoryDto> {
        delayHelper.simulateNetworkDelay()
        return CATEGORIES
    }
    
    suspend fun loadContactsForCategory(categoryId: Int): List<ContactPersonDto> {
        delayHelper.simulateNetworkDelay()
        return CONTACTS.shuffled()
    }

    suspend fun loadContactById(contactId: Int): ContactPersonDto {
        delayHelper.simulateNetworkDelay(1)
        return CONTACTS.single { it.id == contactId }
    }

    companion object {

        private val CATEGORIES = arrayListOf(
                ContactCategoryDto(0, "Friends"),
                ContactCategoryDto(1, "Family"),
                ContactCategoryDto(2, "Work"),
                ContactCategoryDto(3, "Yoga"),
                ContactCategoryDto(4, "Golf"),
                ContactCategoryDto(5, "School")
        )

        private val FREQ_LIST = listOf("Once a week", "Twice a month", "Every Monday", "Every day")

        private val CONTACTS = arrayListOf(
                ContactPersonDto(0, "Kate", "Snow", CATEGORIES[0],
                        LocalDateTime.of(2017, 9, 23, 14, 55),
                        LocalDateTime.of(2017, 10, 22, 11, 21),
                        LocalDate.of(1982, 1, 1), "file:///android_asset/photos/01.jpg",
                        FREQ_LIST[0], 3),
                ContactPersonDto(1, "Rebeca", "Day", CATEGORIES[1],
                        LocalDateTime.of(2017, 9, 10, 11, 21),
                        LocalDateTime.of(2017, 10, 16, 11, 4),
                        LocalDate.of(1974, 5, 5), "file:///android_asset/photos/02.jpg",
                        FREQ_LIST[2], 4),
                ContactPersonDto(2, "Paul", "Johnson", CATEGORIES[0],
                        LocalDateTime.of(2017, 9, 9, 11, 21),
                        LocalDateTime.of(2017, 10, 14, 11, 21),
                        LocalDate.of(1993, 1, 11), "file:///android_asset/photos/03.jpg",
                        FREQ_LIST[1], 1),
                ContactPersonDto(3, "Richard", "King", CATEGORIES[1],
                        LocalDateTime.of(2017, 9, 23, 11, 2),
                        LocalDateTime.of(2017, 10, 18, 11, 1),
                        LocalDate.of(1994, 4, 22), "file:///android_asset/photos/04.jpg",
                        FREQ_LIST[0], 5),
                ContactPersonDto(4, "Monica", "Brown", CATEGORIES[2],
                        LocalDateTime.of(2017, 9, 5, 22, 21),
                        LocalDateTime.of(2017, 10, 22, 11, 3),
                        LocalDate.of(1988, 2, 3), "file:///android_asset/photos/05.jpg",
                        FREQ_LIST[3], 5)
        )
    }
}
