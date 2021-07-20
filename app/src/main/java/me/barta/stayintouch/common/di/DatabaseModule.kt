package me.barta.stayintouch.common.di

import dagger.Module
import dagger.Provides
import me.barta.stayintouch.data.database.DatabaseHelper

@Module
class DatabaseModule {

    @ApplicationScope
    @Provides
    fun provideDatabaseHelper(): DatabaseHelper = DatabaseHelper()
}