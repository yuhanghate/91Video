package com.yh.video.pirate.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 分类: 國產自拍/高清無碼
 */
@Entity
class VideoSortEntity {

    @PrimaryKey(autoGenerate = true)
    var id:Int = 0

    /**
     * 分类id
     */
    var sortId:Int = 0

    /**
     * 类别名称
     */
    var name:String = ""

    /**
     * 优先级
     */
    var order:Int = 0

    /**
     * 封面
     */
    var icopath:String = ""
}