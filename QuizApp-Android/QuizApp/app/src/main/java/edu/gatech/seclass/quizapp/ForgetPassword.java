package edu.gatech.seclass.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static edu.gatech.seclass.quizapp.MainActivity.currentUser;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

public class ForgetPassword extends AppCompatActivity {
    private Button submit;
    private Button back;
    private EditText new_password;
    private EditText new_password_repeat;
    private EditText new_username;
    private String old_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        // old_password = currentUser.password;

        back = (Button) findViewById(R.id.forgot_pass_back_button);
        submit = (Button) findViewById(R.id.submit_button_forgetpass);
        new_username = (EditText) findViewById(R.id.new_username);
        new_password = (EditText) findViewById(R.id.new_password);
        new_password_repeat = (EditText) findViewById(R.id.new_password_repeat);
    }

    // get the user's old password
    // make sure the userid is valid.
    // make sure the user's old password isn't equal to user's new password
    // make sure the password and re-entered password are the same.
    // and THEN update in db
    public void submitNewCredentials(View view){
        // make sure the password and re-entered password are the same.
        String newpass = new_password.getText().toString();
        String newpass_repeat = new_password_repeat.getText().toString();
        String newusername = new_username.getText().toString();

        boolean newpassBlank = newpass.length() == 0;
        boolean newpassRepeatBlank = newpass_repeat.length() == 0;
        boolean newusernameBlank = newusername.length() == 0;

        if (newpassBlank || newpassRepeatBlank || newusernameBlank) {
            Context context = getApplicationContext();
            CharSequence text = "fields can't be blank!";
            int duration = Toast.LENGTH_LONG;

            Toast toast;
            toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }

        if (!newpass.equals(newpass_repeat)) {
            Context context = getApplicationContext();
            CharSequence text = "passwords don't match!";
            int duration = Toast.LENGTH_LONG;

            Toast toast;
            toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            // make sure the userid is valid.
            // make sure the user's old password isn't equal to user's new password
            String url = "http://" + Login.ipa + ":3000/getUser/" + new_username.getText().toString();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    User userFromDB = JsonUtil.parseUser(response);
                    String oldPass = userFromDB.getPassword();

                    if (!newpass.equals(oldPass)) {
                        updateUserDBPassword(newpass);
                    } else {
                        Context context = getApplicationContext();
                        CharSequence text = "new password must be different from old password";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error.getClass().toString().equals("class com.android.volley.ParseError")) {
                        Context context = getApplicationContext();
                        CharSequence text = "user: "+ new_username.getText().toString()+ " not found";
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

    public void updateUserDBPassword(String newPass) {
        String url = "http:/" + Login.ipa + ":3000/updatePassword/" + new_username.getText().toString() + "/" + newPass;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Context context = getApplicationContext();
                CharSequence text = "password updated!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        SingletonRequestQueue.getInstance(this).getRequestQueue().getCache().clear();
        SingletonRequestQueue.getInstance(this).getRequestQueue().add(stringRequest);
    }

    public void BackToLogin(View view) {
        Intent intent = new Intent(ForgetPassword.this, Login.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed () {
        return;
    }
}

