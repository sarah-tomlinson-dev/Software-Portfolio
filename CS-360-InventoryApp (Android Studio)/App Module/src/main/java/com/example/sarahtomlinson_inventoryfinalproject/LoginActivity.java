package com.example.sarahtomlinson_inventoryfinalproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 *
 *
 */
public class LoginActivity extends AppCompatActivity {

    Activity activity;
    Button LoginButton, RegisterButton;
    EditText Username, Password;
    String UsernameHolder, PasswordHolder;
    Boolean EmptyHolder;
    SQLiteDatabase db;
    SQLiteDBHelper handler;
    String TempPassword = "NOT_FOUND";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;

        LoginButton = findViewById(R.id.button_login);
        RegisterButton = findViewById(R.id.button_register);
        Username = findViewById(R.id.edit_username);
        Password = findViewById(R.id.edit_password);
        handler = new SQLiteDBHelper(this);

        // Adding click listener to sign in login button
        LoginButton.setOnClickListener(view -> {
            // Call Login function
            LoginFunction();
        });


        RegisterButton.setOnClickListener(v -> {
            // Switch to RegisterActivity
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    // Login function
    @SuppressLint("Range")
    public void LoginFunction() {

        String message = CheckEditTextNotEmpty();

        if (EmptyHolder) {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return;
        }

        db = handler.getReadableDatabase();

        Cursor cursor = db.query(
                SQLiteDBHelper.USERS_TABLE_NAME,
                null,
                SQLiteDBHelper.COLUMN_1_NAME + "=?",
                new String[]{UsernameHolder},
                null, null, null
        );

        if (cursor.moveToFirst()) {

            TempPassword = cursor.getString(
                    cursor.getColumnIndexOrThrow(SQLiteDBHelper.COLUMN_2_PASSWORD)
            );

            cursor.close();
            CheckFinalResult();

        } else {

            cursor.close();

            Toast.makeText(this,
                    "User not registered",
                    Toast.LENGTH_LONG).show();
        }
    }

    // Checking editText fields are not empty.
    public String CheckEditTextNotEmpty() {
        // Getting value from fields and storing into string variable
        String message = "";
        UsernameHolder = Username.getText().toString().trim();
        PasswordHolder = Password.getText().toString().trim();

        if (UsernameHolder.isEmpty()) {
            Username.requestFocus();
            EmptyHolder = true;
            message = "Username is Empty";
        } else if (PasswordHolder.isEmpty()) {
            Password.requestFocus();
            EmptyHolder = true;
            message = "User Password is Empty";
        } else {
            EmptyHolder = false;
        }
        return message;
    }

    // Checking entered password from SQLite database username associated password
    public void CheckFinalResult() {
        if (TempPassword.equals(PasswordHolder)) {
            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

            // Sending Name to ItemListFragment using intent
            Bundle bundle = new Bundle();
            bundle.putString("user_name", UsernameHolder);

            // Going to ItemListFragment after login success message
            Intent intent = new Intent(LoginActivity.this, ListActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

            // Empty editText  after login successful and close database
            EmptyEditTextAfterDataInsert();
        } else {
            // Display error message if credentials are not correct
            Toast.makeText(LoginActivity.this, "Incorrect Username or Password\nor User Not Registered", Toast.LENGTH_LONG).show();
        }
        TempPassword = "NOT_FOUND";
    }

    // Empty edittext after login successful
    public void EmptyEditTextAfterDataInsert() {
        Username.getText().clear();
        Password.getText().clear();
    }
}
