package me.barta.stayintouch.ui.contactlist

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

import me.barta.stayintouch.data.models.ContactCategory

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(activity: AppCompatActivity, private val contactCategories: List<ContactCategory>,
                           private val fragmentProviderFor: (Int) -> Fragment) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = contactCategories.size
    override fun createFragment(position: Int): Fragment = fragmentProviderFor(position)
}
