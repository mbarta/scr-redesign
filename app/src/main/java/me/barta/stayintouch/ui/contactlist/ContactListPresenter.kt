package me.barta.stayintouch.ui.contactlist

import androidx.fragment.app.Fragment
import me.barta.stayintouch.ui.base.MVPPresenter
import me.barta.stayintouch.ui.contactlist.categorylist.CategoryListFragment
import me.barta.stayintouch.data.database.DatabaseHelper
import me.barta.stayintouch.data.models.ContactCategory
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
