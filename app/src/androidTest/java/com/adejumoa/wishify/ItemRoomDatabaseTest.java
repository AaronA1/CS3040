package com.adejumoa.wishify;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ItemRoomDatabaseTest {

    private ItemDao itemDao;
    private ItemRoomDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, ItemRoomDatabase.class).build();
        itemDao = db.itemDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeItemAndReadInList() {
        Item item = new Item("Test", "Test Desc", 29.99, "Test Place", 0, 0, null);
        itemDao.insert(item);
        Item[] items = itemDao.getAnyItem();
        assertEquals(item.getName(), items[0].getName());
    }

    @Test
    public void UpdateAndReadItem() {
        // Insert an item
        Item item = new Item("Test", "Test Desc", 29.99, "Test Place", 0, 0, null);
        itemDao.insert(item);

        // Get item and read
        Item[] items = itemDao.getAnyItem();
        Item itemExtracted = items[0];
        assertEquals(item.getName(), itemExtracted.getName());

        // Set and Update new name
        itemExtracted.setName("New Name");
        itemDao.update(itemExtracted);

        // Extract item and assert name different
        Item[] itemsNew = itemDao.getAnyItem();
        Item itemNew = itemsNew[0];
        assertEquals("New Name", itemNew.getName());
    }

    @Test
    public void deleteItem() {
        // Assert item in database
        Item item = new Item("Test", "Test Desc", 29.99, "Test Place", 0, 0, null);
        itemDao.insert(item);
        Item[] items = itemDao.getItems();
        assertEquals(1, items.length);

        // Get and delete item via primary key
        item = itemDao.getAnyItem()[0];
        itemDao.delete(item);

        // Assert items now empty
        items = itemDao.getItems();
        assertEquals(0, items.length);

    }
}
