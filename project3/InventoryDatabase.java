package com.zybooks.project3;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InventoryDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME = "Inventory.db";
    private static final int VERSION = 1;
    private static final int MIN_STOCK_LEVEL = 10;
    private int total_pulled = 0;

    public InventoryDatabase(Context context){
        super(context, DB_NAME, null, VERSION);
    }

    //Both tables necessary for application
    private static final class UserTable{
        private static final String TABLE = "Users";
        private static final String COL_ID = "_id";
        private static final String COL_ROLE = "User_Role";
        private static final String COL_NAME = "User_Name";
        private static final String COL_SUPER_NAME = "Supervisor_Name";
        private static final String COL_ORG = "Organization";
        private static final String COL_LOCATION = "Location";
        private static final String COL_MESSAGES = "Messages";
    }

    private static final class InventoryTable{
        private static final String TABLE = "Inventory";
        private static final String COL_ID = "_id";
        private static final String COL_ITEM_NAME = "Item_Name";
        private static final String COL_QTY = "Quantity";
        private static final String COL_PRIOR_TRANS = "Prior_Transactions";
        private static final String COL_STK_NUM = "Stock_Number";
        private static final String COL_PN = "Part_Number";
        private static final String COL_ITEM_LOCATION = "Item_Location";
        private static final String COL_UOI = "Unit_of_Issue";
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //Creates DB if it does not exist
        sqLiteDatabase.execSQL("create table " + UserTable.TABLE + " (" +
                UserTable.COL_ID + " text, " +
                UserTable.COL_ROLE + " text, " +
                UserTable.COL_NAME + " text, " +
                UserTable.COL_SUPER_NAME + " text, " +
                UserTable.COL_ORG + " text, " +
                UserTable.COL_LOCATION + " text, " +
                UserTable.COL_MESSAGES + " text)"
                );
        sqLiteDatabase.execSQL("create table " + InventoryTable.TABLE + " (" +
                InventoryTable.COL_ID + " integer primary key autoincrement, " +
                InventoryTable.COL_ITEM_NAME + " text, " +
                InventoryTable.COL_QTY + " text, " +
                InventoryTable.COL_PRIOR_TRANS + " text, " +
                InventoryTable.COL_STK_NUM + " text, " +
                InventoryTable.COL_PN + " text, " +
                InventoryTable.COL_ITEM_LOCATION + " text, " +
                InventoryTable.COL_UOI + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //For upgrading db
        sqLiteDatabase.execSQL("drop table if exists " + UserTable.TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean addUser(HashMap newUser){
        //Adds user to db
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserTable.COL_ID, newUser.get("id").toString());
        values.put(UserTable.COL_NAME, newUser.get("name").toString());
        values.put(UserTable.COL_ORG, newUser.get("org").toString());
        values.put(UserTable.COL_SUPER_NAME, newUser.get("super").toString());
        values.put(UserTable.COL_LOCATION, newUser.get("location").toString());
        values.put(UserTable.COL_ROLE, "User");
        values.put(UserTable.COL_MESSAGES, "");

        long insertResponse = db.insert(UserTable.TABLE, null, values);
        return (insertResponse != -1);
    }
    public HashMap getUser(String id){
        //Returns matching user and "logs in"
        HashMap<String, String> user = Login.user;
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + UserTable.TABLE + " where _id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id});
        if(cursor.moveToFirst()){
            user.replace("id", cursor.getString(0));
            user.replace("name", cursor.getString(2));
            user.replace("org", cursor.getString(4));
            user.replace("super", cursor.getString(3));
            user.replace("location", cursor.getString(5));
            user.replace("messages", cursor.getString(6));
        }
        else{
            return null;
        }
        cursor.close();
        return user;
    }

    public void updateInventoryQty(int id, int qtyChange){
        //Not used at moment see other change qty for current method
        //Update entry
        String id_s = String.valueOf(id);
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + InventoryTable.TABLE + " where _id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{id_s});
        int currentInv = 0;
        String itemName = "";
        String priorTransactions = "";
        if(cursor.moveToFirst()){
            currentInv = Integer.parseInt(cursor.getString(2));
            itemName = cursor.getString(1);
            priorTransactions = cursor.getString(3);
        }
        currentInv += qtyChange;

        ContentValues values = new ContentValues();
        values.put(InventoryTable.COL_QTY, Integer.toString(currentInv));
        if(priorTransactions.trim().isEmpty()){
            values.put(InventoryTable.COL_PRIOR_TRANS, itemName +" Qty Changed by " + qtyChange); //Would add date, timestamp and Employee ID in real world application
        }
        else{
            values.put(InventoryTable.COL_PRIOR_TRANS, priorTransactions + " \\| " + itemName +" Qty Changed by " + qtyChange);
        }


        db.update(InventoryTable.TABLE, values,"_id = ?",
                new String[]{id_s});

        //Send/Update Message if (below MIN STOCK constant) could send SMS but that seems excessive for project
        if(currentInv <= MIN_STOCK_LEVEL){
//            String[] messages = Login.user.get("messages").split("\\|");
//            String[] newMessages = new String[messages.length + 1];
//            System.arraycopy(messages, 0, newMessages, 0, messages.length);
//            newMessages[messages.length] = itemName + " has reached inventory level of " + Integer.toString(currentInv) + "!";
//            Login.user.replace("messages", String.join(" \\| ", newMessages));
            String messages;
            String message = itemName + " has reached inventory level of " + Integer.toString(currentInv) + "!";
            if(Login.user.get("messages").trim().isEmpty()){
                messages = message;
            }
            else{
                messages = Login.user.get("messages") + " \\| " + message;
            }
            Login.user.replace("messages", messages);
            //Sends text to cell phone
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("15555215554", null, message,
                    null,null);
        }

        //Update Inventory Recycler TODO

    }

    public boolean addInventoryItem(HashMap newItem){
        //Adds inventory item to table, requires item ID

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryTable.COL_ID, newItem.get("id").toString());
        values.put(InventoryTable.COL_ITEM_NAME, newItem.get("name").toString());
        values.put(InventoryTable.COL_QTY, newItem.get("qty").toString());
        values.put(InventoryTable.COL_PRIOR_TRANS, newItem.get("prior_trans").toString());
        values.put(InventoryTable.COL_STK_NUM, newItem.get("stock_num").toString());
        values.put(InventoryTable.COL_PN, newItem.get("part_num").toString());
        values.put(InventoryTable.COL_ITEM_LOCATION, newItem.get("item_loc").toString());
        values.put(InventoryTable.COL_UOI, newItem.get("UOI").toString());

        long insertResponse = db.insert(InventoryTable.TABLE, null, values);
        return (insertResponse != -1);
    }
    public long addInventoryItem(HashMap newItem, int unused){
        //Adds inventory item, no id required

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        //values.put(InventoryTable.COL_ID, );
        values.put(InventoryTable.COL_ITEM_NAME, newItem.get("name").toString());
        values.put(InventoryTable.COL_QTY, newItem.get("qty").toString());
        values.put(InventoryTable.COL_PRIOR_TRANS, newItem.get("prior_trans").toString());
        values.put(InventoryTable.COL_STK_NUM, newItem.get("stock_num").toString());
        values.put(InventoryTable.COL_PN, newItem.get("part_num").toString());
        values.put(InventoryTable.COL_ITEM_LOCATION, newItem.get("item_loc").toString());
        values.put(InventoryTable.COL_UOI, newItem.get("UOI").toString());

        long insertResponse = db.insert(InventoryTable.TABLE, null, values);
        return insertResponse;
    }

    public ArrayList<HashMap<String, String>> getInventoryItems(){
        //Default getter, not currently used
        int qty = 50 + total_pulled;
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + InventoryTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<HashMap<String, String>> returnArray = new ArrayList<>();

        if(cursor.moveToPosition(total_pulled)){
            do{
                InventoryItem item = new InventoryItem();
                item.Item.replace("id", cursor.getString(0));
                item.Item.replace("name", cursor.getString(1));
                item.Item.replace("qty", cursor.getString(2));
                item.Item.replace("prior_trans", cursor.getString(3));
                item.Item.replace("stock_num", cursor.getString(4));
                item.Item.replace("part_num", cursor.getString(5));
                item.Item.replace("item_loc", cursor.getString(6));
                item.Item.replace("UOI", cursor.getString(7));
                returnArray.add(item.Item);
                total_pulled++;
            } while(cursor.moveToNext() && total_pulled < qty);

        }

        return returnArray;
    }

    public ArrayList<HashMap<String, String>> getInventoryItems(int qtyToPull){
        //Allows user to specify qty of entries to get
        int qty = qtyToPull + total_pulled;
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from " + InventoryTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<HashMap<String, String>> returnArray = new ArrayList<>();

        if(cursor.moveToPosition(total_pulled)){
            do{
                InventoryItem item = new InventoryItem();
                item.Item.replace("id", cursor.getString(0));
                item.Item.replace("name", cursor.getString(1));
                item.Item.replace("qty", cursor.getString(2));
                item.Item.replace("prior_trans", cursor.getString(3));
                item.Item.replace("stock_num", cursor.getString(4));
                item.Item.replace("part_num", cursor.getString(5));
                item.Item.replace("item_loc", cursor.getString(6));
                item.Item.replace("UOI", cursor.getString(7));
                returnArray.add(item.Item);
                total_pulled++;
            } while(cursor.moveToNext() && total_pulled < qty);

        }

        return returnArray;
    }

    public boolean deleteItem(String name, String stk){
        //Deletes item from db table
        SQLiteDatabase db = getWritableDatabase();

        System.out.println("In Delete");

        int deleted = db.delete(InventoryTable.TABLE, InventoryTable.COL_ITEM_NAME + " = ? AND " + InventoryTable.COL_STK_NUM + " = ?", new String[] {name, stk});
        System.out.println(deleted);
        return (deleted >= 1);
    }

    public long changeQty(String name, String stk, int new_qty){
        //Changes qty with name and stk number
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InventoryTable.COL_QTY, Integer.toString(new_qty));

        long updated = db.update(InventoryTable.TABLE, values, InventoryTable.COL_ITEM_NAME + " = ? AND " + InventoryTable.COL_STK_NUM + " = ?", new String[] {name, stk});

        //Used to update user message screen and send text message
        if(new_qty < MIN_STOCK_LEVEL){
            String messages;
            String message =  name + " has reached inventory level of " + Integer.toString(new_qty) + "!";
            if(Login.user.get("messages").trim().isEmpty()){
                messages = message;
            }
            else{
                messages = Login.user.get("messages") + " \\| " + message;
            }
            Login.user.replace("messages", messages);

            //Sends text to cell phone
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("15555215554", null, message,
                    null,null);
        }

        return updated;
    }
}
