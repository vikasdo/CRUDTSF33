package com.example.donth.crudtsf;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;




import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

    public class keducationdetailsactivity extends AppCompatActivity {

        @BindView(R.id.university_name_edu_edit_text)
        EditText mUniversityName;
        @BindView(R.id.stream_name_edu_edit_text)
        EditText mStreamName;
        @BindView(R.id.city_name_edu_edit_text)
        EditText mCityName;
        @BindView(R.id.start_date_edu_edit_text)
        EditText mStartDate;
        @BindView(R.id.end_date_edu_edit_text)
        EditText mEndDate;
        @BindView(R.id.save_edu_details_button)
        Button mSaveButton;
        private String mUrl = "http://139.59.65.145:9090//user/educationdetail/";
        private Session s;
        private List<dataedu> mData;
        private VolleySingleton mVolleySingleton;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_keducationdetailsactivity);
            ButterKnife.bind(this);

            s = new Session(this);
            mData = new ArrayList<>();

            Bundle bundle = getIntent().getExtras();
            final String id = bundle.getString("user_id");
            final String flag = bundle.getString("flag");
            String universityName = bundle.getString("university_name");
            String streamName = bundle.getString("stream_name");
            String cityName = bundle.getString("city_name");
            String startDate = bundle.getString("start_date");
            String endDate = bundle.getString("end_date");


            if (flag.equals("Add")) {
                mUniversityName.setText("");
                mStreamName.setText("");
                mCityName.setText("");
                mStartDate.setText("");
                mEndDate.setText("");
                getSupportActionBar().setTitle(flag + " Education Data");
            } else if (flag.equals("Edit")) {
                mUniversityName.setText(universityName);
                mStreamName.setText(streamName);
                mCityName.setText(cityName);
                mStartDate.setText(startDate);
                mEndDate.setText(endDate);
                getSupportActionBar().setTitle(flag + " Education Data");
            }

            mSaveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setNewVolleyRequest();
                    if (flag.equals("Add")) {
                        setNewData(id, Request.Method.POST);
                    } else if (flag.equals("Edit")) {
                        setNewData(id, Request.Method.PUT);
                    }
                }
            });

        }

        /***
         * Setting new VolleyRequest and create new RequestQueue object
         * for both login details and signUp details
         */
        private void setNewVolleyRequest() {
            mVolleySingleton = VolleySingleton.getInstance(this);
            Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
            Network network = new BasicNetwork(new HurlStack());
            final RequestQueue requestQueue = mVolleySingleton.getRequestQueue(cache, network);
            requestQueue.start();
        }

        private void setNewData(String id, int method) {
            setUserData();
            mUrl += id;
            JSONObject userCredential = new JSONObject();
            try {
                userCredential.put("organisation", mData.get(0).getUniversityName());
                userCredential.put("degree", mData.get(0).getStreamName());
                userCredential.put("location", mData.get(0).getCityName());
                userCredential.put("start_year", mData.get(0).getStartYear());
                userCredential.put("end_year", mData.get(0).getEndYear());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest request = new JsonObjectRequest(method,
                    mUrl, userCredential,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("Response-edu-info", response.toString());
                            Toast.makeText(getApplicationContext(), "Successfully Saved new Data", Toast.LENGTH_SHORT).show();
                            String id = null;
                            try {
                                id = response.getJSONObject("data").getString("id");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            s.createDeleteId(id);
                            Intent intent = new Intent(getApplicationContext(), showpages.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.toString());
                }
            });

            mVolleySingleton.addToRequestQueue(request);
        }

        private void setUserData() {
            String universityName = mUniversityName.getText().toString().trim();
            String streamName = mStreamName.getText().toString().trim();
            String cityName = mCityName.getText().toString().trim();
            String startDate = mStartDate.getText().toString().trim();
            String endDate = mEndDate.getText().toString().trim();

            mData.add(new dataedu(universityName, streamName, cityName, startDate, endDate));
        }

    }


