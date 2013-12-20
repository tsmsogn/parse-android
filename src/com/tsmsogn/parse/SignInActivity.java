package com.tsmsogn.parse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignInActivity extends Activity implements OnClickListener {
    private static final String TAG = SignInActivity.class.getCanonicalName();

    private Context mContext;
    private EditText mUsernameEditText;
    private EditText mPasswordEditText;
    private Button mSignInButton;

    private Button mSignUpButton;

    private TextView mForgotYourPasswordButton;

    private ImageView mSignInWithTwitterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mContext = getApplicationContext();

        mUsernameEditText = (EditText) findViewById(R.id.editText1);
        mPasswordEditText = (EditText) findViewById(R.id.editText2);
        mSignInButton = (Button) findViewById(R.id.button1);
        mSignUpButton = (Button) findViewById(R.id.button2);
        mSignInWithTwitterImageView = (ImageView) findViewById(R.id.imageView1);
        mForgotYourPasswordButton = (TextView) findViewById(R.id.textView1);
        mSignInButton.setOnClickListener(this);
        mSignUpButton.setOnClickListener(this);
        mForgotYourPasswordButton.setOnClickListener(this);
        mSignInWithTwitterImageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mSignInButton) {
            signIn(getUsername(), getPassword());
        } else if (v == mSignUpButton) {
            final ParseUser user = new ParseUser();

            final String username = getUsername();
            final String password = getPassword();

            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(username);
            user.signUpInBackground(new SignUpCallback() {

                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        signIn(username, password);
                    } else {
                        Toast.makeText(mContext, e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else if (v == mSignInWithTwitterImageView) {

        } else if (v == mForgotYourPasswordButton) {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
            finish();
        }
    }

    private String getPassword() {
        return mPasswordEditText.getText().toString();
    }

    private String getUsername() {
        return mUsernameEditText.getText().toString();
    }

    private void signIn(String username, String password) {

        ParseUser.logInInBackground(username, password, new LogInCallback() {

            @Override
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }

            }
        });

    }

}
