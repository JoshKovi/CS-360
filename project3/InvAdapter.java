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

public class InvAdapter extends RecyclerView.Adapter<InvAdapter.ViewHolder> {

    //This class is a recycler view adapted, similar to ItemAdapter
    //Mostly boilerplate code, not anything interesting

    private ArrayList<HashMap<String, String>> items;

    public InvAdapter(ArrayList<HashMap<String, String>> input_items){
        items = input_items;
    }

    @NonNull
    @Override
    public InvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.inv_change_view, parent, false);

        InvAdapter.ViewHolder viewHolder = new InvAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InvAdapter.ViewHolder holder, int position) {
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
        format = item.get("qty");
        QtyView.setText(format);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemName, stkNum, PN, Location, UOI, QTY;
        public Button goButton;

        public ViewHolder(View view){
            super(view);

            itemName = view.findViewById(R.id.item_header);
            stkNum = view.findViewById(R.id.stkNumberView);
            PN = view.findViewById(R.id.partNumberView);
            Location = view.findViewById(R.id.locationView);
            UOI = view.findViewById(R.id.UOIView);
            QTY = view.findViewById(R.id.qtyView);
            goButton = view.findViewById(R.id.goButton);

        }
    }
}