package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

data class VideoResult(
    var created_at: String?,
    var deleted_at: Any?,
    var id: Int?,
    var imgpath: String?,
    var is_ad: Int?,
    var name: String?,
    var place_id: Int?,
    var rate: Int?,
    var sort_id: Int?,
    var status: Int?,
    var updated_at: String?,
    var url: String?,
    var urlname: String?
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