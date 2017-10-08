package me.barta.stayintouch.contactlist.categorylist

import android.support.v4.view.ViewCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.contact_list_item.view.*
import me.barta.stayintouch.R
import me.barta.stayintouch.common.utils.inflate
import me.barta.stayintouch.common.utils.loadUrl
import me.barta.stayintouch.common.utils.setColoredRating
import me.barta.stayintouch.common.utils.toLegacyDate
import me.barta.stayintouch.contactdetail.ContactDetailActivity
import me.barta.stayintouch.datastore.models.ContactPerson
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

/**
 * Adapter for contact list
 */
class CategoryListAdapter(private val contactList: List<ContactPerson>, private val listener: (ContactPerson, View) -> Unit)
    : RecyclerView.Adapter<CategoryListAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(parent.inflate(R.layout.contact_list_item))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position], listener)
    }

    override fun getItemCount(): Int = contactList.size


    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ContactPerson, listener: (ContactPerson, View) -> Unit) = with(itemView) {
            ViewCompat.setTransitionName(itemView.photoCard, ContactDetailActivity.SHARED_PICTURE_ID + item.id)

            photo.loadUrl(item.photo)
            setOnClickListener { listener(item, itemView.photoCard) }

            val pt = PrettyTime(Locale.getDefault())

            name.text = "${item.firstName} ${item.lastName}"
            lastContact.text = "Contacted: ${pt.format(item.lastContact.toLegacyDate())}"
            nextContact.text = "Next in: ${pt.format(item.nextContact.toLegacyDate())}"

            ratingBar.setColoredRating(item.karma)
        }
    }
}