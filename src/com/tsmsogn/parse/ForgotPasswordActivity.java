package com.tsmsogn.parse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgotPasswordActivity extends Activity implements OnClickListener {
    private final static String TAG = ForgotPasswordActivity.class
            .getCanonicalName();
    private Button mResetButton;
    private EditText mUsernameEditText;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mContext = getApplicationContext();
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mUsernameEditText = (EditText) findViewById(R.id.editText1);
        mResetButton = (Button) findViewById(R.id.button1);
        mResetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mResetButton) {
            ParseUser.requestPasswordResetInBackground(getUsername(),
                    new RequestPasswordResetCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                // An email was successfully sent with reset
                                // instructions.
                            } else {
                                // Something went wrong. Look at the
                                // ParseException to see what's up.
                                Toast.makeText(mContext, e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }

    private String getUsername() {
        return mUsernameEditText.getText().toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
        case android.R.id.home:
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            break;
        default:
            break;
        }

        return super.onOptionsItemSelected(item);
    }

}
