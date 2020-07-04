package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class Discover(
    var coverpath: String?,
    var id: Int?,
    var is_ad: Int?,
    var pageviews: Int?,
    var title: String?
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Discover>() {
            override fun areItemsTheSame(
                oldItem: Discover,
                newItem: Discover
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Discover,
                newItem: Discover
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
