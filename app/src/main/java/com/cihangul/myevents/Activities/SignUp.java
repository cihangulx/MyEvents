package com.cihangul.myevents.Activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.cihangul.myevents.DB.Data.EventViewModel;
import com.cihangul.myevents.DB.Data.UserViewModel;
import com.cihangul.myevents.DB.Models.User;
import com.cihangul.myevents.R;

import java.security.MessageDigest;

public class SignUp extends AppCompatActivity {

    EditText name, email, password;

    UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    public void register(View view) {
        if (!isValid())
            return;
        User user = new User();
        user.setName(name.getText().toString().trim());
        user.setEmail(email.getText().toString().trim());
        user.setPassword(sha256(password.getText().toString().trim()));
        userViewModel.insertUser(user);
        startActivity(new Intent(this, Login.class).putExtra("email", user.getEmail()));
        finish();
    }


    public  String sha256(String base) {
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

    boolean isValid() {
        if (email.getText().toString().trim().isEmpty()) {
            email.setError("Required");
            return false;
        } else if (!isEmail(email.getText().toString().trim())) {
            email.setError("Not Valid E-Mail");
            return false;
        } else if (password.getText().toString().trim().isEmpty()) {
            password.setError("Required");
            return false;
        } else if (password.getText().toString().trim().length() < 6) {
            password.setError("Must be greater than 6 characters");
            return false;
        } else {
            return true;
        }
    }

    public boolean isEmail(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}
