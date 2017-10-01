package me.barta.stayintouch.contactlist

import android.content.Context
import me.barta.stayintouch.common.ui.MVPPresenter
import javax.inject.Inject

/**
 * Contact list Presenter
 */
class ContactListPresenter
@Inject
constructor(private val context: Context) : MVPPresenter<ContactListContract.View>(), ContactListContract.Presenter<ContactListContract.View> {

}