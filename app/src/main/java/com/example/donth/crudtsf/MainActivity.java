package com.example.donth.crudtsf;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.donth.crudtsf.R.id.button;


public class MainActivity extends AppCompatActivity {

    EditText userEmail;

    EditText password;

    RequestQueue queue;
    Button btn;
String Url="http://139.59.65.145:9090/user/signup";
    Button btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
           userEmail=(EditText)findViewById(R.id.STY);
           password=(EditText)findViewById(R.id.editText6);
        btn=(Button)findViewById(R.id.button4);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
getit();
            }
        });




    }

    private void getit() {

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            String Email = userEmail.getText().toString();
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
                            if (!response.isNull("data")) {

                                Intent intent = new Intent(getApplicationContext(), login.class);
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