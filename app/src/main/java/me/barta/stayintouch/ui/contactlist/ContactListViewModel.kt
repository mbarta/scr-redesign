package me.barta.stayintouch.ui.contactlist

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
import me.barta.stayintouch.data.models.ContactCategory
import me.barta.stayintouch.repository.ContactCategoryRepository
import javax.inject.Inject

@HiltViewModel
class ContactListViewModel @Inject constructor(private val categoryRepository: ContactCategoryRepository) : ViewModel() {

    private val _viewState: MutableLiveData<SimpleScreenViewState<List<ContactCategory>>> = MutableLiveData()
    val viewState: LiveData<SimpleScreenViewState<List<ContactCategory>>> = _viewState

    init { loadCategories() }

    fun loadCategories() {
        viewModelScope.launch {
            _viewState.value = Loading

            runCatching {
                categoryRepository.loadContactCategories()
            }.fold(
                    onSuccess = { categories -> _viewState.value = Success(categories) },
                    onFailure = { error -> _viewState.value = Failure(error) }
            )
        }
    }
}
