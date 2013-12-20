package com.tsmsogn.parse;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;

public class ToDoListApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "", "");

        // ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        // Optionally enable public read access while disabling public write
        // access.
        // defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }

}
