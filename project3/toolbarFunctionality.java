package com.zybooks.project3;


import android.content.Context;
import android.content.Intent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public interface toolbarFunctionality {
    //Used to force activities to implement navigation

    void inventoryClicked(View view);
        //TODO add Inventory switcher
    void orderClicked(View view);
        //TODO add order switcher
    void addRemoveClicked(View view);
        //TODO add add/remove switcher
    void logClicked(View view);
        //TODO add log switcher
    void messageClicked(View view);
        //TODO add message switcher

}
