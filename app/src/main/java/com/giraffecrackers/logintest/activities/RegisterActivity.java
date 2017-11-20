package com.giraffecrackers.logintest.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.giraffecrackers.logintest.R;
import com.giraffecrackers.logintest.helper.InputValidation;
import com.giraffecrackers.logintest.model.User;
import com.giraffecrackers.logintest.sql.DatabaseHelper;

/**
 * Created by giraffecrackers on 19.11.17.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = RegisterActivity.this;
    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutNurseID;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextNurseID;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews(){
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName = (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutNurseID = (TextInputLayout)findViewById(R.id.textInputLayoutNurseID);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextName = (TextInputEditText)findViewById(R.id.textInputEditTextName);
        textInputEditTextNurseID = (TextInputEditText)findViewById(R.id.textInputEditTextNurseID);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = (AppCompatButton) findViewById(R.id.appCompatButtonRegister);
        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(R.id.appCompatTextViewLoginLink);
    }

    private  void initListeners(){
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    private void initObjects(){
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;
            case R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }
    }

    private void postDataToSQLite(){
        if(!inputValidation.isInputEditTextFilled(textInputEditTextName,textInputLayoutName, getString(R.string.error_message_name))){
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextNurseID,textInputLayoutNurseID, getString(R.string.error_message_nurseID))){
            return;
        }
        if(!inputValidation.isInputEditTextNurseID(textInputEditTextNurseID,textInputLayoutNurseID, getString(R.string.error_message_nurseID))){
            return;
        }
        if(!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_password))){
            return;
        }
        if(!inputValidation.isInputEditTextMatches(textInputEditTextPassword,textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword,getString(R.string.error_password_match))){
            return;
        }

        if(!databaseHelper.checkUser(textInputEditTextNurseID.getText().toString().trim())){
            user.setName(textInputEditTextName.getText().toString().trim());
            user.setNurseid(textInputEditTextNurseID.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            //Snackbar что бы показать успех
            Snackbar.make(nestedScrollView, getString(R.string.success_message),Snackbar.LENGTH_SHORT).show();
            emptyInputEditText();
        }else{
            //неудача
            Snackbar.make(nestedScrollView, getString(R.string.error_nurseid_exists), Snackbar.LENGTH_SHORT).show();
        }
    }
    private void emptyInputEditText(){
        textInputEditTextName.setText(null);
        textInputEditTextNurseID.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }



}
