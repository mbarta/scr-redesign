package me.barta.stayintouch.ui.contactlist.categorylist

import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_category_list.*
import me.barta.stayintouch.R
import me.barta.stayintouch.common.viewstate.Failure
import me.barta.stayintouch.common.viewstate.Loading
import me.barta.stayintouch.common.viewstate.Success
import me.barta.stayintouch.ui.contactdetail.ContactDetailActivity
import me.barta.stayintouch.data.models.ContactPerson


/**
 * Fragment containing a list of contacts for a category
 */
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
                Loading -> {}       // TODO
                is Success -> handleSuccess(state.data)
                is Failure -> {}    //TODO()
            }
        }

        viewModel.loadContactsByCategory(categoryId)
    }

    private fun setUpViews() {
        adapter = CategoryListAdapter { contact, photoView, infoView -> startDetailActivityForContact(contact, photoView, infoView) }

        list.layoutManager = LinearLayoutManager(context)
        list.adapter = adapter
    }

    private fun handleSuccess(data: List<ContactPerson>) {
        adapter?.submitList(data)
    }

    private fun startDetailActivityForContact(contact: ContactPerson, photoView: View, infoView: View) {
        val intent = Intent(context, ContactDetailActivity::class.java)
        intent.putExtra(ContactDetailActivity.CONTACT_ID, contact.id)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),
                Pair.create(photoView, photoView.transitionName),
                Pair.create(infoView, infoView.transitionName))
        startActivity(intent, options.toBundle())
    }

    companion object {
        const val CATEGORY_ID = "CategoryIdExtra"

        fun newInstance(categoryId: Int): CategoryListFragment {
            val args = Bundle()
            args.putInt(CATEGORY_ID, categoryId)
            val fragment = CategoryListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
