package me.barta.stayintouch.network

import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class NetworkDelayHelper @Inject constructor() {

    private val random = Random.Default

    suspend fun simulateNetworkDelay(maxMillis: Long = 1000) {
        val randomDelay = random.nextLong(maxMillis)
        delay(randomDelay)
    }
}