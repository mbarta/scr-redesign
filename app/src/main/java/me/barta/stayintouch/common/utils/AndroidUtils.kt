package me.barta.stayintouch.common.utils

import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.support.annotation.DimenRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

/**
 * Android utility functions
 */

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun ImageView.loadUrl(url: String) {
    Picasso.with(context).load(url).into(this)
}

fun Resources.getFontSize(@DimenRes dimensionResource: Int): Float {
    return this.getDimension(dimensionResource) / this.displayMetrics.density
}

fun TextView.animateTextSize(startSize: Float, endSize: Float, duration: Long) {
    val animator = ObjectAnimator.ofFloat(this, "textSize", startSize, endSize)
    animator.duration = duration
    animator.setEvaluator(FloatEvaluator())
    animator.interpolator = LinearInterpolator()

    this.post({ animator.start() })
}
