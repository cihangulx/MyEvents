package com.cihangul.myevents.DB.Data;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.cihangul.myevents.DB.Models.Event;

import java.util.List;

/**
 * Created by Cihan on 5.11.2018.
 */
@Dao
public interface EventDao {


    @Query("SELECT * FROM events   WHERE userId= :userId ORDER BY eventId DESC")
    LiveData<List<Event>> getEventList(int userId);

    // TODO: 4.11.2018 maybe return int or long
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addEvent(Event event);

    @Update
    void updateEvent(Event event);

    @Delete
    void removeEvent(Event event);

}