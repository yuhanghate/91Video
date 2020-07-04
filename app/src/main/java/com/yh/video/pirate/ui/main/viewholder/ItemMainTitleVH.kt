package com.yh.video.pirate.ui.main.viewholder

import android.view.ViewGroup
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.ItemMainTitleBinding
import com.yh.video.pirate.repository.network.result.Main
import com.yuhang.novel.pirate.base.BaseViewHolder

class ItemMainTitleVH(parent:ViewGroup):BaseViewHolder<Main, ItemMainTitleBinding>(parent, R.layout.item_main_title) {
    override fun bindData(data: Main, position: Int) {
        mBinding.titleTv.text = data.name
    }

}