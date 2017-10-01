package me.barta.stayintouch.contactlist

import android.os.Bundle
import android.os.PersistableBundle
import me.barta.stayintouch.StayInTouchApplication
import me.barta.stayintouch.common.ui.MVPActivity

/**
 * Contact list Activity
 */
class ContactListActivity : MVPActivity<ContactListContract.View, ContactListPresenter, ContactListComponent>(), ContactListContract.View {

    override fun createComponent(): ContactListComponent =
            DaggerContactListComponent.builder().applicationComponent(StayInTouchApplication.component).build()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onResume() {
        super.onResume()
        presenter.loadData()
    }
}