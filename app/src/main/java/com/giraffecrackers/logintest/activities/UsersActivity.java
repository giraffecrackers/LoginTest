package com.giraffecrackers.logintest.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.giraffecrackers.logintest.R;

/**
 * Created by giraffecrackers on 19.11.17.
 */

public class UsersActivity extends AppCompatActivity {

    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        textViewName = (TextView) findViewById(R.id.text1);
        String nameFromIntent = getIntent().getStringExtra("NURSEID");
        textViewName.setText("Welcome" + nameFromIntent);

    }
}
