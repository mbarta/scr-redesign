package me.barta.stayintouch.ui.contactlist.categorylist


import android.content.Intent
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_category_list.*
import me.barta.stayintouch.R
import me.barta.stayintouch.StayInTouchApplication
import me.barta.stayintouch.ui.base.MVPFragment
import me.barta.stayintouch.ui.contactdetail.ContactDetailActivity
import me.barta.stayintouch.data.models.ContactPerson


/**
 * Fragment containing a list of contacts for a category
 */
class CategoryListFragment : MVPFragment<CategoryListContract.View, CategoryListPresenter, CategoryListComponent>(), CategoryListContract.View {

    companion object {
        const val CATEGORY_ID = "CategoryIdExtra"

        @JvmStatic fun newInstance(categoryId: Int): CategoryListFragment {
            val args = Bundle()
            args.putInt(CATEGORY_ID, categoryId)
            val fragment = CategoryListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun createComponent(): CategoryListComponent =
            DaggerCategoryListComponent.builder().applicationComponent(StayInTouchApplication.component).build()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_category_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()

        val categoryId = arguments?.getInt(CATEGORY_ID) ?: -1
        presenter.loadContactsByCategory(categoryId)
    }

    private fun setUpViews() {
        val spanSize = resources.getInteger(R.integer.contact_list_span)
        val gridLayoutManager = GridLayoutManager(context, spanSize)

        list.layoutManager = gridLayoutManager
    }

    override fun presentLoadedData(data: List<ContactPerson>) {
        val adapter = CategoryListAdapter(data) { contact, photoView, infoView -> startDetailActivityForContact(contact, photoView, infoView) }
        list.adapter = adapter
    }

    private fun startDetailActivityForContact(contact: ContactPerson, photoView: View, infoView: View) {
        val intent = Intent(context, ContactDetailActivity::class.java)
        intent.putExtra(ContactDetailActivity.CONTACT_ID, contact.id)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),
                Pair.create(photoView, photoView.transitionName),
                Pair.create(infoView, infoView.transitionName))
        startActivity(intent, options.toBundle())
    }
}
