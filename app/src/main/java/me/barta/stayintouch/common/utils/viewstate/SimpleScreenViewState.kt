package me.barta.stayintouch.common.utils.viewstate

sealed class SimpleScreenViewState<out T : Any>

object Loading : SimpleScreenViewState<Nothing>()
data class Success<out T : Any>(val data: T) : SimpleScreenViewState<T>()
data class Failure(val throwable: Throwable) : SimpleScreenViewState<Nothing>()