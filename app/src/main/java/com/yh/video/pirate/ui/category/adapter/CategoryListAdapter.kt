package com.yh.video.pirate.ui.category.adapter

import android.view.ViewGroup
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.repository.network.result.CategoryList
import com.yh.video.pirate.ui.category.viewholder.ItemCategoryListVH
import com.yh.video.pirate.base.BaseViewHolder

class CategoryListAdapter : BaseAdapter<CategoryList>(CategoryList.diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CategoryList, *> {
        return ItemCategoryListVH(parent)
    }
}