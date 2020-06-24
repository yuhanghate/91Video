package com.yh.video.pirate.repository.database

import androidx.room.TypeConverter
import java.util.*

/**
 * e-mail : 714610354@qq.com
 * time   : 2018/04/24
 * desc   : 转换
 * @author yuhang
 */
class ConvertersFactory {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

}