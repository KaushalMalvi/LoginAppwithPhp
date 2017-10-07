package com.example.kaushal.loginappwithphp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Admin on 9/18/2017.
 */

public class MySingleton {

    public static MySingleton mInstance;
    public RequestQueue queue;
    public Context context;

    public MySingleton(Context context){

        this.context = context;
        queue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){

        if (queue==null)
            queue = Volley.newRequestQueue(context.getApplicationContext());

        return queue;
    }

    public static synchronized MySingleton getInstance(Context context){

        if (mInstance==null){

            mInstance = new MySingleton(context);
        }

        return mInstance;
    }


    public <T> void addRequestqueue(Request<T> request){

        queue.add(request);
    }
}
