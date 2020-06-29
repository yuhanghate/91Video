package com.yh.video.pirate.repository.network.result

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
)