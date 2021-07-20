package me.barta.stayintouch.contactlist

import android.os.Bundle
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import android.widget.TextView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_contact_list.*
import me.barta.stayintouch.R
import me.barta.stayintouch.StayInTouchApplication
import me.barta.stayintouch.common.ui.MVPActivity
import me.barta.stayintouch.common.utils.animateTextSize
import me.barta.stayintouch.common.utils.getFontSize


/**
 * Contact list Activity
 */
class ContactListActivity : MVPActivity<ContactListContract.View, ContactListPresenter, ContactListComponent>(), ContactListContract.View {

    private var tabLayoutMediator: TabLayoutMediator? = null

    override fun createComponent(): ContactListComponent =
            DaggerContactListComponent.builder().applicationComponent(StayInTouchApplication.component).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        setUpViews()
    }

    private fun setUpViews() {
        setUpToolbar()
        setUpViewPager()
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

                val scale = 1 + verticalOffset / scrollRange.toFloat()

                toolbarArcBackground.setScale(scale)
            }
        })
    }

    private fun setUpViewPager() {
        tabLayoutMediator?.detach()

        val categories = presenter.loadCategories()
        val mSectionsPagerAdapter = SectionsPagerAdapter(this, categories) { presenter.getFragmentForPosition(it) }

        viewPager.adapter = mSectionsPagerAdapter
        viewPager.offscreenPageLimit = 5

        tabLayoutMediator = TabLayoutMediator(tabs, viewPager, true) { tab, position -> tab.text = categories[position].name }
        tabLayoutMediator?.attach()
    }
}