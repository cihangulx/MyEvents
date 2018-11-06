package com.cihangul.myevents.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.cihangul.myevents.DB.Data.UserViewModel;
import com.cihangul.myevents.DB.Models.User;
import com.cihangul.myevents.R;

import java.security.MessageDigest;
import java.util.Objects;

public class Login extends AppCompatActivity {

   private EditText email, password;// Email and password
   private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        //UserViewModel instance
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        // if user came from register screen email is a favor
        try {
            if (Objects.requireNonNull(getIntent().getStringExtra("email")) != null)
                email.setText(getIntent().getStringExtra("email"));
        } catch (NullPointerException ignored) {
        }


    }

    public void login(final View view) {
        //email and password will not be empty
        if (email.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty())
            return;

        // get User with email and password if all fields not correct user will be null
        //password must be sha256
        userViewModel.getUser(email.getText().toString(), sha256(password.getText().toString())).observeForever(new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                if (user != null) {
                    //email and password correct save session for later
                    saveSession(user);
                } else {
                    // User not found show message
                    Snackbar.make(view, "Email or password is incorrect !", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     *
     * @param view Register Button
     */
    public void register(View view) {
        startActivity(new Intent(this, SignUp.class));
    }

    //password to sha password  for security
    public String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }


    // TODO: 6.11.2018 AccountManager kullanılabilir
    /**
     * Ignore
     *
     * Simple DON'T use for real projects
     */
    private void saveSession(User user) {
        SharedPreferences sharedPreferences = getSharedPreferences("user", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user", user.toString());
        editor.apply();
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
    }

}
