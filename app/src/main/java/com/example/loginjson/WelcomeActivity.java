package com.example.loginjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {
    TextView username,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        username=findViewById(R.id.username_set);
        mobile=findViewById(R.id.mobile_set);
        Bundle bundle=getIntent().getExtras();
        username.setText("Welcome " +bundle.getString("username"));
        mobile.setText("Your no " +bundle.getString("mobile"));

    }
}
