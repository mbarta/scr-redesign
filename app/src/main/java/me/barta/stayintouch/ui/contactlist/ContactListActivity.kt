package me.barta.stayintouch.ui.contactlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.toolbar_content.*
import me.barta.stayintouch.R
import me.barta.stayintouch.common.viewstate.Failure
import me.barta.stayintouch.common.viewstate.Loading
import me.barta.stayintouch.common.viewstate.Success
import me.barta.stayintouch.data.models.ContactCategory
import me.barta.stayintouch.ui.contactlist.categorylist.CategoryListFragment


/**
 * Contact list Activity
 */
@AndroidEntryPoint
class ContactListActivity : AppCompatActivity(R.layout.activity_contact_list) {

    private val viewModel: ContactListViewModel by viewModels()
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()

        viewModel.viewState.observe(this) { state ->
            when (state) {
                Loading -> showLoading()
                is Success -> {
                    hideLoading()
                    handleSuccess(state.data)
                }
                is Failure -> hideLoading()
            }
        }
    }

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        actionBar?.title = ""

        appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                toolbarArcBackground.scale = 1 + verticalOffset / scrollRange.toFloat()
            }
        })
    }

    private fun showLoading() {
        categoryLoadingProgress.show()
    }

    private fun hideLoading() {
        categoryLoadingProgress.hide()
    }

    private fun handleSuccess(categories: List<ContactCategory>) {
        tabLayoutMediator?.detach()

        val mSectionsPagerAdapter = SectionsPagerAdapter(this, categories) { getFragmentForPosition(it) }

        viewPager.adapter = mSectionsPagerAdapter
        viewPager.offscreenPageLimit = 5

        tabLayoutMediator = TabLayoutMediator(tabs, viewPager, true) { tab, position -> tab.text = categories[position].name }
        tabLayoutMediator?.attach()
    }

    private fun getFragmentForPosition(position: Int): Fragment = CategoryListFragment.newInstance(position)
}