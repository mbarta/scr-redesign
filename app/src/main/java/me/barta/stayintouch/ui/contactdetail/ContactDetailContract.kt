package me.barta.stayintouch.ui.contactdetail

import me.barta.stayintouch.ui.base.MVPContract
import me.barta.stayintouch.data.models.ContactPerson

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