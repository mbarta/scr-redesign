package me.barta.stayintouch.ui.views

import android.content.Context
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.tabs.TabLayout
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.viewpager2.widget.ViewPager2
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

    override fun layoutDependsOn(parent: CoordinatorLayout, child: TabLayout, dependency: View): Boolean {
        return dependency is ViewPager2
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: TabLayout, dependency: View): Boolean {
        child.y = dependency.y - topMargin
        return true
    }
}
