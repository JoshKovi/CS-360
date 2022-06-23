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

public class inventory extends AppCompatActivity implements toolbarFunctionality{

    //Useful variables for inventory screen
    public InventoryDatabase db;
    public final int STANDARD_PULL = 50;
    public InventoryItem item;
    public PopupWindow pw;
    public RecyclerView RVInventory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);


        //Creates db and pulls 50 items
        db = new InventoryDatabase(getApplicationContext());
        item = new InventoryItem(db.getInventoryItems(STANDARD_PULL));

        //Recycler view setup
        RVInventory = findViewById(R.id.inventoryRecyclerChange);
        InvAdapter adapter = new InvAdapter(item.DatabaseItems);
        RVInventory.setAdapter(adapter);
        RVInventory.setLayoutManager(new LinearLayoutManager(this));
    }


    //These are used on all (non-login screen)

    @Override
    public void inventoryClicked(View view) {
        //TODO fix switch
        Intent intent = new Intent(inventory.this, inventory.class);
        startActivity(intent);
    }

    @Override
    public void orderClicked(View view) {
        //TODO fix switch
        Intent intent = new Intent(inventory.this, Message.class);
        startActivity(intent);
    }

    @Override
    public void addRemoveClicked(View view) {
        Intent intent = new Intent(inventory.this, Add_Remove.class);
        startActivity(intent);
    }

    @Override
    public void logClicked(View view) {
        //TODO fix switch
        Intent intent = new Intent(inventory.this, Message.class);
        startActivity(intent);
    }

    @Override
    public void messageClicked(View view) {
        Intent intent = new Intent(inventory.this, Message.class);
        startActivity(intent);

    }

    public void expand(View view) {

        //Not implemented yet, see Add_remove for more detail
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

    public void changeQty(View view) {

        //Setups popup window and populates data
        LayoutInflater inflater = (LayoutInflater) inventory.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.change_qty_pop, null);
        View Rec_Item = (View) view.getParent().getParent();

        ((TextView)layout.findViewById(R.id.item_headerPop)).setText(((TextView)Rec_Item.findViewById(R.id.item_header)).getText().toString());
        ((TextView)layout.findViewById(R.id.stkNumberPop)).setText(((TextView)Rec_Item.findViewById(R.id.stkNumberView)).getText().toString());
        ((TextView)layout.findViewById(R.id.partNumberPop)).setText(((TextView)Rec_Item.findViewById(R.id.partNumberView)).getText().toString());
        ((TextView)layout.findViewById(R.id.locationPop)).setText(((TextView)Rec_Item.findViewById(R.id.locationView)).getText().toString());
        ((TextView)layout.findViewById(R.id.UOIPop)).setText(((TextView)Rec_Item.findViewById(R.id.UOIView)).getText().toString());
        ((TextView)layout.findViewById(R.id.qtyPop)).setText(((TextView)Rec_Item.findViewById(R.id.qtyView)).getText().toString());
        String prompt = "Are you sure you would like to change Qty: " + ((EditText)Rec_Item.findViewById(R.id.qtyChange)).getText().toString();
        ((TextView)layout.findViewById(R.id.qtyToChange)).setText(prompt);

        float width =inventory.this.getResources().getDisplayMetrics().widthPixels;
        float height =inventory.this.getResources().getDisplayMetrics().heightPixels;
        pw = new PopupWindow(layout, (int)(width*0.8), (int)(height*0.8), true);
        pw.showAtLocation(layout, Gravity.CENTER, 0, 0);
    }


    public void change(View view){

        //Changes qty, sets qty to 0 if non-parsable as their is not strict type checking in this project
        View Rec_Item = (View) view.getParent().getParent();
        int change = Integer.parseInt(((TextView)pw.getContentView().findViewById(R.id.qtyToChange)).getText().toString()
                .split("Qty: ")[1]);
        int old_value;
        try{
            old_value = (Integer.parseInt(((TextView)pw.getContentView()
                    .findViewById(R.id.qtyPop)).getText().toString()));
        }catch (NumberFormatException e){
            old_value = 0;
        }


        //Inputs new qty to db with name and stk number
        int new_qty = old_value + change;
        String itemName = ((TextView)pw.getContentView().findViewById(R.id.item_headerPop)).getText().toString();
        String itemStkNum = ((TextView)pw.getContentView().findViewById(R.id.stkNumberPop)).getText().toString();
        itemStkNum = itemStkNum.replace("Stock #: ", "");

        long id_num = db.changeQty(itemName, itemStkNum, new_qty);

        //Shows appropriate toast message based on update
        if(id_num!=-1){
            Toast.makeText(getApplicationContext(), "Successfully Changed Item Qty!", Toast.LENGTH_SHORT).show();
            pw.dismiss();
        }
        else{
            Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            pw.dismiss();
        }

    }

    public void cancel(View view) {
        //Exits popup with no action
        pw.dismiss();
    }
}