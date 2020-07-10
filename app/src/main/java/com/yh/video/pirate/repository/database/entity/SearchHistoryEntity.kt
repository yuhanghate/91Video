package com.yh.video.pirate.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yh.video.pirate.common.Identifiable

/**
 * 搜索过的历史记录
 */
@Entity
class SearchHistoryEntity: Identifiable<Int>{

    companion object{
        const val TYPE_HOT = "hot"//热门
        const val TYPE_RECOMMENT = "recomment"//推荐
        const val TYPE_HISTORY = "history"//历史
    }
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0

    /**
     * 关键字
     */
    var keyword: String = ""

    /**
     * 类型:
     * hot:热门
     * recomment:推荐
     * history:历史搜索
     */
    var type:String  = ""

    /**
     * 搜索的更新时间
     */
    var updateTime: Long = System.currentTimeMillis() / 1000

    fun update():SearchHistoryEntity {
        updateTime = System.currentTimeMillis() / 1000
        return this
    }
}

