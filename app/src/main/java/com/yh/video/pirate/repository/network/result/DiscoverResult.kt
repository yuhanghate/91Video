package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class DiscoverResult(
    var coverpath: String?,
    var id: Int?,
    var is_ad: Int?,
    var pageviews: Int?,
    var title: String?
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<DiscoverResult>() {
            override fun areItemsTheSame(
                oldItem: DiscoverResult,
                newItem: DiscoverResult
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: DiscoverResult,
                newItem: DiscoverResult
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
