package com.adejumoa.wishify;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_table")
public class Item {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    public Item(@NonNull String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
