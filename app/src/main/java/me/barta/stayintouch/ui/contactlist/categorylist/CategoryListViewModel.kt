package me.barta.stayintouch.ui.contactlist.categorylist

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
import me.barta.stayintouch.data.models.ContactPerson
import me.barta.stayintouch.repository.ContactPersonRepository
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(private val contactRepository: ContactPersonRepository) : ViewModel() {

    private val _viewState: MutableLiveData<SimpleScreenViewState<List<ContactPerson>>> = MutableLiveData()
    val viewState: LiveData<SimpleScreenViewState<List<ContactPerson>>> = _viewState

    fun loadContactsByCategory(categoryId: Int) {
        viewModelScope.launch {
            _viewState.value = Loading

            runCatching {
                contactRepository.loadContactsForCategory(categoryId)
            }.fold(
                    onSuccess = { contacts -> _viewState.value = Success(contacts) },
                    onFailure = { error -> _viewState.value = Failure(error) }
            )
        }
    }
}