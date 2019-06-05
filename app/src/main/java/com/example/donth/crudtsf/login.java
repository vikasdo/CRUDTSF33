package com.example.donth.crudtsf;

import android.content.Intent;
import android.media.session.MediaSessionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {
EditText editText;
EditText password;
String id,email;
Session as;

   final static String Url="http://139.59.65.145:9090/user/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editText=(EditText)findViewById(R.id.editText3);
        password=(EditText)findViewById(R.id.editText2);
        Button btn=(Button)findViewById(R.id.button);
        as=new Session(getApplicationContext());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 getlogin();
            }
        });
    }

    private void getlogin() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String Email = editText.getText().toString();
        String pass = password.getText().toString();
        JSONObject user = new JSONObject();
        try {
            user.put("email", Email);
            user.put("password", pass);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,
                Url, user,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response-signUp", response.toString());
                        if (!response.isNull("status_message")) {
                            Toast.makeText(login.this, "Please Enter your correct credential or create new account", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(login.this, "Successfully login", Toast.LENGTH_SHORT).show();
                            try {
                               id  = response.getJSONObject("data").getString("id");
                                 email= response.getJSONObject("data").getString("email");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                          Toast.makeText(getApplicationContext(),id.toString(),Toast.LENGTH_LONG);
                            as.createLoginSession(id,email);
                            Intent intent = new Intent(getApplicationContext(), showpages.class);
                            startActivity(intent);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        });
        queue.add(request);

    }

}
