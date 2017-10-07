package com.example.kaushal.loginappwithphp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistrationAct extends AppCompatActivity {

    EditText et1,et2,et3,et4;
    Button b;
    AlertDialog.Builder builder;
    String un,em,ps,cps;
    String url = "http://192.168.1.56:8080/Register.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        b = findViewById(R.id.button);
        et1 = findViewById(R.id.editText);
        et2 = findViewById(R.id.editText2);
        et3 = findViewById(R.id.editText3);
        et4 = findViewById(R.id.editText4);



        builder = new AlertDialog.Builder(this);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                un = et1.getText().toString().trim();
                em = et2.getText().toString().trim();
                ps = et3.getText().toString().trim();
                cps = et4.getText().toString().trim();

                if (un.equals("")||em.equals("")||ps.equals("")||cps.equals("")){

                    builder.setTitle("Something is Wrong..").setMessage("Fill All Fields");
                    displayAlert("input_error");

                }else {

                    if (!(ps.equals(cps))){

                        builder.setTitle("Something is Wrong..").setMessage("Confirm password does not match..");
                        displayAlert("input_error");

                    }else {

                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONArray jsonArray = new JSONArray(response);
                                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                                    String code = jsonObject.getString("code");
                                    String msg = jsonObject.getString("message");
                                    builder.setTitle("Server Response");
                                    builder.setMessage(msg);
                                    displayAlert(code);
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

                                Map<String,String> map = new HashMap<>();
                                map.put("un",un);
                                map.put("em",em);
                                map.put("ps",ps);

                                return map;
                            }
                        };

                        MySingleton.getInstance(RegistrationAct.this).addRequestqueue(request);
                    }
                }
            }
        });
    }

    private void displayAlert(final String err) {

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (err.equals("input_error")){

                    et3.setText("");
                    et4.setText("");
                }else if (err.equals("Reg. Success")){

                    finish();
                }else if (err.equals("reg. failed")){

                    et1.setText("");
                    et2.setText("");
                    et3.setText("");
                    et4.setText("");
                }
            }
        }).show();
    }
}
