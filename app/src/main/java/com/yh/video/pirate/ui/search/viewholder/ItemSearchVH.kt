package com.yh.video.pirate.ui.search.viewholder

import android.view.ViewGroup
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.ItemSearchBinding
import com.yh.video.pirate.listenter.OnClickItemListener
import com.yh.video.pirate.repository.network.result.Search
import com.yh.video.pirate.utils.loadImage
import com.yh.video.pirate.base.BaseViewHolder

class ItemSearchVH(parent:ViewGroup): BaseViewHolder<Search, ItemSearchBinding>(parent, R.layout.item_search) {
    override fun bindData(data: Search, position: Int) {
        mBinding.coverIv.loadImage(mContext, data.coverpath)
        mBinding.titleTv.text = data.title

        mBinding.root.setOnClickListener {
            getListener<OnClickItemListener>()?.onClickItemListener(it, position)
        }
        mBinding.coverIv.setOnClickListener{
            getListener<OnClickItemListener>()?.onClickItemListener(it, position)
        }
    }
}