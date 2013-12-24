package com.tsmsogn.parse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CreatePostActivity extends Activity implements OnClickListener {

    private Button mButton1;
    private EditText mEditText;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        mEditText = (EditText) findViewById(R.id.editText1);
        mButton1 = (Button) findViewById(R.id.button1);

        mButton1.setOnClickListener(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {

            String name = bundle.getString("name");
            position = bundle.getInt("position");

            if (name != null) {
                mEditText.setText(name);
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_post, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

        if (v == mButton1) {

            Bundle bundle = new Bundle();

            bundle.putString("name", mEditText.getText().toString());
            bundle.putInt("position", position);

            Intent intent = new Intent();
            intent.putExtras(bundle);

            setResult(RESULT_OK, intent);
            finish();
        }

    }

}
