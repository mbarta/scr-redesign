package me.barta.stayintouch.ui.contactlist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import me.barta.stayintouch.R
import me.barta.stayintouch.common.utils.setNotImplementedClickListener
import me.barta.stayintouch.common.viewbinding.viewBinding
import me.barta.stayintouch.common.viewstate.Failure
import me.barta.stayintouch.common.viewstate.Loading
import me.barta.stayintouch.common.viewstate.Success
import me.barta.stayintouch.data.models.ContactCategory
import me.barta.stayintouch.databinding.ActivityContactListBinding
import me.barta.stayintouch.ui.contactlist.categorylist.CategoryListFragment

@AndroidEntryPoint
class ContactListActivity : AppCompatActivity() {

    private val viewModel: ContactListViewModel by viewModels()
    private val binding: ActivityContactListBinding by viewBinding(ActivityContactListBinding::inflate)

    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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
        setSupportActionBar(binding.toolbar)
        actionBar?.title = ""

        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                //Initialize the size of the scroll
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }

                binding.toolbarArcBackground.scale = 1 + verticalOffset / scrollRange.toFloat()
            }
        })

        with(binding.toolbarContent) {
            navigationMenu.setNotImplementedClickListener()
            actionSearch.setNotImplementedClickListener()
        }
    }

    private fun showLoading() {
        binding.toolbarContent.categoryLoadingProgress.show()
    }

    private fun hideLoading() {
        binding.toolbarContent.categoryLoadingProgress.hide()
    }

    private fun handleSuccess(categories: List<ContactCategory>) {
        tabLayoutMediator?.detach()

        binding.viewPager.adapter = SectionsPagerAdapter(this, categories) { pos -> CategoryListFragment.newInstance(pos) }
        binding.viewPager.offscreenPageLimit = categories.size

        tabLayoutMediator = TabLayoutMediator(binding.tabs, binding.viewPager, true) { tab, position ->
            tab.text = categories[position].name
        }
        tabLayoutMediator?.attach()
    }

    private fun handleError(error: Throwable) {
        Snackbar.make(binding.rootLayout, R.string.error_loading_categories, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.retry) { viewModel.loadCategories() }
                .show()
    }
}