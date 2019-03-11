package com.example.loginjson;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Mysingleton {

    private static Mysingleton mysingleton;
    private RequestQueue requestQueue;
    private static Context mctx;

    private Mysingleton(Context context){
        mctx=context;
        requestQueue=getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(mctx.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized Mysingleton getMysingleton(Context context){
        if (mysingleton==null){
            mysingleton=new Mysingleton(context);
        }
        return mysingleton;
    }
    public<R> void addRequest(Request<R> request){
        requestQueue.add(request);

    }
}
