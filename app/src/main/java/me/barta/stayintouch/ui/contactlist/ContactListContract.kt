package me.barta.stayintouch.ui.contactlist

import androidx.fragment.app.Fragment
import me.barta.stayintouch.ui.base.MVPContract
import me.barta.stayintouch.data.models.ContactCategory

/**
 * Contract between ContactListActivity and ContactListPresenter
 */
interface ContactListContract {
    interface View : MVPContract.View

    interface Presenter<V : View> : MVPContract.Presenter<V> {
        fun loadCategories(): List<ContactCategory>
        fun getFragmentForPosition(position: Int): Fragment
    }

    interface Component<V : View , out P : Presenter<V>> : MVPContract.Component<V,P>
}