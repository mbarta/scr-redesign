package me.barta.stayintouch.contactlist

import android.support.v4.app.Fragment
import me.barta.stayintouch.common.ui.MVPPresenter
import me.barta.stayintouch.datastore.database.DatabaseHelper
import me.barta.stayintouch.datastore.models.ContactCategory
import javax.inject.Inject

/**
 * Contact list Presenter
 */
class ContactListPresenter
@Inject
constructor(private val databaseHelper: DatabaseHelper) :
        MVPPresenter<ContactListContract.View>(), ContactListContract.Presenter<ContactListContract.View> {

    override fun loadCategories(): List<ContactCategory> = databaseHelper.loadCategories()

    override fun getFragmentForPosition(position: Int): Fragment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
