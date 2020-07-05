package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class Search(
    var auther_no: Any?,
    var authername: String?,
    var coverpath: String?,
    var id: Int?,
    var title: String?
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(
                oldItem: Search,
                newItem: Search
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Search,
                newItem: Search
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}