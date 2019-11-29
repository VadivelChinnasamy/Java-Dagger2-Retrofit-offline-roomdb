package com.javadagger2.roomdb;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.javadagger2.dataclass.Result;

@Database(entities = {Result.class},version = 3)
public abstract class AppDataBase extends RoomDatabase {
    public abstract MoviesListDao moviesListDao();

}
