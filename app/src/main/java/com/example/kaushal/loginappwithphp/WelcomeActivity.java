package com.example.kaushal.loginappwithphp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv1 = findViewById(R.id.textView4);
        tv2 = findViewById(R.id.textView5);

        Bundle bundle = getIntent().getExtras();

        String name = bundle.getString("name");
        String email = bundle.getString("email");

        tv1.setText(name);
        tv2.setText(email);
    }
}
