package cn.kk.roomdemo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Word.class},version = 1,exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {

   private static WordDatabase INSTANCE;
    abstract WordDao getWordDao();

    static synchronized WordDatabase getINSTANCE(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),WordDatabase.class,"word")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

}
