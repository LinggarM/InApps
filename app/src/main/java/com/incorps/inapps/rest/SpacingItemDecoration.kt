package com.incorps.inapps.rest

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpacingItemDecoration(private var dimensionPixelSize: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.top = dimensionPixelSize
        outRect.bottom = dimensionPixelSize
        outRect.right = dimensionPixelSize
        outRect.left = dimensionPixelSize
    }
}
