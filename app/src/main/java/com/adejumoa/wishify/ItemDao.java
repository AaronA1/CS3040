package com.adejumoa.wishify;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insert(Item item);

    @Delete
    void delete(Item item);

    @Update
    void update(Item item);

    @Query("DELETE FROM item_table")
    void deleteAll();

    @Query("SELECT * from item_table ORDER BY name ASC")
    LiveData<List<Item>> getAllItems();
}
