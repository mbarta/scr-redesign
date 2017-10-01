package me.barta.stayintouch.common.di

import dagger.Module
import dagger.Provides
import me.barta.stayintouch.datastore.database.DatabaseHelper

@Module
class DatabaseModule {

    @ApplicationScope
    @Provides
    fun provideDatabaseHelper(): DatabaseHelper = DatabaseHelper()
}