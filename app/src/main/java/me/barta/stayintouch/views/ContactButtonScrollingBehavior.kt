package me.barta.stayintouch.views

import android.content.Context
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.View

internal class ContactButtonScrollingBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<FloatingActionButton>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout?, child: FloatingActionButton?, dependency: View?): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout?, child: FloatingActionButton, dependency: View): Boolean {
        if (dependency is AppBarLayout) {

            val distanceToScroll = child.height

            val ratio = dependency.y / distanceToScroll.toFloat()
            child.translationY = -distanceToScroll * ratio
        }
        return true
    }
}