package com.yh.video.pirate.utils

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * 双击置顶
 */
class TopSmoothScroller(context: Context) : LinearSmoothScroller(context) {
    //标记是否需要二次滑动
    private var shouldMove = false
    //需要滑动到的item位置
    private var mPosition = 0

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }

    override fun getHorizontalSnapPreference(): Int {
        return SNAP_TO_START
    }

    fun smoothMoveToPosition(recyclerView: RecyclerView, position: Int) { // 获取RecyclerView的第一个可见位置
        val firstItem: Int = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0))
        // 获取RecyclerView的最后一个可见位置
        val lastItem: Int =
            recyclerView.getChildLayoutPosition(recyclerView.getChildAt(recyclerView.childCount - 1))

        if (position == -1) {
            //从头加载到尾
            recyclerView.smoothScrollToPosition(0)
        }else if (lastItem > position) {
            //太长了,先移动到指定位置再置顶
            recyclerView.scrollToPosition(position)
            recyclerView.smoothScrollToPosition(0)
        } else {
            //置顶
            recyclerView.smoothScrollToPosition(0)
        }
    }
}