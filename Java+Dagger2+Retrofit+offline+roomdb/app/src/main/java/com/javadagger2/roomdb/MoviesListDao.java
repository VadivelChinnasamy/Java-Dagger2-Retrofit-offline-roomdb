package com.javadagger2.roomdb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.javadagger2.dataclass.Result;

import java.util.List;

@Dao
public interface MoviesListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void mInsPopMovList(List<Result> result);

    @Query("SELECT * FROM POPULARMOVIE")
    List<Result> getAllDetail();

}
