package com.example.kaushal.loginappwithphp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView tv1;
    EditText et1,et2;
    Button b;

    AlertDialog.Builder builder;
    RequestQueue queue;

    String url = "http://192.168.1.56:8080/Login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tv1 = findViewById(R.id.textView3);
        et1 = findViewById(R.id.editText5);
        et2 = findViewById(R.id.editText7);
        b = findViewById(R.id.button2);

        builder = new AlertDialog.Builder(LoginActivity.this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = et1.getText().toString().trim();
                final String pass = et2.getText().toString().trim();

                if (email.equals("")||pass.equals("")){

                    builder.setTitle("Something Wrong...").setMessage("Fill All Blank Field");
                    displayAlert("input_error");

                }else {

                    Log.d("aa","Enter");
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                        @Override
                        public void onResponse(String response) {

                            Log.d("aa","Enter");
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String code = jsonObject.getString("code");
                                Log.d("aa",code);
                                if (code.equals("login fail")){

                                    builder.setTitle("Login Failed..").setMessage(jsonObject.getString("message"));
                                    displayAlert("login fail");
                                }
                                else {

                                    if (code.equals("login success")){

                                        Intent intent = new Intent(LoginActivity.this,WelcomeActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("name",jsonObject.getString("name"));
                                        bundle.putString("email",jsonObject.getString("email"));
                                        intent.putExtras(bundle);
                                        startActivity(intent);
                                    }
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {


                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> param = new HashMap<>();
                            param.put("em",email);
                            param.put("ps",pass);

                            return param;
                        }
                    };

                    MySingleton.getInstance(LoginActivity.this).addRequestqueue(request);
                }
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationAct.class));
            }
        });
    }

    private void displayAlert(final String err) {

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (err.equals("input_error")){

                }

                if (err.equals("login fail")) {

                    et1.setText("");
                    et2.setText("");
                }
            }
        }).show();

    }
}
