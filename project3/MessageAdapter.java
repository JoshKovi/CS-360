package com.zybooks.project3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    //Even more boiler plate than other two adapters

    private List<String> messages;

    public MessageAdapter(String[] input_Messages){
        messages = Arrays.asList(input_Messages);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View messageView = inflater.inflate(R.layout.message_row_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(messageView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String message = messages.get(position);

        TextView textView = holder.messageTextView;
        textView.setText(message);

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView messageTextView;

        public ViewHolder(View view){
            super(view);

            messageTextView = (TextView) view.findViewById(R.id.messageView);
        }
    }

}


