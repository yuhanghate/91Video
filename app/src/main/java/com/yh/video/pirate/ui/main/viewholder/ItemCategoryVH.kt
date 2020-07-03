package com.yh.video.pirate.ui.main.viewholder

import android.view.ViewGroup
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.ItemCategoryBinding
import com.yh.video.pirate.repository.network.result.VideoTypeResult
import com.yuhang.novel.pirate.base.BaseViewHolder

class ItemCategoryVH(parent:ViewGroup):BaseViewHolder<VideoTypeResult, ItemCategoryBinding>(parent, R.layout.item_category) {
    override fun bindData(data: VideoTypeResult, position: Int) {
        mBinding.nameTv.text = data.name
    }


}