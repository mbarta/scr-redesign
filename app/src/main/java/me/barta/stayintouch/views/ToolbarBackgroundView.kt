package me.barta.stayintouch.views

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.media.ThumbnailUtils
import android.util.AttributeSet
import android.view.View
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import me.barta.stayintouch.R
import me.barta.stayintouch.common.utils.blur


/**
 * Custom toolbar background view
 */
class ToolbarBackgroundView : View {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        resolveAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        resolveAttributes(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        resolveAttributes(attrs)
    }

    private var scale: Float = 1.0f
    private var topMargin: Int = 0

    private val paint = Paint()
    private var upperLeft = PointF()
    private var upperRight = PointF()
    private var bottomRight = PointF()
    private var bottomLeft = PointF()
    private val path = Path()

    private var gradientColourTop = Color.TRANSPARENT
    private var gradientColourBottom = Color.TRANSPARENT
    private var gradient = LinearGradient(0f, 0f, 0f, 0f, gradientColourTop, gradientColourBottom, Shader.TileMode.MIRROR)

    private var url: String? = null
    private var loadedBitmap: Bitmap? = null
    private var bitmapShader: BitmapShader? = null

    init {
        paint.apply {
            isAntiAlias = true
            shader = gradient
            style = Paint.Style.FILL
        }
    }

    private fun resolveAttributes(attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.ToolbarBackgroundView, 0, 0)

        try {
            topMargin = a.getDimensionPixelSize(R.styleable.ToolbarBackgroundView_topMargin, 0)
            gradientColourTop = a.getColor(R.styleable.ToolbarBackgroundView_gradientColorStart, Color.TRANSPARENT)
            gradientColourBottom = a.getColor(R.styleable.ToolbarBackgroundView_gradientColorEnd, Color.TRANSPARENT)
        } finally {
            a.recycle()
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        gradient = LinearGradient(0f, 0f, width.toFloat(), height * 2f, gradientColourTop, gradientColourBottom, Shader.TileMode.MIRROR)
        paint.shader = gradient

        if (url != null) {
            processBitmapBackground(url)
        }
    }

    fun setScale(scale: Float) {
        this.scale = scale
        invalidate()
    }

    fun setBitmapByUrl(url: String) {
        this.url = url
        invalidate()
    }

    private fun processBitmapBackground(url: String?) {
        Picasso.with(context).load(url).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, arg1: LoadedFrom) {
                loadedBitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height).blur(context)

                bitmapShader = BitmapShader(loadedBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                paint.shader = bitmapShader

                invalidate()
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {}

            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}
        })
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        upperLeft.set(0f, 0f)
        upperRight.set(width.toFloat(), 0f)
        bottomRight.set(width.toFloat(), height.toFloat() - topMargin.toFloat() + ((1 - scale) * topMargin.toFloat()))
        bottomLeft.set(0f, height.toFloat())

        path.reset()
        path.moveTo(0f, 0f)
        path.lineTo(upperRight.x, upperRight.y)
        path.lineTo(bottomRight.x, bottomRight.y)
        path.lineTo(bottomLeft.x, bottomLeft.y)
        path.close()

        canvas.drawPath(path, paint)
    }
}
