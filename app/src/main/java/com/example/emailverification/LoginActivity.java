package com.example.emailverification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUser, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUser = findViewById(R.id.editTextUser);
        editTextPassword = findViewById(R.id.editTextPassword);

    }

    public void loginIsPressed(View btnLogin) {

        ParseUser.logInInBackground(editTextUser.getText().toString(), editTextPassword.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    if(user.getBoolean("emailVerified")) {
                        alertDisplay("Login successful", "Welcome " + editTextUser.getText().toString());
                    } else {
                        ParseUser.logOut();
                        alertDisplay("Login failed", "Verify email before Login");
                    }
                } else {
                    ParseUser.logOut();
                    alertDisplay("Login failed", e.getMessage() + " try again");
                }
            }
        });

    }

    private void alertDisplay(String title, String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        /*if(!error) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }*/
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();

    }
}