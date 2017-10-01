package me.barta.stayintouch.contactlist.categorylist

import me.barta.stayintouch.common.ui.MVPContract
import me.barta.stayintouch.datastore.models.ContactPerson

/**
 * Contract between CategoryListFragment and CategoryListPresenter
 */
interface CategoryListContract {
    interface View : MVPContract.View {
        fun presentLoadedData(data: List<ContactPerson>)
    }

    interface Presenter<V : View> : MVPContract.Presenter<V> {
        fun loadContactsByCategory(categoryId: Int)
    }

    interface Component<V : View , out P : Presenter<V>> : MVPContract.Component<V,P>
}