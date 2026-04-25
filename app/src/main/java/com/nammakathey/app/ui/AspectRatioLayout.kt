package com.nammakathey.app.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

/**
 * A FrameLayout that enforces a fixed aspect ratio (height = width * ratio).
 * Set the ratio via android:tag="ratio:X.XXX" in XML.
 * Default ratio is 1.479 which matches the Karnataka map image (879 x 1300 px).
 */
class AspectRatioLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    // Default: 1300 / 879 ≈ 1.479 (height-to-width ratio of the Karnataka map image)
    private var ratio = 1.479f

    override fun onFinishInflate() {
        super.onFinishInflate()
        // Allow overriding ratio via tag="ratio:X.XX"
        val tag = tag?.toString() ?: ""
        if (tag.startsWith("ratio:")) {
            tag.removePrefix("ratio:").toFloatOrNull()?.let { ratio = it }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = (width * ratio).toInt()
        val newHeightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        super.onMeasure(widthMeasureSpec, newHeightSpec)
    }
}
