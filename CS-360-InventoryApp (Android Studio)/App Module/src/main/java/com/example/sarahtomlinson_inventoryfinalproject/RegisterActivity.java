package com.example.sarahtomlinson_inventoryfinalproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class RegisterActivity extends AppCompatActivity {

    Button RegisterButton;
    EditText UsernameHolder, PasswordHolder;
    Boolean EmptyHolder;
    SQLiteDatabase db;
    SQLiteDBHelper handler;
    String F_Result = "Not_Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        UsernameHolder = findViewById(R.id.edit_username);
        PasswordHolder = findViewById(R.id.edit_password);
        RegisterButton = findViewById(R.id.button_register);
        handler = new SQLiteDBHelper(this);

        // Adding click listener to register Register Button
        RegisterButton.setOnClickListener(view -> {
            String message = CheckEditTextNotEmpty();

            if (!EmptyHolder) {
                // Check if username already exists in database
                CheckUsernameAlreadyExists();
                // Empty editText fields after done inserting in database
                EmptyEditTextAfterDataInsert();
            } else {
                // Display toast message if any field is empty and focus the field
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    // Register new user into database
    public void InsertUserIntoDatabase(){
        String name = UsernameHolder.getText().toString().trim();
        String pass = PasswordHolder.getText().toString().trim();

        User user = new User(name, pass);
        handler.createUser(user);

        // Printing toast message after done inserting.
        Toast.makeText(RegisterActivity.this,"User Registered Successfully", Toast.LENGTH_LONG).show();

        // Going back to LoginActivity after register success message
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        this.finish();
    }

    // Checking item description is not empty
    public String CheckEditTextNotEmpty() {
        // Getting value from fields and storing into string variable
        String message = "";
        String name = UsernameHolder.getText().toString().trim();
        String pass = PasswordHolder.getText().toString().trim();

        if (name.isEmpty()) {
            UsernameHolder.requestFocus();
            EmptyHolder = true;
            message = "User Name is Empty";
        } else if (pass.isEmpty()){
            PasswordHolder.requestFocus();
            EmptyHolder = true;
            message = "User Password is Empty";
        } else {
            EmptyHolder = false;
        }
        return message;
    }

    // Check if username already exists in database
    public void CheckUsernameAlreadyExists(){
        String username = UsernameHolder.getText().toString().trim();
        db = handler.getWritableDatabase();

        // Adding search username query to cursor
        Cursor cursor = db.query(SQLiteDBHelper.USERS_TABLE_NAME, null, " " + SQLiteDBHelper.COLUMN_1_NAME + "=?", new String[]{username}, null, null, null);

        if(cursor.moveToFirst()){
            F_Result = "Username Found";
        }

        cursor.close();
        handler.close();

        // Calling method to check final result and insert data into SQLite database
        CheckFinalCredentials();
    }

    // Check login credentials are correct
    public void CheckFinalCredentials(){
        // Checking whether username is already in database
        if(F_Result.equalsIgnoreCase("Username Found"))
        {
            // If username is exists then toast msg will display
            Toast.makeText(RegisterActivity.this,"Username Already Exists",Toast.LENGTH_LONG).show();
        }
        else {
            // If username already doesn't exists then user registration details will entered to SQLite database
            InsertUserIntoDatabase();
        }
        F_Result = "Not_Found" ;
    }

    // Empty edittext after done inserting in database
    public void EmptyEditTextAfterDataInsert(){
        UsernameHolder.getText().clear();
        PasswordHolder.getText().clear();
    }

}
