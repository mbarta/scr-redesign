package me.barta.stayintouch.contactlist

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter

import me.barta.stayintouch.datastore.models.ContactCategory

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fm: FragmentManager, private val contactCategories: List<ContactCategory>,
                           private val FragmentProviderFor: (Int) -> Fragment) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment = FragmentProviderFor(position)

    override fun getCount(): Int = contactCategories.size

    override fun getPageTitle(position: Int): CharSequence = contactCategories[position].name
}
