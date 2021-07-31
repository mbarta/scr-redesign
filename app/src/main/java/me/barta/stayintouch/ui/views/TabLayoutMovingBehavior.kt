package me.barta.stayintouch.ui.views

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import me.barta.stayintouch.R

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
