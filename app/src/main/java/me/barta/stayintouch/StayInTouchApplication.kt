package me.barta.stayintouch

import android.app.Application
import me.barta.stayintouch.common.di.ApplicationComponent
import me.barta.stayintouch.common.di.ApplicationModule
import me.barta.stayintouch.common.di.DaggerApplicationComponent
import me.barta.stayintouch.common.di.DatabaseModule

/**
 * Custom Application class
 */
internal class StayInTouchApplication : Application() {

    companion object {
        @JvmStatic lateinit var component: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        component = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .databaseModule(DatabaseModule())
                .build()
    }
}