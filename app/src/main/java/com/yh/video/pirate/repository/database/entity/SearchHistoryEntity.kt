package com.yh.video.pirate.repository.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yh.video.pirate.common.Identifiable

/**
 * 搜索过的历史记录
 */
@Entity
class SearchHistoryEntity: Identifiable<Int>{
    @PrimaryKey(autoGenerate = true)
    override var id: Int = 0

    /**
     * 关键字
     */
    var keyword: String = ""

    /**
     * 搜索的更新时间
     */
    var updateTime: Long = System.currentTimeMillis() / 1000

    fun update():SearchHistoryEntity {
        updateTime = System.currentTimeMillis() / 1000
        return this
    }
}

