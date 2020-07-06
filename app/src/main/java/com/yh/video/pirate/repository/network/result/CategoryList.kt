package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class CategoryList(
    var auther_no: Any?,
    var authername: String?,
    var coverpath: String?,
    var id: Int?,
    var title: String?
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<CategoryList>() {
            override fun areItemsTheSame(
                oldItem: CategoryList,
                newItem: CategoryList
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CategoryList,
                newItem: CategoryList
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}