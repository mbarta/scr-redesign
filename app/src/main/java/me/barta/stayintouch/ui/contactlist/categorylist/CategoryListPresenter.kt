package me.barta.stayintouch.ui.contactlist.categorylist

import me.barta.stayintouch.ui.base.MVPPresenter
import me.barta.stayintouch.data.database.DatabaseHelper
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