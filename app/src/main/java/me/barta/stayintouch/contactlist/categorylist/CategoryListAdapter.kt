package me.barta.stayintouch.contactlist.categorylist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import me.barta.stayintouch.R
import me.barta.stayintouch.common.utils.inflate
import me.barta.stayintouch.datastore.models.ContactPerson

/**
 * Adapter for contact list
 */
class CategoryListAdapter(private val contactList: List<ContactPerson>, private val listener: (ContactPerson) -> Unit)
    : RecyclerView.Adapter<CategoryListAdapter.ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(parent.inflate(R.layout.contact_list_item))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contactList[position], listener)
    }

    override fun getItemCount(): Int = contactList.size


    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ContactPerson, listener: (ContactPerson) -> Unit) = with(itemView) {
//            itemTitle.text = item.title
//            itemImage.loadUrl(item.url)
            setOnClickListener { listener(item) }
        }
    }
}