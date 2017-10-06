package me.barta.stayintouch.contactlist

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.TabLayout
import android.widget.TextView
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

        appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var scrollRange = -1

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
        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,
                presenter.loadCategories()) { presenter.getFragmentForPosition(it) }

        viewPager.adapter = mSectionsPagerAdapter
        viewPager.offscreenPageLimit = 5

        tabs.addOnTabSelectedListener(
                object : TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        super.onTabSelected(tab)

                        val textView = tab.customView as TextView
                        textView.animateTextSize(resources.getFontSize(R.dimen.tab_text_size),
                                resources.getFontSize(R.dimen.tab_text_selected_size),
                                250)

                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {
                        super.onTabSelected(tab)

                        val textView = tab.customView as TextView
                        textView.animateTextSize(resources.getFontSize(R.dimen.tab_text_selected_size),
                                resources.getFontSize(R.dimen.tab_text_size),
                                250)
                    }
                })

        tabs.setupWithViewPager(viewPager)
    }
}