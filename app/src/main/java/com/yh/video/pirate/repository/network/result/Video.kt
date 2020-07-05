package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

//视频播放信息
data class Video(
    var artist: Int?,
    var auther: String?,
    var auther_no: String?,
    var authername: String?,
    var coverpath: String?,
    var created_at: String?,
    var deleted_at: Any?,
    var ext: String?,
    var id: Int?,
    var introduction: String?,
    var is_like: Int?,
    var is_vip: Int?,
    var labls: List<Label>?,
    var pageviews: Int?,
    var playtimes: String?,
    var recommend: Int?,
    var sort_id: Int?,
    var status: Int?,
    var sync_status: Int?,
    var title: String?,
    var ts_time: Int?,
    var updated_at: String?,
    var videopath: String?
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

//标签
data class Label(
    var cover: String?,
    var created_at: String?,
    var deleted_at: String?,
    var description: String?,
    var id: Int?,
    var name: String?,
    var order: String?,
    var pivot: Pivot?,
    var updated_at: String?
)

data class Pivot(
    var created_at: String?,
    var topic_id: Int?,
    var updated_at: String?,
    var video_id: Int?
)