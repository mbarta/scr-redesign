package me.barta.stayintouch.contactlist

import me.barta.stayintouch.StayInTouchApplication
import me.barta.stayintouch.common.ui.MVPActivity

/**
 * Contact list Activity
 */
class ContactListActivity : MVPActivity<ContactListContract.View, ContactListPresenter, ContactListComponent>(), ContactListContract.View {

    override fun createComponent(): ContactListComponent =
            DaggerContactListComponent.builder().applicationComponent(StayInTouchApplication.component).build()
}