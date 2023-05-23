package com.example.basicworktest.denise.mendez.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class ScaleTransformation(private val pageOffset: Int) :
    ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val myOffset = position * -(4 * pageOffset)
        val normalizedPosition = abs(abs(position) - 1)
        val scaleFactor = normalizedPosition / 8 + 0.8f
        page.translationX = myOffset
        page.scaleX = scaleFactor
        page.scaleY = scaleFactor
    }
}