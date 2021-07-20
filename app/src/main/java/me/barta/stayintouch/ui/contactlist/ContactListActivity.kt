package me.barta.stayintouch.ui.contactlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
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

@AndroidEntryPoint
class ContactListActivity : AppCompatActivity(R.layout.activity_contact_list) {

    private val viewModel: ContactListViewModel by viewModels()
    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpToolbar()

        viewModel.viewState.observe(this) { state ->
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

        viewPager.adapter = SectionsPagerAdapter(this, categories) { pos -> CategoryListFragment.newInstance(pos) }
        viewPager.offscreenPageLimit = categories.size

        tabLayoutMediator = TabLayoutMediator(tabs, viewPager, true) { tab, position -> tab.text = categories[position].name }
        tabLayoutMediator?.attach()
    }

    private fun handleError(error: Throwable) {
        Snackbar.make(rootLayout, R.string.error_loading_categories, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) { viewModel.loadCategories() }
                .show()
    }
}