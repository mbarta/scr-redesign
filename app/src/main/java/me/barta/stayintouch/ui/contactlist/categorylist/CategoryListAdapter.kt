package me.barta.stayintouch.ui.contactlist.categorylist

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.contact_list_item.view.*
import me.barta.stayintouch.R
import me.barta.stayintouch.common.utils.inflate
import me.barta.stayintouch.common.utils.setColoredRating
import me.barta.stayintouch.common.utils.toLegacyDate
import me.barta.stayintouch.data.models.ContactPerson
import me.barta.stayintouch.ui.contactdetail.ContactDetailActivity
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class CategoryListAdapter(private val listener: (ContactPerson, View, View) -> Unit)
    : ListAdapter<ContactPerson, CategoryListAdapter.ContactViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(parent.inflate(R.layout.contact_list_item), listener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContactViewHolder(itemView: View, val listener: (ContactPerson, View, View) -> Unit) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ContactPerson) = with(itemView) {
            ViewCompat.setTransitionName(itemView.photoCard, ContactDetailActivity.SHARED_PICTURE_ID + item.id)
            ViewCompat.setTransitionName(itemView.infoCard, ContactDetailActivity.SHARED_INFO_CARD_ID + item.id)

            photo.load(item.photo) { allowHardware(false) }
            setOnClickListener { listener(item, itemView.photoCard, itemView.infoCard) }

            val pt = PrettyTime(Locale.getDefault())

            name.text = resources.getString(R.string.contact_name, item.firstName, item.lastName)
            lastContact.text = resources.getString(R.string.last_contact, pt.format(item.lastContact.toLegacyDate()))
            nextContact.text = resources.getString(R.string.next_contact, pt.format(item.nextContact.toLegacyDate()))

            ratingBar.setColoredRating(item.karma)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContactPerson>() {
            override fun areItemsTheSame(oldItem: ContactPerson, newItem: ContactPerson): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ContactPerson, newItem: ContactPerson): Boolean {
                return oldItem == newItem
            }
        }
    }
}