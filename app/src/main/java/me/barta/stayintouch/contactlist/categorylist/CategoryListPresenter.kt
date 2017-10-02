package me.barta.stayintouch.contactlist.categorylist

import me.barta.stayintouch.common.ui.MVPPresenter
import me.barta.stayintouch.datastore.database.DatabaseHelper
import javax.inject.Inject

/**
 * Contact list Presenter
 */
class CategoryListPresenter
@Inject
constructor(private val databaseHelper: DatabaseHelper) :
        MVPPresenter<CategoryListContract.View>(), CategoryListContract.Presenter<CategoryListContract.View> {

    override fun loadContactsByCategory(categoryId: Int) {
        val contacts = databaseHelper.loadContactsForCategory(categoryId)
        getView()?.presentLoadedData(contacts)
    }
}