package me.barta.stayintouch.contactlist.categorylist


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_category_list.*
import me.barta.stayintouch.R
import me.barta.stayintouch.StayInTouchApplication
import me.barta.stayintouch.common.ui.MVPFragment
import me.barta.stayintouch.datastore.models.ContactPerson


/**
 * A simple [Fragment] subclass.
 * Use the [CategoryListFragment.newInstance] factory method to
 * create an instance of this fragment.
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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()

        val categoryId = arguments?.getInt(CATEGORY_ID) ?: -1
        presenter.loadContactsByCategory(categoryId)
    }

    private fun setUpViews() {
        list.layoutManager = LinearLayoutManager(context)
    }

    override fun presentLoadedData(data: List<ContactPerson>) {
        val adapter = CategoryListAdapter(data, { contact -> Log.d("CLICK", "Clicked on $contact") })
        list.adapter = adapter
    }

}
