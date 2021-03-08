package com.example.servicefinality.database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ApplicationPath {
    @PrimaryKey()
    @ColumnInfo(name = "_id")
    private int _id;

    @ColumnInfo(name = "package")
    private String mPackageName;

    public ApplicationPath(String mPackageName){
        this.mPackageName=mPackageName;
        this._id=0;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }
}
