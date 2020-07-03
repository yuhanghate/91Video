package com.yh.video.pirate.ui.main.adapter

import android.view.ViewGroup
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.repository.network.result.DiscoverResult
import com.yh.video.pirate.repository.network.result.VideoResult
import com.yh.video.pirate.repository.network.result.VideoTypeResult
import com.yh.video.pirate.ui.main.viewholder.ItemCategoryVH
import com.yh.video.pirate.ui.main.viewholder.ItemDiscoverVH
import com.yuhang.novel.pirate.base.BaseViewHolder

/**
 * 分类
 */
class CategoryAdapter:BaseAdapter<VideoTypeResult>(VideoTypeResult.diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<VideoTypeResult, *> {
        return ItemCategoryVH(parent)
    }
}