package me.barta.stayintouch.ui.contactdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.barta.stayintouch.common.utils.viewstate.SimpleScreenViewState
import me.barta.stayintouch.common.utils.viewstate.Success
import me.barta.stayintouch.data.database.DatabaseHelper
import me.barta.stayintouch.data.models.ContactPerson
import javax.inject.Inject

/**
 * Contact detail ViewModel
 */
@HiltViewModel
class ContactDetailViewModel @Inject constructor(private val databaseHelper: DatabaseHelper) : ViewModel() {

    private val _viewState: MutableLiveData<SimpleScreenViewState<ContactPerson>> = MutableLiveData()
    val viewState: LiveData<SimpleScreenViewState<ContactPerson>> = _viewState

    fun loadContactById(contactId: Int) {
        // TODO: coroutine, error handling, ...
        val contact = databaseHelper.loadContactById(contactId)
        _viewState.value = Success(contact)
    }
}