package me.barta.stayintouch.common.di

import android.content.Context
import dagger.Component
import me.barta.stayintouch.data.database.DatabaseHelper


@ApplicationScope
@Component(modules = arrayOf(ApplicationModule::class, DatabaseModule::class))
interface ApplicationComponent {
    val applicationContext: Context
    val databaseHelper: DatabaseHelper
}