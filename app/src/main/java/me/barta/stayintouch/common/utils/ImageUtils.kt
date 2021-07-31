package me.barta.stayintouch.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

fun Bitmap.blur(ctx: Context, blurRadius: Float = 21f): Bitmap {
    val rs = RenderScript.create(ctx)

    val input = Allocation.createFromBitmap(rs, this)
    val output = Allocation.createTyped(rs, input.type)

    ScriptIntrinsicBlur.create(rs, Element.U8_4(rs)).apply {
        setRadius(blurRadius)
        setInput(input)
        forEach(output)
    }

    output.copyTo(this)

    return this
}