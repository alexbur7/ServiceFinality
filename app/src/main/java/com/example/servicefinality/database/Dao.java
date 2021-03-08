package com.example.servicefinality.database;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface Dao {

    @Query("SELECT * FROM ApplicationPath")
    ApplicationPath getPath();

    @Update
    void updatePath(ApplicationPath pack);

    @Insert
    void insertPackage(ApplicationPath pack);
}
