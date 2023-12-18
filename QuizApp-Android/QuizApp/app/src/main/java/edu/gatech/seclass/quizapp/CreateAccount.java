package edu.gatech.seclass.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static edu.gatech.seclass.quizapp.MainActivity.currentUser;

import com.android.volley.AuthFailureError;
import com.android.volley.ClientError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateAccount extends AppCompatActivity  {
    private Button create;
    private Button browse;
    private Button login;
    private EditText name;
    private EditText password;
    private EditText username;
    private EditText email;
    private ImageView pfp;
    private static final int RQS_OPEN_IMAGE = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // get all of the ids
        login = (Button) findViewById(R.id.create_account_login_button);
        create = (Button) findViewById(R.id.create_account_button);
        username = (EditText) findViewById(R.id.create_account_username);
        password = (EditText) findViewById(R.id.create_account_password);
        name = (EditText)  findViewById(R.id.create_account_name);
        email = (EditText) findViewById(R.id.create_account_email);
        pfp = (ImageView) findViewById(R.id.profile_picture);
        browse = (Button) findViewById(R.id.create_account_browse);

        browse.setOnClickListener(browseOnClickListener);

    }

    View.OnClickListener browseOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");

            startActivityForResult(intent, RQS_OPEN_IMAGE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RQS_OPEN_IMAGE) {

                pfp.setImageBitmap(null);
                Uri mediaUri = data.getData();
                String mediaPath = mediaUri.getPath();

                //display the image
                try {
                    InputStream inputStream = getBaseContext().getContentResolver().openInputStream(mediaUri);
                    Bitmap bm = BitmapFactory.decodeStream(inputStream);
                    pfp.setImageBitmap(bm);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed () {
        return;
    }

    public void createAccount (View view) {
        //send user confirmation email
        String[] TO = {email.getText().toString()};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Quiz App Registration");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email confirmation for Quiz App account creation and credentials are: " + "name is " + name.getText().toString() + " username is " + username.getText().toString() + " password is " + password.getText().toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Toast.makeText(CreateAccount.this,"Email sent", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CreateAccount.this,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }

        // check if userId, name, password are blank
        boolean usernameBlank = username.getText().toString().length()==0;
        boolean nameBlank = name.getText().toString().length()==0;
        boolean passwordBlank = password.getText().toString().length()==0;

        if (usernameBlank || nameBlank || passwordBlank) {
            Context context = getApplicationContext();
            CharSequence text = "Username, password, name can't be blank";
            int duration = Toast.LENGTH_LONG;

            Toast toast;
            toast = Toast.makeText(context, text, duration);
            toast.show();
        } else {
            User user  = new User();
            user.setPassword(password.getText().toString());
            user.setUserID(username.getText().toString());
            user.setName(name.getText().toString());

            final JSONObject reqBody = JsonUtil.convertUser(user);
            // change endpoint
            String url = "http://" + Login.ipa + ":3000/recordUser";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Context context = getApplicationContext();
                    CharSequence text = "Congrats on making an account " + user.getName() + "!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast;
                    toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(error.networkResponse.statusCode == 400) {
                        Context context = getApplicationContext();
                        CharSequence text = user.getUserID() + " is an existing username!";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast;
                        toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else{
                        error.printStackTrace();
                    }
                }
            }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return reqBody.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
            };

            SingletonRequestQueue.getInstance(this).getRequestQueue().getCache().clear();
            SingletonRequestQueue.getInstance(this).getRequestQueue().add(stringRequest);
        }
    }

    public void BackToLogin(View view) {
        Intent intent = new Intent(CreateAccount.this, Login.class);
        startActivity(intent);
    }
}


