package com.example.loginjson;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    EditText ed_1, ed_2;
    String user, pass;
    AlertDialog.Builder builder;
    String log_url = "http://shameed.000webhostapp.com/login_det.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed_1 = findViewById(R.id.enter_user);
        ed_2 = findViewById(R.id.enter_pass);
    }

    public void sign_reg(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void log_in(View view) {
        user = ed_1.getText().toString();
        pass = ed_2.getText().toString();
        builder = new AlertDialog.Builder(MainActivity.this);

        if (user.equals("") || pass.equals("")) {
            builder.setTitle("Something went wrong");
            displayAlert("Enter valid username and password..");
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, log_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        String code = jsonObject.getString("code");
//                        String usered = jsonObject.getString("user");
//                        String mobile = jsonObject.getString("mobile");
                        if (code.equals("Login failed..")) {
                            builder.setTitle("Login Error...");
                            displayAlert(jsonObject.getString("message"));
                        } else {
                            Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("username", jsonObject.getString("user"));
                            bundle.putString("mobile", jsonObject.getString("mobile"));
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Error in Response" + error, Toast.LENGTH_SHORT).show();
                    error.printStackTrace();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user", user);
                    params.put("pass", pass);
                    return params;
                }
            };
            Mysingleton.getMysingleton(MainActivity.this).addRequest(stringRequest);
        }
    }

    public void displayAlert(String message) {
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ed_1.setText("");
                ed_2.setText("");

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
