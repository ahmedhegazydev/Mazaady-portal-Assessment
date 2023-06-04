package com.mazaady.portal.util

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mazaady.portal.R

/* This is a Kotlin class that adds spacing between items in a RecyclerView. */
class DataCategoriesItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    private val spacing: Int = context.resources.getDimensionPixelSize(R.dimen.item_spacing)

    /**
     * This function sets the bottom spacing for items in a RecyclerView.
     *
     * @param outRect `outRect` is a `Rect` object that specifies the size of the item view's margins
     * and padding. It is used to set the spacing between items in a `RecyclerView`. The
     * `getItemOffsets()` method is called by the `RecyclerView` to retrieve the spacing between items.
     * The values set
     * @param view The current child view being drawn by the RecyclerView.
     * @param parent The parent RecyclerView in which the item views are contained.
     * @param state The `state` parameter in the `getItemOffsets` method of a
     * `RecyclerView.ItemDecoration` is an object that holds information about the current state of the
     * `RecyclerView`. It contains information such as the total number of items in the adapter, the
     * current scroll position, and any changes to the adapter data
     */
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        val itemCount = state.itemCount
        if (position < itemCount - 1) {
            outRect.bottom = spacing
        }
    }
}
