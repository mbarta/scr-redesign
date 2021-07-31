package me.barta.stayintouch.ui.contactdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.flextrade.kfixture.KFixture
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import me.barta.stayintouch.common.CoroutineTestRule
import me.barta.stayintouch.common.viewstate.Failure
import me.barta.stayintouch.common.viewstate.Loading
import me.barta.stayintouch.common.viewstate.SimpleScreenViewState
import me.barta.stayintouch.common.viewstate.Success
import me.barta.stayintouch.data.models.ContactPerson
import me.barta.stayintouch.repository.ContactPersonRepository
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.kotlin.*
import java.lang.Exception
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class ContactDetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutineTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val kFixture = KFixture()

    private val observer: Observer<SimpleScreenViewState<ContactPerson>> = mock()

    private val repository: ContactPersonRepository = mock()
    private val viewModel = ContactDetailViewModel(repository)

    @Test
    fun `Given contact exists, When load contact called, Then ViewState is Success`() {
        val contactPerson: ContactPerson = kFixture()

        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(repository.loadContactById(any())).thenReturn(contactPerson)

            viewModel.viewState.observeForever(observer)
            viewModel.loadContactById(contactId = contactPerson.id)
        }

        val argumentCaptor = argumentCaptor<SimpleScreenViewState<ContactPerson>>()
        verify(observer, times(2)).onChanged(argumentCaptor.capture())
        val values = argumentCaptor.allValues

        assertThat(values[0]).isEqualTo(Loading)
        assertThat(values[1]).isEqualTo(Success(contactPerson))
    }

    @Test
    fun `Given contact doesn't exist, When load contact called, Then ViewState is Failure`() {
        val exception: RuntimeException = kFixture()

        coroutinesTestRule.testDispatcher.runBlockingTest {
            whenever(repository.loadContactById(any())).thenThrow(exception)

            viewModel.viewState.observeForever(observer)
            viewModel.loadContactById(contactId = 0)
        }

        val argumentCaptor = argumentCaptor<SimpleScreenViewState<ContactPerson>>()
        verify(observer, times(2)).onChanged(argumentCaptor.capture())
        val values = argumentCaptor.allValues

        assertThat(values[0]).isEqualTo(Loading)
        assertThat(values[1]).isEqualTo(Failure(exception))
    }

}