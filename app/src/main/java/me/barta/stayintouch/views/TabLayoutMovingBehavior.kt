package me.barta.stayintouch.views

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import me.barta.stayintouch.R

/**
 * Behaviour for TabLayout - to be positioned below Toolbar
 */

class TabLayoutMovingBehavior : CoordinatorLayout.Behavior<TabLayout> {

    private var topMargin: Int = 0

    constructor()

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val tv = TypedValue()
        if (context.theme.resolveAttribute(R.attr.actionBarSize, tv, true)) {
            topMargin = TypedValue.complexToDimensionPixelSize(tv.data, context.resources.displayMetrics)
        }
    }

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: TabLayout?, dependency: View?): Boolean {
        return dependency is ViewPager
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: TabLayout, dependency: View): Boolean {
        child.y = dependency.y - topMargin
        return true
    }
}
