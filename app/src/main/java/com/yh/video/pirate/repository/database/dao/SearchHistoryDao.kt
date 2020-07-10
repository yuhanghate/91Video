package com.yh.video.pirate.repository.database.dao

import androidx.room.*
import com.yh.video.pirate.repository.database.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao : IDao<SearchHistoryEntity> {

    @Query("select * from searchhistoryentity as s  group by s.keyword order by s.updateTime desc  limit 10")
    suspend fun query(): List<SearchHistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity:SearchHistoryEntity)

    /**
     * 查询关键字
     */
    @Query("select * from searchhistoryentity as s where s.keyword = :keyword order by s.updateTime limit 1")
    suspend fun query(keyword: String): SearchHistoryEntity?

    /**
     * 匹配模糊匹配取5个
     */
    @Query("select * from searchhistoryentity as s where s.keyword LIKE '%' || :keyword || '%'  order by s.updateTime desc limit 5")
    suspend fun queryList(keyword: String): List<SearchHistoryEntity?>

    /**
     * 清空历史记录
     */
    @Query("delete from SearchHistoryEntity")
    suspend fun clear()
}