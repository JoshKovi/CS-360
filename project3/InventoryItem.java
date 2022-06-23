package com.zybooks.project3;

import java.util.ArrayList;
import java.util.HashMap;


public class InventoryItem {

    //This was easier than building out a full class structure this time
    public HashMap<String, String> Item = new HashMap<>();
    public ArrayList<HashMap<String, String>> DatabaseItems = new ArrayList<>();


    public InventoryItem(){
        //Builds Empty Item Dictionary
        Item.put("id", "");
        Item.put("name", "");
        Item.put("qty", "");
        Item.put("prior_trans", "");
        Item.put("stock_num", "");
        Item.put("part_num", "");
        Item.put("item_loc", "");
        Item.put("UOI", "");
    }
    public InventoryItem(HashMap<String, String> dbItem){
        //Builds completed Item Dictionary
        Item.put("id", dbItem.get("id"));
        Item.put("name", dbItem.get("name"));
        Item.put("qty", dbItem.get("qty"));
        Item.put("prior_trans", dbItem.get("prior_trans"));
        Item.put("stock_num", dbItem.get("stock_num"));
        Item.put("part_num", dbItem.get("part_num"));
        Item.put("item_loc", dbItem.get("item_loc"));
        Item.put("UOI", dbItem.get("UOI"));
    }

    public InventoryItem(ArrayList<HashMap<String, String>> dbItems){
        //Adds dbItems to Arraylist
        for(int i = 0; i < dbItems.size(); i++){
            DatabaseItems.add(dbItems.get(i));
        }
    }

}
