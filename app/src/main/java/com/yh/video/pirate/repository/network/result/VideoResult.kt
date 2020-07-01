package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class VideoResult(
    var coverpath: String?,
    var id: Int?,
    var is_ad: Int?,
    var pageviews: Int?,
    var title: String?
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<VideoResult>() {
            override fun areItemsTheSame(
                oldItem: VideoResult,
                newItem: VideoResult
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: VideoResult,
                newItem: VideoResult
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}