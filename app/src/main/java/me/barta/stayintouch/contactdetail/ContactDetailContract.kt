package me.barta.stayintouch.contactdetail

import me.barta.stayintouch.common.ui.MVPContract
import me.barta.stayintouch.datastore.models.ContactPerson

/**
 * Contract between ContactDetailActivity and ContactDetailPresenter
 */
interface ContactDetailContract {
    interface View : MVPContract.View {
        fun displayContact(contact: ContactPerson)
    }

    interface Presenter<V : View> : MVPContract.Presenter<V> {
        fun loadContactById(contactId: Int)
    }

    interface Component<V : View , out P : Presenter<V>> : MVPContract.Component<V, P>
}