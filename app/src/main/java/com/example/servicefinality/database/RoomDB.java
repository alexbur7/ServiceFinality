package com.example.servicefinality.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities ={ApplicationPath.class},version = 1)
public abstract class RoomDB extends RoomDatabase {
    public abstract Dao dao();
}
