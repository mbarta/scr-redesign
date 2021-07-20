package me.barta.stayintouch.ui.contactlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_contact_list.*
import me.barta.stayintouch.R
import me.barta.stayintouch.StayInTouchApplication
import me.barta.stayintouch.common.utils.viewstate.Failure
import me.barta.stayintouch.common.utils.viewstate.Loading
import me.barta.stayintouch.common.utils.viewstate.Success
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
                Loading -> {}       // TODO
                is Success -> handleSuccess(state.data)
                is Failure -> {}    //TODO()
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