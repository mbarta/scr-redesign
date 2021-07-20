package me.barta.stayintouch.contactlist

import androidx.fragment.app.Fragment
import me.barta.stayintouch.common.ui.MVPPresenter
import me.barta.stayintouch.contactlist.categorylist.CategoryListFragment
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

    override fun getFragmentForPosition(position: Int): Fragment = CategoryListFragment.newInstance(position)
}
