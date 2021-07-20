package me.barta.stayintouch.common.utils

import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DimenRes
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import me.barta.stayintouch.R

/**
 * Android view utility functions
 */

val karmaColorList: Array<Int> = arrayOf(R.color.red300, R.color.red300, R.color.orange300, R.color.orange300, R.color.light_green300)

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun Resources.getFontSize(@DimenRes dimensionResource: Int): Float {
    return this.getDimension(dimensionResource) / this.displayMetrics.density
}

fun TextView.animateTextSize(startSize: Float, endSize: Float, animDuration: Long) {
    val animator = ObjectAnimator.ofFloat(this, "textSize", startSize, endSize)
    with(animator) {
        duration = animDuration
        setEvaluator(FloatEvaluator())
        interpolator = AccelerateDecelerateInterpolator()
    }

    this.post { animator.start() }
}

fun RatingBar.setColoredRating(@IntRange(from = 1, to = 5) ratingVal: Int, colorList: Array<Int> = arrayOf(
        R.color.red300, R.color.red300, R.color.orange300, R.color.orange300, R.color.light_green300)) {
    val ratingColorIdx = ratingVal - 1

    if (ratingColorIdx < colorList.size) {
        val ratingColor = ContextCompat.getColorStateList(context, colorList[ratingColorIdx])
        with(this) {
            rating = ratingVal.toFloat()
            progressTintList = ratingColor
            secondaryProgressTintList = ratingColor
        }
    }
}

fun View.setNotImplementedClickListener() {
    this.setOnClickListener {
        Toast.makeText(context, R.string.not_implemented, Toast.LENGTH_SHORT).show()
    }
}
