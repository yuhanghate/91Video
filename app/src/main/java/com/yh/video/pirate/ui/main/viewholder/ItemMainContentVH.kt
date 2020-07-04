package com.yh.video.pirate.ui.main.viewholder

import android.view.ViewGroup
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.ItemMainContentBinding
import com.yh.video.pirate.repository.network.result.Main
import com.yh.video.pirate.ui.video.activity.VideoPlayActivity
import com.yh.video.pirate.utils.*
import com.yuhang.novel.pirate.base.BaseViewHolder

class ItemMainContentVH(parent: ViewGroup) :
    BaseViewHolder<Main, ItemMainContentBinding>(parent, R.layout.item_main_content) {

    override fun bindData(data: Main, position: Int) {
        mBinding.titleTv.text = data.title

//        val screenRealWidth = (mContext.getScreenRealWidth() - 15.dp * 3) / 2 - 3.dp
//        val layoutParams = mBinding.coverIv.layoutParams
//        layoutParams.width = screenRealWidth
//        mBinding.coverIv.layoutParams = layoutParams

        mBinding.coverIv.loadImage(mContext, data.coverpath)

        mBinding.root.setOnClickListener {
            data.id?.toLong()?.let { VideoPlayActivity.start(mContext, it) }
        }
        mBinding.coverIv.setOnClickListener {
            data.id?.toLong()?.let { VideoPlayActivity.start(mContext, it) }
        }
    }

}