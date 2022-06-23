package com.zybooks.project3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

public class Message extends AppCompatActivity implements toolbarFunctionality{

    private final int REQUEST_SMS_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

//        //Array for testing only, uncomment if needed
//        Login.user.replace("messages", "Running out of toilet paper | " +
//                "New Supply received of wrenches | " +
//                "Low Wrenches < 5 | " +
//                "Hammer Quantity = 0");

        //Requests sms permission immediately if not granted
        requestSMS();
        String[] messagesArray = Login.user.get("messages").split("\\|");

        //Recycler View for messages
        RecyclerView RVmessages = (RecyclerView) findViewById(R.id.messageRecycler);
        MessageAdapter adapter = new MessageAdapter(messagesArray);
        RVmessages.setAdapter(adapter);
        RVmessages.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void onResume(){
        //Possibly not necessary given the complexity of application atm
        super.onResume();
        String[] messagesArray = Login.user.get("messages").split("\\|");
        RecyclerView RVmessages = (RecyclerView) findViewById(R.id.messageRecycler);
        MessageAdapter adapter = new MessageAdapter(messagesArray);
        RVmessages.setAdapter(adapter);
        RVmessages.setLayoutManager(new LinearLayoutManager(this));

    }

    private void requestSMS(){
        //Request permission if not obtained
       String SMS_permission = Manifest.permission.SEND_SMS;
       if(ContextCompat.checkSelfPermission(this, SMS_permission) !=
               PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(this,
                   new String[] { SMS_permission }, REQUEST_SMS_CODE);
       }
    }


    //Basic navigation
    @Override
    public void inventoryClicked(View view) {
        //TODO fix switch
        Intent intent = new Intent(Message.this, inventory.class);
        startActivity(intent);
    }

    @Override
    public void orderClicked(View view) {
        //TODO fix switch
        Intent intent = new Intent(Message.this, Message.class);
        startActivity(intent);
    }

    @Override
    public void addRemoveClicked(View view) {
        Intent intent = new Intent(Message.this, Add_Remove.class);
        startActivity(intent);
    }

    @Override
    public void logClicked(View view) {
        //TODO fix switch
        Intent intent = new Intent(Message.this, Message.class);
        startActivity(intent);
    }

    @Override
    public void messageClicked(View view) {
        Intent intent = new Intent(Message.this, Message.class);
        startActivity(intent);

    }
}