package me.barta.stayintouch.common.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

/**
 * Date manipulating utilities
 */

fun LocalDateTime.toLegacyDate(): Date = Date(this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())