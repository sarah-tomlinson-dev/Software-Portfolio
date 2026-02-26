package com.example.sarahtomlinson_inventoryfinalproject;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

// itemsScrollView will show this custom layout of ItemRowList
public class ItemRowList extends BaseAdapter {

    private final Activity context;
    ArrayList<Item> items;
    SQLiteItemDBHelper db;

    public ItemRowList(Activity context, ArrayList<Item> items, SQLiteItemDBHelper db) {
        this.context = context;
        this.items = items;
        this.db = db;
    }

    public static class ViewHolder {
        TextView textViewItemId;
        TextView textViewItemDescription;
        TextView textViewItemQuantity;
        ImageButton edit_button;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        ViewHolder text_view_holder;

        if (convertView == null) {
            text_view_holder = new ViewHolder();
            row = inflater.inflate(R.layout.activity_item_list, null, true);


            text_view_holder.textViewItemDescription = row.findViewById(R.id.item_name);
            text_view_holder.textViewItemQuantity = row.findViewById(R.id.item_quantity);

            row.setTag(text_view_holder);
        } else {
            text_view_holder = (ViewHolder) convertView.getTag();
        }

        text_view_holder.textViewItemId.setText("" + items.get(position).getId());
        text_view_holder.textViewItemDescription.setText(items.get(position).getDescription());
        text_view_holder.textViewItemQuantity.setText(items.get(position).getQuantity());


        // Check is the cell value is zero to change color and send SMS
        String value = text_view_holder.textViewItemQuantity.getText().toString().trim();
        if (value.equals("0")) {
            // bg color and text of item quantity cell when 0
            text_view_holder.textViewItemQuantity.setBackgroundColor(Color.RED);
            text_view_holder.textViewItemQuantity.setTextColor(Color.WHITE);
            ListActivity.SendSMSMessage(context.getApplicationContext());
        } else {
            // item quantity cell bg color and text color
            text_view_holder.textViewItemQuantity.setBackgroundColor(Color.parseColor("#E6E6E6"));
            text_view_holder.textViewItemQuantity.setTextColor(Color.BLACK);
        }

        return  row;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return items.size();
    }
}