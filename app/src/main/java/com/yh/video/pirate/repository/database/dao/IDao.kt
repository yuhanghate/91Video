package com.yh.video.pirate.repository.database.dao

import androidx.room.*
import com.yh.video.pirate.common.Identifiable

@Dao
interface IDao<T : Identifiable<Int>> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg entities: T): IntArray

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entities: Collection<T>): List<Int>

    @Delete
    suspend fun delete(entity: T)

    @Delete
    suspend fun delete(vararg entities: T)

    @Delete
    suspend fun delete(entities: Collection<T>)

    @Update
    suspend fun update(entity: T): Int

    @Update
    suspend fun update(vararg entities: T): Int

    @Update
    suspend fun update(entities: Collection<T>): Int

}