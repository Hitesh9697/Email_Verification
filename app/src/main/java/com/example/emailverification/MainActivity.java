package com.example.emailverification;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTextEmailAddress, editTextText, editTextTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTextEmailAddress = findViewById(R.id.editTextTextEmailAddress);
        editTextText = findViewById(R.id.editTextText);
        editTextTextPassword = findViewById(R.id.editTextTextPassword);

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        //ParseInstallation.getCurrentInstallation().saveInBackground();
        /*ParseObject firstObject = new ParseObject("FirstClass");
        firstObject.put("message","Hey ! First message from android. Parse is now connected");
        firstObject.saveInBackground(e -> {
            if (e != null){
                Log.e("MainActivity", e.getLocalizedMessage());
            }else{
                Log.d("MainActivity","Object saved.");
            }
        });*/

        /*Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("0cDGa5Bd7zmnnSWINTtyQcQOS7YRxi0TT7SOCZHU")//"myAppId" should be sth like ->"ASHWD63787EDXSG8"
                .clientKey("OEwMQnTxxF6IQyESpRNqTE66z4WPswpNAbT6KqWf")//should be sth like ->"tdf29yedih287823"
                .server("https://parseapi.back4app.com/")//something like-> "https://parseapi.back4app.com"
                .build()
        );*/
    }

    public void signUpIsPressed(View btnView) {

        try {

            ParseUser parseUser = new ParseUser();
            parseUser.setEmail(editTextTextEmailAddress.getText().toString());
            parseUser.setUsername(editTextText.getText().toString());
            parseUser.setPassword(editTextTextPassword.getText().toString());

            parseUser.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        ParseUser.logOut();
                        alertDisplay("Account creation successfully", "Please verify email", false);
                    } else {
                        ParseUser.logOut();
                        alertDisplay("Eror account creation failed", "Account could not be created" + " : " + e.getMessage(), true);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void alertDisplay(String title, String message, final Boolean error) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if(!error) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
                AlertDialog ok = builder.create();
                ok.show();

    }
}