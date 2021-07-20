package me.barta.stayintouch.ui.contactdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.barta.stayintouch.common.viewstate.Failure
import me.barta.stayintouch.common.viewstate.Loading
import me.barta.stayintouch.common.viewstate.SimpleScreenViewState
import me.barta.stayintouch.common.viewstate.Success
import me.barta.stayintouch.network.FakeApi
import me.barta.stayintouch.data.models.ContactPerson
import me.barta.stayintouch.repository.ContactPersonRepository
import javax.inject.Inject

/**
 * Contact detail ViewModel
 */
@HiltViewModel
class ContactDetailViewModel @Inject constructor(private val contactRepository: ContactPersonRepository) : ViewModel() {

    private val _viewState: MutableLiveData<SimpleScreenViewState<ContactPerson>> = MutableLiveData()
    val viewState: LiveData<SimpleScreenViewState<ContactPerson>> = _viewState

    fun loadContactById(contactId: Int) {
        viewModelScope.launch {
            _viewState.value = Loading

            runCatching {
                contactRepository.loadContactById(contactId)
            }.fold(
                    onSuccess = { contact -> _viewState.value = Success(contact) },
                    onFailure = { error -> _viewState.value = Failure(error) }
            )
        }
    }
}