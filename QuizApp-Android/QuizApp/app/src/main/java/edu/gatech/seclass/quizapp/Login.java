package edu.gatech.seclass.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static edu.gatech.seclass.quizapp.MainActivity.currentUser;

import com.android.volley.AuthFailureError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private Button login;
    private Button forgot_password;
    private EditText username;
    private EditText password;
    private Button create;
    public static String ipa = "128.61.52.66"; // IP Address

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        login = (Button) findViewById(R.id.login_button);
        forgot_password = (Button) findViewById(R.id.login_forgot_pass_button);
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
    }

    @Override
    public void onBackPressed () {
        return;
    }

    public void resetPassword(View view) {
        if (view.getId() == forgot_password.getId()) {
            Intent intent = new Intent(Login.this, ForgetPassword.class);
            startActivity(intent);
        }
    }

    public void goToAccountCreation(View view) {
        Intent intent = new Intent(Login.this, CreateAccount.class);
        startActivity(intent);
    }

    //get user from db
    //validate if the password entered equals password put in
    //go to next screen if it does, if not, error message
    public void submitCredentials(View view) {
        boolean usernameBlank = username.getText().toString().length() == 0;
        boolean passwordBlank = password.getText().toString().length() == 0;

        if (usernameBlank || passwordBlank) {
            Context context = getApplicationContext();
            CharSequence text = "fields can't be blank";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if (view.getId() == login.getId()) {
            // update IP address of laptop here
            String url = "http://" + Login.ipa + ":3000/getUser/" + username.getText().toString();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("Yes you reached here.");
                    User userFromDB = JsonUtil.parseUser(response);
                    String enteredPass = password.getText().toString();
                    String passDB = userFromDB.getPassword();
                    if (enteredPass.equals(passDB)) {
                        currentUser = userFromDB;
                        Context context = getApplicationContext();
                        CharSequence text = "user " + username.getText().toString() + " credentials validated";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "password is wrong! try again";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("No you didn't reach here");
                    if (error.getClass().toString().equals("class com.android.volley.ParseError")) {
                        Context context = getApplicationContext();
                        CharSequence text = "user: " + username.getText().toString() + " not found";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        error.printStackTrace();
                    }

                }
            });

            SingletonRequestQueue.getInstance(this).getRequestQueue().getCache().clear();
            SingletonRequestQueue.getInstance(this).getRequestQueue().add(jsonObjectRequest);
        }
    }
}
