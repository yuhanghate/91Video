package com.yh.video.pirate.ui.video.adapter

import android.view.ViewGroup
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.repository.network.result.RecommendedLike
import com.yh.video.pirate.ui.video.viewholder.ItemRecommendLikeVH
import com.yuhang.novel.pirate.base.BaseViewHolder

/**
 * 猜你喜欢
 */
class RecommendLikeAdapter :BaseAdapter<RecommendedLike>(RecommendedLike.diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<RecommendedLike, *> {
        return ItemRecommendLikeVH(parent)
    }
}