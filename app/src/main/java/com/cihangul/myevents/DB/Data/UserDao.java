package com.cihangul.myevents.DB.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.cihangul.myevents.DB.Models.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM users WHERE email= :email AND password= :password")
    LiveData<User> getUser(String email, String password);
}
