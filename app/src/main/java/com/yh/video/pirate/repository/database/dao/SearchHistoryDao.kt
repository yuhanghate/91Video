package com.yh.video.pirate.repository.database.dao

import androidx.room.*
import com.yh.video.pirate.repository.database.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SearchHistoryDao : IDao<SearchHistoryEntity> {

    /**
     * 查询最近10条历史记录
     */
    @Query("select * from searchhistoryentity as s where s.type = :type  group by s.keyword order by s.updateTime desc  limit 10")
    abstract suspend fun queryList10ByType(type:String): List<SearchHistoryEntity>

    /**
     * 根据类型获取所有记录
     */
    @Query("select * from searchhistoryentity as s where s.type = :type group by s.keyword order by s.updateTime desc")
    abstract suspend fun queryListByType(type:String):List<SearchHistoryEntity>

    /**
     * 批量插入
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(vararg entity:SearchHistoryEntity)

    /**
     * 批量插入
     */
    @Insert
    abstract suspend fun insert( entity:List<SearchHistoryEntity>)

    /**
     * 查询关键字
     */
    @Query("select * from searchhistoryentity as s where s.keyword = :keyword and type = :type order by s.updateTime limit 1")
    abstract suspend fun queryEntityByType(keyword: String, type:String): SearchHistoryEntity?


    /**
     * 根据类型全部删除
     */
    @Query("delete from searchhistoryentity where type = :type")
    abstract suspend fun deleteListByType(type:String)

    /**
     * 插入最热
     */
    @Transaction
    open suspend fun insertHotList(list: List<SearchHistoryEntity>) {
        deleteListByType(SearchHistoryEntity.TYPE_HOT)
        insert(list)
    }

    /**
     * 插入推荐
     */
    @Transaction
    open suspend fun insertRecommentList(list: List<SearchHistoryEntity>) {
        deleteListByType(SearchHistoryEntity.TYPE_RECOMMENT)
        insert(list)
    }

}