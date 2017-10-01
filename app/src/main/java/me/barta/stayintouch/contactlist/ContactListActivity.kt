package me.barta.stayintouch.contactlist

import android.os.Bundle
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
        setSupportActionBar(toolbar)
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager,
                presenter.loadCategories()) { presenter.getFragmentForPosition(it) }

        viewPager.adapter = mSectionsPagerAdapter
        viewPager.offscreenPageLimit = 1
    }
}