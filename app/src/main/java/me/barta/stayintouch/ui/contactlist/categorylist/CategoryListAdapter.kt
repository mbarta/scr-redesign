package me.barta.stayintouch.ui.contactlist.categorylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.barta.stayintouch.R
import me.barta.stayintouch.common.utils.setColoredRating
import me.barta.stayintouch.common.utils.setNotImplementedClickListener
import me.barta.stayintouch.common.utils.toLegacyDate
import me.barta.stayintouch.data.models.ContactPerson
import me.barta.stayintouch.databinding.ContactListItemBinding
import me.barta.stayintouch.ui.contactdetail.ContactDetailActivity
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class CategoryListAdapter(private val listener: (ContactPerson, View, View) -> Unit)
    : ListAdapter<ContactPerson, CategoryListAdapter.ContactViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding = ContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ContactViewHolder(val binding: ContactListItemBinding, val listener: (ContactPerson, View, View) -> Unit) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ContactPerson) = with(binding) {
            ViewCompat.setTransitionName(photoCard, ContactDetailActivity.SHARED_PICTURE_ID + item.id)
            ViewCompat.setTransitionName(infoCard, ContactDetailActivity.SHARED_INFO_CARD_ID + item.id)

            photo.load(item.photo) { allowHardware(false) }
            root.setOnClickListener { listener(item, photoCard, infoCard) }

            val pt = PrettyTime(Locale.getDefault())

            val resources = itemView.resources

            name.text = resources.getString(R.string.contact_name, item.firstName, item.lastName)
            lastContact.text = resources.getString(R.string.last_contact, pt.format(item.lastContact.toLegacyDate()))
            nextContact.text = resources.getString(R.string.next_contact, pt.format(item.nextContact.toLegacyDate()))

            ratingBar.setColoredRating(item.karma)

            contactButton.setNotImplementedClickListener()
            moreButton.setNotImplementedClickListener()
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