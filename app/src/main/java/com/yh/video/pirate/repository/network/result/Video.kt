package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class Video(
    var coverpath: String?,
    var id: Int?,
    var is_ad: Int?,
    var pageviews: Int?,
    var title: String?
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(
                oldItem: Video,
                newItem: Video
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Video,
                newItem: Video
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}