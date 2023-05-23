package com.example.basicworktest.denise.mendez.utils

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class CubeInDepthTransformer : ViewPager2.PageTransformer {
    override fun transformPage(page: View, position: Float) {
        page.cameraDistance = 20000F
        if (position < -1) {
            page.alpha = 0F
        } else if (position <= 0) {
            page.alpha = 1F
            page.setPivotX(page.getWidth().toFloat())
            page.setRotationY(90 * Math.abs(position))
        } else if (position <= 1) {
            page.setAlpha(1F)
            page.setPivotX(0F)
            page.setRotationY(-90 * Math.abs(position))
        } else {
            page.setAlpha(0F)
        }
        if (Math.abs(position) <= 0.5) {
            page.setScaleY(Math.max(.4f, 1 - Math.abs(position)))
        } else if (Math.abs(position) <= 1) {
            page.setScaleY(Math.max(.4f, 1 - Math.abs(position)))
        }
    }
}
