package me.barta.stayintouch.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.widget.ImageView
import com.squareup.picasso.Picasso


/**
 * Image manipulation utils
 */

fun ImageView.loadUrl(url: String) = Picasso.with(context).load(url).into(this)

fun Bitmap.blur(ctx: Context, blurRadius: Float = 21f): Bitmap {
    val rs = RenderScript.create(ctx)

    val input = Allocation.createFromBitmap(rs, this)
    val output = Allocation.createTyped(rs, input.type)

    val script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
    script.setRadius(blurRadius)
    script.setInput(input)
    script.forEach(output)
    output.copyTo(this)

    return this
}