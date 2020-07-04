package com.yh.video.pirate.ui.main.adapter

import android.view.ViewGroup
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.repository.network.result.Main
import com.yh.video.pirate.ui.main.viewholder.ItemMainContentVH
import com.yh.video.pirate.ui.main.viewholder.ItemMainSeparatorVH
import com.yh.video.pirate.ui.main.viewholder.ItemMainTitleVH
import com.yuhang.novel.pirate.base.BaseViewHolder

/**
 * 首页
 */
class MainAdapter : BaseAdapter<Main>(Main.diffCallback) {

    companion object {
        const val TYPE_TITLE = 0 //标题
        const val TYPE_CONTENT = 1//视频内容
        const val TYPE_SEPARATOR = 3//分隔线
    }

    override fun getItemViewType(position: Int): Int {
        val obj = getItem(position) ?: return TYPE_SEPARATOR
        return when {
            obj.type_id != null -> TYPE_TITLE
            obj.id != null -> TYPE_CONTENT
            else -> TYPE_SEPARATOR
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): BaseViewHolder<Main, *> {
        return when (viewType) {
            TYPE_TITLE -> ItemMainTitleVH(parent)
            TYPE_CONTENT -> ItemMainContentVH(parent)
            else -> ItemMainSeparatorVH(parent)
        }
    }

}