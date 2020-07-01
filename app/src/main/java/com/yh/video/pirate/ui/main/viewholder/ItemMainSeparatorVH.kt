package com.yh.video.pirate.ui.main.viewholder

import android.view.ViewGroup
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.ItemMainSeparatorBinding
import com.yh.video.pirate.repository.network.result.MainResult
import com.yuhang.novel.pirate.base.BaseViewHolder

class ItemMainSeparatorVH(parent:ViewGroup):BaseViewHolder<MainResult, ItemMainSeparatorBinding>(parent,R.layout.item_main_separator) {
    override fun bindData(data: MainResult, position: Int) {

    }
}