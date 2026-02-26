package com.example.sarahtomlinson_inventoryfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

public class AddItem extends AppCompatActivity {

    EditText item_description, item_quantity;
    Button cancel_button, add_item_button;
    Boolean empty_place;
    int itemId = -1;
    String description_holder, quantity_holder;
    SQLiteItemDBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_item);

        // Initiate buttons, textViews, and editText variables
        item_description = findViewById(R.id.edit_item_name);
        item_quantity = findViewById(R.id.edit_quantity);
        cancel_button = findViewById(R.id.button_cancel);
        add_item_button = findViewById(R.id.button_save);
        itemId = getIntent().getIntExtra("ITEM_ID", -1);
        db = new SQLiteItemDBHelper(this);

        if(itemId != -1){
            Item item = db.readItem(itemId);

            item_description.setText(item.getDescription());
            item_quantity.setText(item.getQuantity());
        }

        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());

        // add_item_button click listener, pass to ListActivity finally
        add_item_button.setOnClickListener(view -> insertItemEntryToDB());

        // Adding click listener to addCancelButton
        cancel_button.setOnClickListener(view -> {
            // Return to ListActivity after cancel adding item
            Intent add = new Intent();
            setResult(0, add);
            this.finish();
        });
    }

    // After inserting ItemEntry to db, send data to ListActivity
    public void insertItemEntryToDB() {
        String message = CheckEditTextNotEmpty();

        if (!empty_place) {
            String description = description_holder;
            String quantity = quantity_holder;

            if(itemId == -1){
                Item item = new Item(description, quantity);
                db.createItem(item);
            }
            else{
                Item item = new Item(itemId, description, quantity);
                db.updateItem(item);
            }

            // Display toast message after insert in table
            Toast.makeText(this,"Item Added Successfully", Toast.LENGTH_LONG).show();

            // close AddItemActivity
            Intent add = new Intent();
            setResult(RESULT_OK, add);
            this.finish();
        } else {
            // Display toast message if item description is empty and focus the field
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    // Checking item description is not empty
    public String CheckEditTextNotEmpty() {
        // Getting value from fields and storing into string variable
        String message = "";
        quantity_holder = item_quantity.getText().toString().trim();
        description_holder = item_description.getText().toString().trim();

        // warn user if no description entered, else return message
        if (description_holder.isEmpty()) {
            item_description.requestFocus();
            empty_place = true;
            message = "Empty Item Description!";
        } else {
            empty_place = false;
        }
        return message;
    }
}