package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

/**
 * 主页数据列表
 * 两层数据放一起了
 */
data class MainResult(
    var id:Int?,
    var title:String?,
    var coverpath:String?,
    var is_ad: Int?,
    var list: List<MainResult>?,
    var name: String?,
    var type: Int?,
    var type_id: Int?
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<MainResult>() {
            override fun areItemsTheSame(
                oldItem: MainResult,
                newItem: MainResult
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.type_id == newItem.type_id
            }

            override fun areContentsTheSame(
                oldItem: MainResult,
                newItem: MainResult
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
