package com.adejumoa.wishify;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Item.class}, version = 1, exportSchema = false)
public abstract class ItemRoomDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();
    private static ItemRoomDatabase INSTANCE;

    static ItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, "item_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ItemDao mDao;
        String [] items = {"dolphin", "crocodile", "cobra"};

        PopulateDbAsync(ItemRoomDatabase db) {
            mDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll();

            for( int i = 0; i <= items.length - 1; i++) {
                Item item = new Item(items[i]);
                mDao.insert(item);
            }
            return null;
        }
    }

}
