package me.barta.stayintouch.ui.contactlist

import dagger.Component
import me.barta.stayintouch.common.di.ActivityScope
import me.barta.stayintouch.common.di.ApplicationComponent

/**
 * Contact list Component
 */
@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ContactListComponent : ContactListContract.Component<ContactListContract.View, ContactListPresenter> {
    override fun presenter(): ContactListPresenter
}