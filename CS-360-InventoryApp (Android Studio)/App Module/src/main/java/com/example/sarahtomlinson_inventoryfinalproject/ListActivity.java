package com.example.sarahtomlinson_inventoryfinalproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ListActivity extends AppCompatActivity {

    // Declare buttons and ListView to display items
    TableLayout item_table;
    FloatingActionButton add_new_item;
    ImageButton sms_button;
    int itemId = -1;
    SQLiteItemDBHelper db;
    static String PhoneNumHolder;
    AlertDialog AlertDialog = null;
    ArrayList<Item> all_items;
    // Tracking messages sent
    private ArrayList<Integer> smsSentItems = new ArrayList<>();

    private static final int USER_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static boolean smsAuthorized = false;
    private static boolean deleteItems = false;

    private void refreshTable() {

        int childCount = item_table.getChildCount();
        if (childCount > 1) {
            item_table.removeViews(1, childCount - 1);
        }

        all_items = (ArrayList<Item>) db.getAllItems();

        for (Item item : all_items) {

            TableRow row = new TableRow(this);

            TextView description = new TextView(this);
            description.setText(item.getDescription());
            description.setPadding(8,8,8,8);
            description.setTextColor(getResources().getColor(android.R.color.white));

            TextView quantity = new TextView(this);
            quantity.setText(item.getQuantity());
            quantity.setPadding(8,8,8,8);
            quantity.setTextColor(getResources().getColor(android.R.color.white));

            if (smsAuthorized &&
                    item.getQuantity().equals("0") &&
                    !smsSentItems.contains(item.getId())) {

                SendSMSMessage(this);
                smsSentItems.add(item.getId());
            }

            ImageButton editBtn = new ImageButton(this);
            editBtn.setImageResource(android.R.drawable.ic_menu_edit);

            ImageButton deleteBtn = new ImageButton(this);
            deleteBtn.setImageResource(android.R.drawable.ic_delete);

            editBtn.setOnClickListener(v -> {
                Intent intent = new Intent(ListActivity.this, AddItem.class);
                intent.putExtra("ITEM_ID", item.getId());
                startActivityForResult(intent, 1);
            });

            deleteBtn.setOnClickListener(v -> {
                db.deleteItem(item);
                refreshTable();
                Toast.makeText(this,"Item Deleted",Toast.LENGTH_SHORT).show();
            });

            row.addView(description);
            row.addView(quantity);
            row.addView(editBtn);
            row.addView(deleteBtn);

            item_table.addView(row);
        }
    }

    public void deleteItem() {

        if (itemId == -1) {
            Toast.makeText(this, "No item to delete", Toast.LENGTH_SHORT).show();
            return;
        }

        Item item = new Item();
        item.setId(itemId);

        db.deleteItem(item);
        refreshTable();

        Toast.makeText(this, "Item Deleted Successfully", Toast.LENGTH_LONG).show();

        Intent delete = new Intent();
        setResult(RESULT_OK, delete);
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            refreshTable();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        // initialize name, count, buttons, View variables
        add_new_item = findViewById(R.id.fab_add_item);
        sms_button = findViewById(R.id.sms_button);
        item_table = findViewById(R.id.item_table);
        db = new SQLiteItemDBHelper(this);

        all_items = (ArrayList<Item>) db.getAllItems();
        refreshTable();

        // add_button click listener
        add_new_item.setOnClickListener(view -> {
            Intent intent = new Intent(ListActivity.this, AddItem.class);
            startActivityForResult(intent, 1);
        });

        // sms button click listener
        sms_button.setOnClickListener(view -> {
            // Request sms permission for the device
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.SEND_SMS)) {
                    Toast.makeText(this,"Device SMS Permission is Needed", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[] {Manifest.permission.SEND_SMS},
                            USER_PERMISSIONS_REQUEST_SEND_SMS);
                }
            } else {
                Toast.makeText(this,"Device SMS Permission is Allowed", Toast.LENGTH_LONG).show();
            }
            // Open SMS Alert Dialog
            AlertDialog = SMSNotification.buttonToggle(this);
            AlertDialog.show();
        });

    }


    // Receive and evaluate user response from AlertDialog to send SMS
    public static void AllowSendSMS() {
        smsAuthorized = true;
    }

    public static void DenySendSMS() {
        smsAuthorized = false;
    }

    public static void SendSMSMessage(Context context) {

        String phone_number = PhoneNumHolder;
        String sms_msg = "An item in the inventory is empty (0 remaining).";

        if (!smsAuthorized) {
            Toast.makeText(context, "SMS Alerts Disabled in App Settings", Toast.LENGTH_LONG).show();
            return;
        }

        if (ContextCompat.checkSelfPermission(context,
                android.Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(context, "Android SMS Permission Not Granted", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            SmsManager sms_manager = SmsManager.getDefault();
            sms_manager.sendTextMessage(phone_number, null, sms_msg, null, null);
            Toast.makeText(context, "SMS Sent", Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            Toast.makeText(context, "SMS Failed to Send", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == USER_PERMISSIONS_REQUEST_SEND_SMS) {

            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this,
                        "SMS Permission Granted",
                        Toast.LENGTH_LONG).show();

            } else {

                Toast.makeText(this,
                        "SMS Permission Denied",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}