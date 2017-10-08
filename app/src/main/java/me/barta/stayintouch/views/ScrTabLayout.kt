package me.barta.stayintouch.views

import android.content.Context
import android.support.design.widget.TabLayout
import android.util.AttributeSet
import android.view.View
import android.widget.TextView

import me.barta.stayintouch.R

/**
 * TabLayout implementation with custom tab layout
 */

class ScrTabLayout : TabLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun addTab(tab: TabLayout.Tab, setSelected: Boolean) {
        val tabViewText = View.inflate(context, R.layout.scr_tab_textview, null) as TextView
        tabViewText.text = tab.text

        tab.customView = tabViewText

        super.addTab(tab, setSelected)
    }

}
