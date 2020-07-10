package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

/**
 * 视频类型:國產自拍/家庭亂倫
 */
data class VideoType(
    var icopath: String? = null,//图片
    var id: Int?,//类型id
    var name: String?,//类型名称
    var order: Int? = null//优先级
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<VideoType>() {
            override fun areItemsTheSame(
                oldItem: VideoType,
                newItem: VideoType
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: VideoType,
                newItem: VideoType
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}