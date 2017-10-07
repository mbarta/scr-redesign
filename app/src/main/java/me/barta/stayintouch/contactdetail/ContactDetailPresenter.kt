package me.barta.stayintouch.contactdetail

import me.barta.stayintouch.common.ui.MVPPresenter
import me.barta.stayintouch.datastore.database.DatabaseHelper
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