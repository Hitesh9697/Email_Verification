package com.example.emailverification;

import com.parse.Parse;
import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("0cDGa5Bd7zmnnSWINTtyQcQOS7YRxi0TT7SOCZHU")
                // if desired
                .clientKey("OEwMQnTxxF6IQyESpRNqTE66z4WPswpNAbT6KqWf")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
