package me.barta.stayintouch.contactlist

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import kotlinx.android.synthetic.main.activity_contact_list.*
import me.barta.stayintouch.R
import me.barta.stayintouch.StayInTouchApplication
import me.barta.stayintouch.common.ui.MVPActivity



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
        viewPager.offscreenPageLimit = 2

        tabs.setupWithViewPager(viewPager)
    }


}