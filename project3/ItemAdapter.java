package com.zybooks.project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    //Almost identical to InvAdapter, also just boilerplate basic code

    private ArrayList<HashMap<String, String>> items;

    public ItemAdapter(ArrayList<HashMap<String, String>> input_items){
        items = input_items;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.item_view, parent, false);

        ItemAdapter.ViewHolder viewHolder = new ItemAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        HashMap<String, String> item = items.get(position);

        TextView nameView = holder.itemName;
        TextView stkView = holder.stkNum;
        TextView pnView = holder.PN;
        TextView locView = holder.Location;
        TextView UOIView = holder.UOI;
        TextView QtyView = holder.QTY;

        nameView.setText(item.get("name"));
        String format = "Stock #: " + item.get("stock_num");
        stkView.setText(format);
        format = "PN: " + item.get("part_num");
        pnView.setText(format);
        format = "Location: " + item.get("item_loc");
        locView.setText(format);
        format = "Unit of Issue: " + item.get("UOI");
        UOIView.setText(format);
        format = "QTY: " + item.get("qty");
        QtyView.setText(format);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemName, stkNum, PN, Location, UOI, QTY;
        public Button deleteButton;

        public ViewHolder(View view){
            super(view);

            itemName = view.findViewById(R.id.item_header);
            stkNum = view.findViewById(R.id.stkNumberView);
            PN = view.findViewById(R.id.partNumberView);
            Location = view.findViewById(R.id.locationView);
            UOI = view.findViewById(R.id.UOIView);
            QTY = view.findViewById(R.id.qtyView);
            deleteButton = view.findViewById(R.id.delete_item);

        }
    }
}
