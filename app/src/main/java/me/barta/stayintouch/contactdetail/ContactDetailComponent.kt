package me.barta.stayintouch.contactdetail

import dagger.Component
import me.barta.stayintouch.common.di.ActivityScope
import me.barta.stayintouch.common.di.ApplicationComponent

/**
 * Contact detail Component
 */
@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class))
interface ContactDetailComponent : ContactDetailContract.Component<ContactDetailContract.View, ContactDetailPresenter> {
    override fun presenter(): ContactDetailPresenter
}