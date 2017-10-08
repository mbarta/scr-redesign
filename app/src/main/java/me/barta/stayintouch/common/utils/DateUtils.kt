package me.barta.stayintouch.common.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import java.util.*

/**
 * Date manipulating utilities
 */

fun LocalDateTime.toLegacyDate(): Date = Date(this.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())