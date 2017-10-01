package me.barta.stayintouch.contactlist

import me.barta.stayintouch.common.ui.MVPPresenter
import me.barta.stayintouch.datastore.database.DatabaseHelper
import javax.inject.Inject

/**
 * Contact list Presenter
 */
class ContactListPresenter
@Inject
constructor(private val databaseHelper: DatabaseHelper) :
        MVPPresenter<ContactListContract.View>(), ContactListContract.Presenter<ContactListContract.View> {

    override fun loadData() {
        println(databaseHelper.loadCategories())
    }
}