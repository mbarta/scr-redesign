package me.barta.stayintouch.ui.contactlist.categorylist

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.os.bundleOf
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category_list.*
import me.barta.stayintouch.R
import me.barta.stayintouch.common.viewstate.Failure
import me.barta.stayintouch.common.viewstate.Loading
import me.barta.stayintouch.common.viewstate.Success
import me.barta.stayintouch.data.models.ContactPerson
import me.barta.stayintouch.ui.contactdetail.ContactDetailActivity

@AndroidEntryPoint
class CategoryListFragment : Fragment(R.layout.fragment_category_list) {

    private val viewModel: CategoryListViewModel by viewModels()
    private var adapter: CategoryListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()

        val categoryId = arguments?.getInt(CATEGORY_ID) ?: -1

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Loading -> showLoading()
                is Success -> {
                    hideLoading()
                    handleSuccess(state.data)
                }
                is Failure -> {
                    hideLoading()
                    handleError(state.throwable)
                }
            }
        }

        viewModel.loadContactsByCategory(categoryId)
    }

    private fun setUpViews() {
        adapter = CategoryListAdapter { contact, photoView, infoView -> startDetailActivityForContact(contact, photoView, infoView) }
        list.adapter = adapter
    }

    private fun handleSuccess(data: List<ContactPerson>) {
        list.isVisible = true
        errorLayout.isVisible = false

        adapter?.submitList(data)
    }

    private fun handleError(error: Throwable) {
        list.isVisible = false
        errorLayout.isVisible = true
    }

    private fun showLoading() {
        contactsLoadingProgress.show()
    }

    private fun hideLoading() {
        contactsLoadingProgress.hide()
    }

    private fun startDetailActivityForContact(contact: ContactPerson, photoView: View, infoView: View) {
        val intent = Intent(context, ContactDetailActivity::class.java)
        intent.putExtra(ContactDetailActivity.CONTACT_ID, contact.id)

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                Pair.create(photoView, photoView.transitionName),
                Pair.create(infoView, infoView.transitionName)
        )

        startActivity(intent, options.toBundle())
    }

    companion object {
        const val CATEGORY_ID = "CategoryIdExtra"

        fun newInstance(categoryId: Int): CategoryListFragment {
            val args = bundleOf(CATEGORY_ID to categoryId)
            return CategoryListFragment().apply { arguments = args }
        }
    }
}
