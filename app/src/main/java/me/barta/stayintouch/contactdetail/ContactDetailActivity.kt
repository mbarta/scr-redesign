package me.barta.stayintouch.contactdetail

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.activity_contact_detail.*
import kotlinx.android.synthetic.main.contact_detail_content.*
import me.barta.stayintouch.R
import me.barta.stayintouch.StayInTouchApplication
import me.barta.stayintouch.common.ui.MVPActivity
import me.barta.stayintouch.common.utils.loadUrl
import me.barta.stayintouch.datastore.models.ContactPerson

/**
 * Contact detail Activity
 */
class ContactDetailActivity : MVPActivity<ContactDetailContract.View, ContactDetailPresenter, ContactDetailComponent>(), ContactDetailContract.View {

    companion object {
        const val CONTACT_ID = "ContactIdExtra"
    }

    override fun createComponent(): ContactDetailComponent =
            DaggerContactDetailComponent.builder().applicationComponent(StayInTouchApplication.component).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        setUpViews()
    }

    override fun onResume() {
        super.onResume()
        val contactId = intent?.extras?.getInt(CONTACT_ID) ?: -1
        presenter.loadContactById(contactId)
    }

    private fun setUpViews() {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        val toolbarIcon = resources.getDrawable(R.drawable.ic_arrow_back_24dp, theme)
        toolbarIcon.setTint(ContextCompat.getColor(this, R.color.white_transparent))
        toolbarIcon.setTintMode(PorterDuff.Mode.SRC_IN)

        toolbar.navigationIcon = toolbarIcon
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

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

    override fun displayContact(contact: ContactPerson) {
        photo.loadUrl(contact.photo)
        name.text = "${contact.firstName} ${contact.lastName}"
        nextContact.text = "Next contact: ${contact.nextContact}"

        frequency.text = contact.contactFreq
    }
}
