package com.yh.video.pirate.ui.video.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.ItemRecommendLikeBinding
import com.yh.video.pirate.databinding.LayoutVideoLabelBinding
import com.yh.video.pirate.repository.network.result.RecommendedLike
import com.yh.video.pirate.ui.main.viewholder.intChange2Str
import com.yh.video.pirate.ui.video.activity.VideoPlayActivity
import com.yh.video.pirate.utils.loadImage
import com.yuhang.novel.pirate.base.BaseViewHolder

class ItemRecommendLikeVH(parent: ViewGroup) :
    BaseViewHolder<RecommendedLike, ItemRecommendLikeBinding>(
        parent,
        R.layout.item_recommend_like
    ) {
    override fun bindData(data: RecommendedLike, position: Int) {
        mBinding.nameTv.text = data.title
        mBinding.coverIv.loadImage(mContext, data.coverpath)
        mBinding.playAmountTv.text = "${data.pageviews?.intChange2Str(8065)}次播放"

        data.labls?.forEach {
            val inflate =
                LayoutVideoLabelBinding.inflate(LayoutInflater.from(mContext), null, false)
            inflate.contentTv.text = it.name
            inflate.contentLl.setBackgroundResource(R.drawable.bg_label_round_grey)
            mBinding.labelLl.addView(inflate.root)
        }

        mBinding.root.setOnClickListener { data.id?.toLong()?.let { VideoPlayActivity.start(mContext, it) } }
        mBinding.coverIv.setOnClickListener { data.id?.toLong()?.let { VideoPlayActivity.start(mContext, it) } }


    }
}