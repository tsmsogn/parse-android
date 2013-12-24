package com.tsmsogn.parse;

import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MainActivity extends ListActivity {
    private final static String TAG = MainActivity.class.getCanonicalName();
    public List<ParseObject> posts;
    private Dialog progressDialog;

    private final static int ACTIVITY_SIGNIN = 0;
    private final static int ACTIVITY_CREATE = 1;
    private final static int ACTIVITY_EDIT = 2;

    private static final int DELETE_ID = Menu.FIRST + 1;

    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        // Override this method to do custom remote calls
        protected Void doInBackground(Void... params) {
            // Gets the current list of todos in sorted order
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Post");
            query.orderByDescending("_created_at");

            try {
                posts = query.find();
            } catch (ParseException e) {

            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            MainActivity.this.progressDialog = ProgressDialog.show(
                    MainActivity.this, "", "Loading...", true);
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            // Put the list of todos into the list view
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    MainActivity.this, R.layout.todo_row);
            for (ParseObject todo : posts) {
                adapter.add((String) todo.get("name"));
            }
            setListAdapter(adapter);
            MainActivity.this.progressDialog.dismiss();
            TextView empty = (TextView) findViewById(android.R.id.empty);
            // empty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new RemoteDataTask().execute();
        registerForContextMenu(getListView());
    }

    @Override
    protected void onResume() {
        super.onResume();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
        } else {
            startActivityForResult(new Intent(this, SignInActivity.class),
                    ACTIVITY_SIGNIN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTIVITY_SIGNIN && resultCode == RESULT_OK) {
            Log.d(TAG, "From SignIn");
        } else if ((requestCode == ACTIVITY_CREATE || requestCode == ACTIVITY_EDIT)
                && resultCode == RESULT_OK) {
            Log.d(TAG, "From AddPost");

            final Bundle extras = data.getExtras();

            switch (requestCode) {
            case ACTIVITY_CREATE:
                new RemoteDataTask() {
                    protected Void doInBackground(Void... params) {
                        String name = extras.getString("name");
                        ParseObject post = new ParseObject("Post");
                        post.put("name", name);
                        try {
                            post.save();
                        } catch (ParseException e) {
                        }

                        super.doInBackground();
                        return null;
                    }
                }.execute();
                break;
            case ACTIVITY_EDIT:
                // Edit the remote object
                final ParseObject post;
                post = posts.get(extras.getInt("position"));
                post.put("name", extras.getString("name"));

                new RemoteDataTask() {
                    protected Void doInBackground(Void... params) {
                        try {
                            post.save();
                        } catch (ParseException e) {
                        }
                        super.doInBackground();
                        return null;
                    }
                }.execute();
                break;
            }
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
            // TODO Review
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            break;
        case R.id.item2:
            startActivityForResult(new Intent(this, CreatePostActivity.class),
                    ACTIVITY_CREATE);
            break;

        default:
            break;
        }

        // TODO Auto-generated method stub
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent intent = new Intent(this, CreatePostActivity.class);

        intent.putExtra("name", posts.get(position).getString("name")
                .toString());
        intent.putExtra("position", position);

        startActivityForResult(intent, ACTIVITY_EDIT);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        Log.d(TAG, "onCreateContextMenu");

        menu.add(0, DELETE_ID, 0, R.string.menu_delete);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {

        case DELETE_ID:
            AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
                    .getMenuInfo();

            // Delete the remote object
            final ParseObject post = posts.get(info.position);

            new RemoteDataTask() {
                protected Void doInBackground(Void... params) {
                    try {
                        post.delete();
                    } catch (ParseException e) {
                    }
                    super.doInBackground();
                    return null;
                }
            }.execute();
            return true;

        default:
            break;
        }

        // TODO Auto-generated method stub
        return super.onContextItemSelected(item);
    }

}
