package com.cihangul.myevents.DB.Data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.cihangul.myevents.DB.Models.User;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    public LiveData<User> getUser(String email, String password) {
        return  userRepository.getUser(email, password);
    }

}
