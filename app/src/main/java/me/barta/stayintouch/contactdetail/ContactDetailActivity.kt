package me.barta.stayintouch.contactdetail

import android.graphics.PorterDuff
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import android.view.animation.AnimationUtils
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_contact_detail.*
import kotlinx.android.synthetic.main.contact_detail_content.*
import me.barta.stayintouch.R
import me.barta.stayintouch.StayInTouchApplication
import me.barta.stayintouch.common.ui.MVPActivity
import me.barta.stayintouch.datastore.models.ContactPerson



/**
 * Contact detail Activity
 */
class ContactDetailActivity : MVPActivity<ContactDetailContract.View, ContactDetailPresenter, ContactDetailComponent>(), ContactDetailContract.View {

    companion object {
        const val CONTACT_ID = "ContactIdExtra"
        const val SHARED_PICTURE_ID = "ContactPicture"
    }

    override fun createComponent(): ContactDetailComponent =
            DaggerContactDetailComponent.builder().applicationComponent(StayInTouchApplication.component).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        supportPostponeEnterTransition()

        setUpViews()
    }

    override fun onResume() {
        super.onResume()
        val contactId = intent?.extras?.getInt(CONTACT_ID) ?: -1
        photoCard.transitionName = SHARED_PICTURE_ID + contactId

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                supportFinishAfterTransition()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun displayContact(contact: ContactPerson) {

        Picasso.with(this)
                .load(contact.photo)
                .noFade()
                .into(photo, object : Callback {
                    override fun onSuccess() {
                        supportStartPostponedEnterTransition()
                    }

                    override fun onError() {
                        supportStartPostponedEnterTransition()
                    }
                })

        toolbarArcBackground.setBitmapByUrl(contact.photo)

        name.text = "${contact.firstName} ${contact.lastName}"
        lastContact.text = "Next contact: ${contact.nextContact}"

        frequency.text = contact.contactFreq


    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()

//        val originalPos = contactButton.y
//        contactButton.y = resources.displayMetrics.heightPixels

        val slideAnim = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom)
        contactButton.startAnimation(slideAnim)
//
//        val contactButtonAnimation = ObjectAnimator.ofFloat(contactButton, "y", resources.displayMetrics.heightPixels.toFloat(), 0f)
//        with(contactButtonAnimation) {
//            duration = 1000
//            setEvaluator(FloatEvaluator())
//            interpolator = AccelerateDecelerateInterpolator()
//            start()
//        }
    }
}
