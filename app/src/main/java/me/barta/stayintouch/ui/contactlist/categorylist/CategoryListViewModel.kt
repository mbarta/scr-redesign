package me.barta.stayintouch.ui.contactlist.categorylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.barta.stayintouch.common.utils.viewstate.SimpleScreenViewState
import me.barta.stayintouch.common.utils.viewstate.Success
import me.barta.stayintouch.data.database.DatabaseHelper
import me.barta.stayintouch.data.models.ContactPerson
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(private val databaseHelper: DatabaseHelper) : ViewModel() {

    private val _viewState: MutableLiveData<SimpleScreenViewState<List<ContactPerson>>> = MutableLiveData()
    val viewState: LiveData<SimpleScreenViewState<List<ContactPerson>>> = _viewState

    fun loadContactsByCategory(categoryId: Int) {
        // TODO: coroutine, error handling, ...
        val contacts = databaseHelper.loadContactsForCategory(categoryId)
        _viewState.value = Success(contacts)
    }
}