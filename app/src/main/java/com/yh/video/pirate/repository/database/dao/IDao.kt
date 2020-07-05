package com.yh.video.pirate.repository.database.dao

import androidx.room.*
import com.yh.video.pirate.common.Identifiable
import org.jetbrains.annotations.NotNull

@Dao
interface IDao<T : Identifiable<Int>> {

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