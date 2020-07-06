package com.yh.video.pirate.ui.category.viewholder

import android.view.ViewGroup
import com.yh.video.pirate.R
import com.yh.video.pirate.databinding.ItemCategoryListBinding
import com.yh.video.pirate.repository.network.result.CategoryList
import com.yh.video.pirate.ui.video.activity.VideoPlayActivity
import com.yh.video.pirate.utils.loadImage
import com.yuhang.novel.pirate.base.BaseViewHolder

class ItemCategoryListVH(parent:ViewGroup):BaseViewHolder<CategoryList, ItemCategoryListBinding>(parent, R.layout.item_category_list) {

    override fun bindData(data: CategoryList, position: Int) {

        mBinding.coverIv.loadImage(mContext,data.coverpath)
        mBinding.titleTv.text = data.title

        mBinding.root.setOnClickListener { data.id?.let { VideoPlayActivity.start(mContext,it.toLong()) } }
        mBinding.coverIv.setOnClickListener { data.id?.let { VideoPlayActivity.start(mContext,it.toLong()) } }
    }
}