package com.tsmsogn.parse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.ParseUser;

public class MainActivity extends Activity {
    private final static String TAG = MainActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ParseAnalytics.trackAppOpened(getIntent());
        //
        // ParseObject testObject = new ParseObject("TestObject");
        // testObject.put("hoge", "piyo");
        // testObject.saveInBackground();
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
        } else {
            startActivityForResult(new Intent(this, SignInActivity.class), 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Log.d(TAG, "onActivityResult");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Log.d(TAG, "MenuItem id =" + item.getItemId());

        switch (item.getItemId()) {
        case R.id.item1:
            ParseUser.logOut();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            break;

        default:
            break;
        }

        // TODO Auto-generated method stub
        return super.onOptionsItemSelected(item);
    }

}
