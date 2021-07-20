package me.barta.stayintouch.ui.contactlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.barta.stayintouch.common.utils.viewstate.SimpleScreenViewState
import me.barta.stayintouch.common.utils.viewstate.Success
import me.barta.stayintouch.data.database.DatabaseHelper
import me.barta.stayintouch.data.models.ContactCategory
import me.barta.stayintouch.data.models.ContactPerson
import javax.inject.Inject

/**
 * Contact list Presenter
 */
@HiltViewModel
class ContactListViewModel @Inject constructor(private val databaseHelper: DatabaseHelper) : ViewModel() {

    private val _viewState: MutableLiveData<SimpleScreenViewState<List<ContactCategory>>> = MutableLiveData()
    val viewState: LiveData<SimpleScreenViewState<List<ContactCategory>>> = _viewState

    init { loadCategories() }

    private fun loadCategories() {
        // TODO: coroutines, error handling, ...
        val categories = databaseHelper.loadCategories()
        _viewState.value = Success(categories)
    }
}
