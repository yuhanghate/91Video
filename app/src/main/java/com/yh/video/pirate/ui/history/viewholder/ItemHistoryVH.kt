package com.yh.video.pirate.ui.history.viewholder

import android.view.ViewGroup
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.ItemHistoryBinding
import com.yh.video.pirate.repository.network.result.Video
import com.yh.video.pirate.ui.main.viewholder.intChange2Str
import com.yh.video.pirate.ui.video.activity.VideoPlayActivity
import com.yh.video.pirate.utils.loadImage
import com.yh.video.pirate.base.BaseViewHolder

class ItemHistoryVH(parent:ViewGroup): BaseViewHolder<Video, ItemHistoryBinding>(parent, R.layout.item_history) {
    override fun bindData(data: Video, position: Int) {
        mBinding.nameTv.text = data.title
        mBinding.coverIv.loadImage(mContext, data.coverpath)
        mBinding.playAmountTv.text = data.pageviews?.intChange2Str(34729)
        if ("0" != data.playtimes) {
            mBinding.playTimeTv.text = "${data.playtimes}"
        }
        mBinding.root.setOnClickListener { data.id?.let { VideoPlayActivity.start(mContext,it.toLong()) } }
        mBinding.coverIv.setOnClickListener { data.id?.let { VideoPlayActivity.start(mContext,it.toLong()) } }
    }

}