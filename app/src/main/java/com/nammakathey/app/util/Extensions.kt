package com.nammakathey.app.util

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.core.graphics.ColorUtils
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

fun String.toColorInt(): Int = try {
    Color.parseColor(this)
} catch (e: Exception) {
    Color.parseColor("#E8553E")
}

fun Int.lighten(factor: Float = 0.85f): Int = ColorUtils.blendARGB(this, Color.WHITE, factor)
fun Int.darken(factor: Float = 0.3f): Int = ColorUtils.blendARGB(this, Color.BLACK, factor)

fun View.show() { visibility = View.VISIBLE }
fun View.hide() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun View.animateFadeIn(durationMs: Long = 300) {
    alpha = 0f
    visibility = View.VISIBLE
    animate().alpha(1f).setDuration(durationMs).start()
}

fun View.animateBounce(context: Context) {
    val bounce = AnimationUtils.loadAnimation(context, android.R.anim.bounce_interpolator)
    startAnimation(bounce)
}

fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun Long.toFormattedDate(): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(this))
}

fun TextView.setTextColorFromHex(hex: String) {
    try { setTextColor(Color.parseColor(hex)) } catch (e: Exception) { }
}
