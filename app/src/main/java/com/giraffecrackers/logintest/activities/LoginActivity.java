package com.giraffecrackers.logintest.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.giraffecrackers.logintest.R;
import com.giraffecrackers.logintest.helper.InputValidation;
import com.giraffecrackers.logintest.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutNurseID;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextNurseID;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutNurseID = (TextInputLayout)findViewById(R.id.textInputLayoutNurseID);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextNurseID = (TextInputEditText)findViewById(R.id.textInputEditTextNurseID);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

    }

    private void initListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    private void initObjects(){
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

    private void verifyFromSQLite(){
        if(!inputValidation.isInputEditTextFilled(textInputEditTextNurseID,textInputLayoutNurseID, getString(R.string.error_message_nurseID))){
            return;
        }
        if(!inputValidation.isInputEditTextNurseID(textInputEditTextNurseID,textInputLayoutNurseID, getString(R.string.error_message_nurseID))){
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))){
            return;
        }
        if(databaseHelper.checkUser(textInputEditTextNurseID.getText().toString().trim(),
                textInputEditTextPassword.getText().toString().trim())){
            Intent accountsIntent = new Intent(activity, UsersActivity.class);
            accountsIntent.putExtra("NURSEID", textInputEditTextNurseID.getText().toString().trim());
            emptyInputEditText();
            startActivity(accountsIntent);
        }else{
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_nursid_password), Snackbar.LENGTH_SHORT).show();
        }


    }

    private void emptyInputEditText(){
        textInputEditTextNurseID.setText(null);
        textInputEditTextPassword.setText(null);

    }


}