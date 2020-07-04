package com.yh.video.pirate.repository.network.result

import androidx.recyclerview.widget.DiffUtil

/**
 * 主页数据列表
 * 两层数据放一起了
 */
data class Main(
    var id:Int?,
    var title:String?,
    var coverpath:String?,
    var is_ad: Int?,
    var list: List<Main>?,
    var name: String?,
    var type: Int?,
    var type_id: Int?
){
    companion object{
        val diffCallback = object : DiffUtil.ItemCallback<Main>() {
            override fun areItemsTheSame(
                oldItem: Main,
                newItem: Main
            ): Boolean {
                return oldItem.id == newItem.id && oldItem.type_id == newItem.type_id
            }

            override fun areContentsTheSame(
                oldItem: Main,
                newItem: Main
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}
