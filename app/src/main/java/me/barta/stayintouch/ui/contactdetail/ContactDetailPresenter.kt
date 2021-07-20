package me.barta.stayintouch.ui.contactdetail

import me.barta.stayintouch.ui.base.MVPPresenter
import me.barta.stayintouch.data.database.DatabaseHelper
import javax.inject.Inject

/**
 * Contact detail Presenter
 */
class ContactDetailPresenter
@Inject
constructor(private val databaseHelper: DatabaseHelper) :
        MVPPresenter<ContactDetailContract.View>(), ContactDetailContract.Presenter<ContactDetailContract.View> {

    override fun loadContactById(contactId: Int) {
        val contact = databaseHelper.loadContactById(contactId)
        getView()?.displayContact(contact)
    }
}