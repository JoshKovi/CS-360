package com.zybooks.project3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Add_Remove extends AppCompatActivity implements toolbarFunctionality{


    //Handful of useful Variables for Add_Remove class
    public InventoryDatabase db;
    public final int STANDARD_PULL = 50;
    public InventoryItem item;
    public PopupWindow pw;
    public FloatingActionButton adder;
    public RecyclerView RVInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_remove);

        //Adds an onclick listener for the floating button
        adder = findViewById(R.id.floatingActionButton);
        adder.setOnClickListener(this::addItem);

        //Creates database instance and then fills item with 50 entries
        db = new InventoryDatabase(getApplicationContext());
        item = new InventoryItem(db.getInventoryItems(STANDARD_PULL));

        //Recycler View setup
        RVInventory = findViewById(R.id.inventoryRecycler);
        ItemAdapter adapter = new ItemAdapter(item.DatabaseItems);
        RVInventory.setAdapter(adapter);
        RVInventory.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void inventoryClicked(View view) {
        //Changes intent to inventory activity
        Intent intent = new Intent(Add_Remove.this, inventory.class);
        startActivity(intent);
    }

    @Override
    public void orderClicked(View view) {
        //TODO Not implemented currently, placeholder
        Intent intent = new Intent(Add_Remove.this, Message.class);
        startActivity(intent);
    }

    @Override
    public void addRemoveClicked(View view) {
        //Navigates to Add_Remove IE refresh
        Intent intent = new Intent(Add_Remove.this, Add_Remove.class);
        startActivity(intent);
    }

    @Override
    public void logClicked(View view) {
        //TODO log not implemented yet, placeholder
        Intent intent = new Intent(Add_Remove.this, Message.class);
        startActivity(intent);
    }

    @Override
    public void messageClicked(View view) {
        //Navigates to message screen
        Intent intent = new Intent(Add_Remove.this, Message.class);
        startActivity(intent);

    }

    public void expand(View view) {
    //TODO not function atm, could not get an onClick listener to properly work yet
        //Think I know the problem and will fix later for portfolio

        if(view.findViewById(R.id.stkNumberView).getVisibility() == View.GONE){
            view.findViewById(R.id.stkNumberView).setVisibility(View.VISIBLE);
            view.findViewById(R.id.partNumberView).setVisibility(View.VISIBLE);
            view.findViewById(R.id.locationView).setVisibility(View.VISIBLE);
            view.findViewById(R.id.UOIView).setVisibility(View.VISIBLE);
            view.findViewById(R.id.qtyView).setVisibility(View.VISIBLE);
        }
        else{
            view.findViewById(R.id.stkNumberView).setVisibility(View.GONE);
            view.findViewById(R.id.partNumberView).setVisibility(View.GONE);
            view.findViewById(R.id.locationView).setVisibility(View.GONE);
            view.findViewById(R.id.UOIView).setVisibility(View.GONE);
            view.findViewById(R.id.qtyView).setVisibility(View.GONE);
        }


    }

    public void verifyDelete(View view) {

        //Opens a popup window then assigns values
        LayoutInflater inflater = (LayoutInflater) Add_Remove.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.delete_inventory_view, null);
        View Rec_Item = (View) view.getParent().getParent();

        ((TextView)layout.findViewById(R.id.item_headerPop)).setText(((TextView)Rec_Item.findViewById(R.id.item_header)).getText().toString());
        ((TextView)layout.findViewById(R.id.stkNumberPop)).setText(((TextView)Rec_Item.findViewById(R.id.stkNumberView)).getText().toString());
        ((TextView)layout.findViewById(R.id.partNumberPop)).setText(((TextView)Rec_Item.findViewById(R.id.partNumberView)).getText().toString());
        ((TextView)layout.findViewById(R.id.locationPop)).setText(((TextView)Rec_Item.findViewById(R.id.locationView)).getText().toString());
        ((TextView)layout.findViewById(R.id.UOIPop)).setText(((TextView)Rec_Item.findViewById(R.id.UOIView)).getText().toString());
        ((TextView)layout.findViewById(R.id.qtyPop)).setText(((TextView)Rec_Item.findViewById(R.id.qtyView)).getText().toString());

        //Sets the window to 80% of phone screen size
        float width =Add_Remove.this.getResources().getDisplayMetrics().widthPixels;
        float height =Add_Remove.this.getResources().getDisplayMetrics().heightPixels;
        pw = new PopupWindow(layout, (int)(width*0.8), (int)(height*0.8), true);
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }

    public void addItem(View view) {

        //Opens popup window and populates fields
        LayoutInflater inflater = (LayoutInflater) Add_Remove.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.add_inventory_view, null);

        ((TextView)layout.findViewById(R.id.item_headerPop)).setText("Item Name: ");
        ((TextView)layout.findViewById(R.id.stkNumberPop)).setText("Stk #:");
        ((TextView)layout.findViewById(R.id.partNumberPop)).setText("PN: ");
        ((TextView)layout.findViewById(R.id.locationPop)).setText("Location: ");
        ((TextView)layout.findViewById(R.id.UOIPop)).setText("Unit of Issue:");
        ((TextView)layout.findViewById(R.id.qtyPop)).setText("Qty on Hand: ");

        //Sets to 80% screen Size
        float width =Add_Remove.this.getResources().getDisplayMetrics().widthPixels;
        float height =Add_Remove.this.getResources().getDisplayMetrics().heightPixels;
        pw = new PopupWindow(layout, (int)(width*0.8), (int)(height*0.8), true);
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }

    public void Add(View view){

        //Adds an item to the ArrayList, then attempts to add to database
        InventoryItem new_item = new InventoryItem();
        new_item.Item.replace("name", ((EditText)pw.getContentView().findViewById(R.id.item_name_input)).getText().toString());
        new_item.Item.replace("stock_num", ((EditText)pw.getContentView().findViewById(R.id.item_stk_input)).getText().toString());
        new_item.Item.replace("part_num", ((EditText)pw.getContentView().findViewById(R.id.item_pn_input)).getText().toString());
        new_item.Item.replace("item_loc", ((EditText)pw.getContentView().findViewById(R.id.item_loc_input)).getText().toString());
        new_item.Item.replace("UOI", ((EditText)pw.getContentView().findViewById(R.id.item_UOI_input)).getText().toString());
        new_item.Item.replace("qty", ((EditText)pw.getContentView().findViewById(R.id.item_qty_input)).getText().toString());

        long id_num = db.addInventoryItem(new_item.Item, 0);

        //Shows appropriate Toast message based on success of add to database
        if(id_num!=-1){
            Toast.makeText(getApplicationContext(), "Successfully Added Item!", Toast.LENGTH_SHORT).show();
            new_item.Item.replace("id", Long.toString(id_num));
            item.DatabaseItems.add(new_item.Item);
            db.addInventoryItem(new_item.Item);
            onResume();
            pw.dismiss();
        }
        else{
            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            pw.dismiss();
        }
    }

    public void Delete(View view) {
        //Deletes an item based off name and Stock number

        String itemName = ((TextView)pw.getContentView().findViewById(R.id.item_headerPop)).getText().toString();
        String itemStkNum = ((TextView)pw.getContentView().findViewById(R.id.stkNumberPop)).getText().toString();
        itemStkNum = itemStkNum.replace("Stock #: ", "");
        System.out.println("Item Name: " + itemName + " Item STK: " + itemStkNum);

        //deletes in database (crashes if failed atm so boolean not used)
        boolean deleted = db.deleteItem(itemName, itemStkNum);
        pw.dismiss();
    }

    public void cancel(View view) {
        //Closes the popup window with no action
        pw.dismiss();
    }
}