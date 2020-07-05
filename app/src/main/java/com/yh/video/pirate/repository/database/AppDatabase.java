package com.yh.video.pirate.repository.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.yh.video.pirate.repository.database.dao.SearchHistoryDao;
import com.yh.video.pirate.repository.database.entity.SearchHistoryEntity;
import com.yh.video.pirate.repository.database.entity.VideoSortEntity;


/**
 * e-mail : 714610354@qq.com
 * time   : 2018/04/24
 * desc   : 数据库
 *
 * @author yuhang
 */
@Database(entities = {VideoSortEntity.class, SearchHistoryEntity.class},
        version = 1, exportSchema = false)
@TypeConverters({ConvertersFactory.class})
public abstract class AppDatabase
        extends RoomDatabase {
    /**
     * 数据库名称
     */
    private static final String DATABASE_NAME = "91Video";

    private static AppDatabase INSTANCE;

    public synchronized static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    /**
     * 搜索关键字历史
     * @return
     */
    public abstract SearchHistoryDao getSearchHistoryDao();
}
