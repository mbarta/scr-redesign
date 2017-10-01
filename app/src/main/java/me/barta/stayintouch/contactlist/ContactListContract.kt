package me.barta.stayintouch.contactlist

import me.barta.stayintouch.common.ui.MVPContract

/**
 * Contract between ContactListActivity and ContactListPresenter
 */
interface ContactListContract {
    interface View : MVPContract.View {
//        fun showThings()
    }

    interface Presenter<V : View> : MVPContract.Presenter<V> {
        fun loadData()
    }

    interface Component<V : View , out P : Presenter<V>> : MVPContract.Component<V,P>
}