package com.cihangul.myevents.DB.Data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.cihangul.myevents.DB.Models.Event;
import com.cihangul.myevents.DB.Models.User;

@Database(entities = {Event.class, User.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;


    public abstract EventDao eventDao();

    public abstract UserDao userDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "event_db")
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

}
