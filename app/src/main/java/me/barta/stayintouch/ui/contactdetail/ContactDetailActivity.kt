package me.barta.stayintouch.ui.contactdetail

import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LinearInterpolator
import androidx.core.app.SharedElementCallback
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.activity_contact_detail.*
import kotlinx.android.synthetic.main.contact_detail_content.*
import me.barta.stayintouch.R
import me.barta.stayintouch.StayInTouchApplication
import me.barta.stayintouch.common.utils.karmaColorList
import me.barta.stayintouch.common.utils.setColoredRating
import me.barta.stayintouch.common.utils.toLegacyDate
import me.barta.stayintouch.data.models.ContactPerson
import me.barta.stayintouch.ui.base.MVPActivity
import org.ocpsoft.prettytime.PrettyTime
import java.util.*


/**
 * Contact detail Activity
 */
class ContactDetailActivity : MVPActivity<ContactDetailContract.View, ContactDetailPresenter, ContactDetailComponent>(), ContactDetailContract.View {

    companion object {
        const val CONTACT_ID = "ContactIdExtra"
        const val SHARED_PICTURE_ID = "ContactPicture"
        const val SHARED_INFO_CARD_ID = "ContactInfoCard"
    }

    override fun createComponent(): ContactDetailComponent =
            DaggerContactDetailComponent.builder().applicationComponent(StayInTouchApplication.component).build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        setUpSharedElementListener()

        supportPostponeEnterTransition()

        setUpViews()
    }

    private fun setUpSharedElementListener() {
        this.setEnterSharedElementCallback(object : SharedElementCallback() {
            override fun onSharedElementStart(sharedElementNames: MutableList<String>, sharedElements: MutableList<View>, sharedElementSnapshots: MutableList<View>) {
                super.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots)
                sharedElements.find { it.id == R.id.infoCard }?.findViewById<View>(R.id.infoCardContents)?.alpha = 0f
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val contactId = intent?.extras?.getInt(CONTACT_ID) ?: -1
        photoCard.transitionName = SHARED_PICTURE_ID + contactId
        infoCard.transitionName = SHARED_INFO_CARD_ID + contactId

        presenter.loadContactById(contactId)
    }

    private fun setUpViews() {
        setUpToolbar()
    }

    private fun setUpToolbar() {
        val toolbarIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_back_24dp, theme)?.apply {
            setTint(ContextCompat.getColor(this@ContactDetailActivity, R.color.white_transparent))
            setTintMode(PorterDuff.Mode.SRC_IN)
        }

        toolbar.navigationIcon = toolbarIcon
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

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
        val request = ImageRequest.Builder(this)
                .data(contact.photo)
                .allowHardware(false)
                .target(
                        onSuccess = { result ->
                            toolbarArcBackground.bitmap = (result as BitmapDrawable).bitmap
                            supportStartPostponedEnterTransition()
                        },
                        onError = { supportStartPostponedEnterTransition() }
                )
                .build()

        imageLoader.enqueue(request)

        photo.load(contact.photo)

        val pt = PrettyTime(Locale.getDefault())

        name.text = getString(R.string.contact_name, contact.firstName, contact.lastName)
        lastContact.text = getString(R.string.last_contact, pt.format(contact.lastContact.toLegacyDate()))
        nextContact.text = getString(R.string.next_contact, pt.format(contact.nextContact.toLegacyDate()))

        ratingBar.setColoredRating(contact.karma)
        rating.text = getString(R.string.rating, contact.karma)
        rating.setTextColor(ContextCompat.getColor(this, karmaColorList[contact.karma - 1]))

        frequency.text = contact.contactFreq
    }

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()

        val slideAnim = AnimationUtils.loadAnimation(this, R.anim.slide_from_bottom)
        contactButton.startAnimation(slideAnim)

        val alphaAnim = infoCardContents.animate()
                .alpha(1.0f)
                .setDuration(500)
                .setInterpolator(LinearInterpolator())

        alphaAnim.start()
    }


}
