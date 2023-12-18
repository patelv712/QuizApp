package edu.gatech.seclass.quizapp;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

//Wrapper class for RequestQueue so that we can maintain a global instance throughout the lifetime of the app
//made following tutorial at https://google.github.io/volley/
public class SingletonRequestQueue {
    private static SingletonRequestQueue singletonInstance;
    private static RequestQueue requestQueue;
    private static Context context;

    private SingletonRequestQueue(Context context) {
        this.context = context;
        this.requestQueue = getRequestQueue();
    }

    public static SingletonRequestQueue getInstance(Context context) {
        if (singletonInstance == null) {
            singletonInstance = new SingletonRequestQueue(context);
        }
        return singletonInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
