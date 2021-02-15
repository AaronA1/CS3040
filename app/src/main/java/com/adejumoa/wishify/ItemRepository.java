package com.adejumoa.wishify;


import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ItemRepository {

    private ItemDao mItemDao;
    private LiveData<List<Item>> allItems;

    ItemRepository(Application application) {
        ItemRoomDatabase db = ItemRoomDatabase.getDatabase(application);
        mItemDao = db.itemDao();
        allItems = mItemDao.getAllItems();
    }

    LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public void insert (Item item) {
        new insertAsyncTask(mItemDao).execute(item);
    }

    public void delete(Item item)  {
        new deleteItemAsyncTask(mItemDao).execute(item);
    }

    public void deleteAll()  {
        new deleteAllItemsAsyncTask(mItemDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Item, Void, Void> {

        private ItemDao mAsyncTaskDao;

        insertAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }

    private static class deleteItemAsyncTask extends AsyncTask<Item, Void, Void> {
        private ItemDao mAsyncTaskDao;

        deleteItemAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Item... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    private static class deleteAllItemsAsyncTask extends AsyncTask<Void, Void, Void> {

        private ItemDao mAsyncTaskDao;

        deleteAllItemsAsyncTask(ItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

}
