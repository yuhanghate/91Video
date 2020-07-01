package com.yh.video.pirate.ui.main.viewholder

import android.view.ViewGroup
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.ItemMainTitleBinding
import com.yh.video.pirate.repository.network.result.MainResult
import com.yuhang.novel.pirate.base.BaseViewHolder

class ItemMainTitleVH(parent:ViewGroup):BaseViewHolder<MainResult, ItemMainTitleBinding>(parent, R.layout.item_main_title) {
    override fun bindData(data: MainResult, position: Int) {
        mBinding.titleTv.text = data.name
    }

}