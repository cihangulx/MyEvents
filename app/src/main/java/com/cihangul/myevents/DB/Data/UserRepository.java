package com.cihangul.myevents.DB.Data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import com.cihangul.myevents.DB.Models.User;

public class UserRepository {

    private UserDao userDao;

    UserRepository(Application application) {
        userDao = AppDatabase.getInstance(application).userDao();
    }


    LiveData<User> getUser(String email, String password) {
        return userDao.getUser(email, password);
    }

    public void insertUser(User user) {
        new InsertUserAsyncTask(userDao).execute(user);
    }

    public static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        UserDao userDao;

        InsertUserAsyncTask(UserDao userDao) {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insertUser(users[0]);
            return null;
        }
    }

}
