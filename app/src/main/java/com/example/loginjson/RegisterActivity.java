package com.example.loginjson;

import android.app.DownloadManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class RegisterActivity extends AppCompatActivity {
    public String user, mobile, password, confirm;
    private EditText ed_user, ed_mobile, ed_pass, ed_con;
    AlertDialog.Builder builder;
    private String insert = "http://shameed.000webhostapp.com/retrive.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed_user = findViewById(R.id.ins_user);
        ed_mobile = findViewById(R.id.ins_mob);
        ed_pass = findViewById(R.id.ins_pass);
        ed_con = findViewById(R.id.ins_conpas);
    }

    public void sign_insert(View view) {
        user = ed_user.getText().toString();
        mobile = ed_mobile.getText().toString();
        password = ed_pass.getText().toString();
        confirm = ed_con.getText().toString();
        builder = new AlertDialog.Builder(RegisterActivity.this);
        if (user.equals("") || mobile.equals("") || password.equals("") || confirm.equals("")) {
            builder.setTitle("Something went wrong...");
            builder.setMessage("Fill all the fields..");
            displayAlert("input_text");

        }
        else {
            if (!password.equals(confirm)){
                builder.setTitle("Something went wrong...");
                builder.setMessage("Check your password...");
                displayAlert("input_text");

            }
            else {
                StringRequest stringRequest=new StringRequest(Request.Method.POST, insert, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject=jsonArray.getJSONObject(0);
                            String code=jsonObject.getString("code");
                            String message=jsonObject.getString("message");
                            builder.setTitle("Server Response...");
                            builder.setMessage(message);
                            displayAlert(code);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("sha",error.toString());
                        error.printStackTrace();

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> params=new HashMap<String, String>();
                        params.put("user",user);
                        params.put("pass",password);
                        params.put("mobile",mobile);
                        return params;
                    }

                };
                Mysingleton.getMysingleton(RegisterActivity.this).addRequest(stringRequest);
            }

        }

    }

    public void displayAlert(final String code) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (code.equals("input_text")) {
                    ed_pass.setText("");
                    ed_con.setText("");

                }
                else if(code.equals("Register failed")){
                    ed_user.setText("");
                    ed_pass.setText("");
                    ed_con.setText("");
                    ed_mobile.setText("");
                }
                else if(code.equals("Register Successfull")){
                    finish();


                }
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();

    }
}
