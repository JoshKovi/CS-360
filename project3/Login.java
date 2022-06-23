package com.zybooks.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class Login extends AppCompatActivity {

    private EditText userName, password, employeeName, orgName, superName, location;
    private Button signup;
    public static HashMap<String, String> user  = new HashMap<>();
    public InventoryDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //All edit text fields assigned at create
        userName = (EditText) findViewById(R.id.userNameEntry);
        password = (EditText) findViewById(R.id.passwordEntry);
        employeeName = (EditText) findViewById(R.id.employeeNameEntry);
        orgName = (EditText) findViewById(R.id.orgEntry);
        superName = (EditText) findViewById(R.id.supervisorNameEntry);
        location = (EditText) findViewById(R.id.locationEntry);
        signup = (Button) findViewById(R.id.signUp);

        //Initiates db and builds empty user
        db = new InventoryDatabase(getApplicationContext());
        buildUser();
    }

    public void buildUser(){
        //Empty User Hashmap
        user.put("id", "");
        user.put("name", "");
        user.put("org", "");
        user.put("super", "");
        user.put("location", "");
        user.put("messages", "");

    }

    public void loginClicked(View view) {
        //Takes the hash value of username and password and passes it to db to check
        //if user exists.
        String converted = hasher();

        //This is definitly not the safest/most secure way, but once again it
        // is adequate for the project
        HashMap<String, String> temp_user = db.getUser(converted);

        //If user hash matchs user is logged in, else toast displayed
        if(temp_user != null){
            Toast.makeText(getApplicationContext(), "Account Found!",
                    Toast.LENGTH_SHORT).show();
            user = temp_user;
            Intent intent = new Intent(Login.this, Message.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Account Not Found!",
                    Toast.LENGTH_SHORT).show();

        }


    }

    public void createClicked(View view) {
        //Change Visibility of additional fields, change button to signup, change onclick to signup
        employeeName.setVisibility(View.VISIBLE);
        orgName.setVisibility(View.VISIBLE);
        superName.setVisibility(View.VISIBLE);
        location.setVisibility(View.VISIBLE);
        signup.setText(R.string.signup_Button);
        signup.setOnClickListener(this::signupClicked);
    }

    public void signupClicked(View view){
        //Pulls the Values of all fields, not checked in this project
        //But in realworld application these would need to be checked for validity
        String employeeName_S = employeeName.getText().toString();
        String orgName_S = orgName.getText().toString();
        String superName_S = superName.getText().toString();
        String location_S = location.getText().toString();

        String converted = hasher();

        //This section retrieves user rolls after validation

        user.replace("id", converted);
        user.replace("name", employeeName_S);
        user.replace("org", orgName_S);
        user.replace("super", superName_S);
        user.replace("location", location_S);

        if(db.addUser(user)){
            Toast.makeText(getApplicationContext(), "Account Creation Successful!",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login.this, Message.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(getApplicationContext(), "Account Creation Failed!",
                    Toast.LENGTH_SHORT).show();
        }



    }

    public String hasher(){
        //Use SHA-256 Encryption on username+password, then pass to database for checking

        //This section combines username and password then hashes them to SHA-256
        //Maybe not the best approach but is adequate for this project, not checked in this project
        //But in realworld application these values would need to be checked for validity
        String user = userName.getText().toString();
        String pass = password.getText().toString();
        //Username and Password are only mandatory fields at this time
        if(user.trim().isEmpty() || pass.trim().isEmpty()){
            if(user.trim().isEmpty()) {
                userName.setHintTextColor(getColorStateList(R.color.invalid_red));
            }
            if(user.trim().isEmpty()) {
                password.setHintTextColor(getColorStateList(R.color.invalid_red));
            }
            return "";
        }

        String combined = user + pass;
        MessageDigest digest = null;
        try{
            digest = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e){
            return "";
        }
        digest.reset();
        byte[] digested = digest.digest(combined.getBytes());
        String converted = String.format("%0" + (digested.length*2) + "X", new BigInteger(1, digested));
        return converted;
    }
}