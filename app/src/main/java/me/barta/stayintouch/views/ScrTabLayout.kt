package me.barta.stayintouch.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import me.barta.stayintouch.R
import me.barta.stayintouch.common.utils.animateTextSize
import me.barta.stayintouch.common.utils.getFontSize

/**
 * TabLayout implementation with custom tab layout
 */

class ScrTabLayout : TabLayout {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun addTab(tab: Tab, setSelected: Boolean) {
        val tabViewText = View.inflate(context, R.layout.scr_tab_textview, null) as TextView
        tabViewText.text = tab.text

        tab.customView = tabViewText

        super.addTab(tab, setSelected)
    }

    init {
        addOnTabSelectedListener(
                object : OnTabSelectedListener {
                    override fun onTabSelected(tab: Tab?) {
                        val textView = tab?.customView as? TextView
                        textView?.animateTextSize(resources.getFontSize(R.dimen.tab_text_size),
                                resources.getFontSize(R.dimen.tab_text_selected_size),
                                200)
                    }

                    override fun onTabUnselected(tab: Tab?) {
                        val textView = tab?.customView as? TextView
                        textView?.animateTextSize(resources.getFontSize(R.dimen.tab_text_selected_size),
                                resources.getFontSize(R.dimen.tab_text_size),
                                200)
                    }

                    override fun onTabReselected(tab: Tab?) {
                    }

                })
    }

}
