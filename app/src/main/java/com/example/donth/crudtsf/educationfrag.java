package com.example.donth.crudtsf;
import android.content.Intent;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.constraint.Group;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;


        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.HashMap;

        import butterknife.BindView;
        import butterknife.ButterKnife;

public class educationfrag extends Fragment {

    @BindView(R.id.add_edu_details_button)
    Button mAddDetailsButton;
    @BindView(R.id.edit_edu_details_button)
    Button mEditDetailsButton;
    @BindView(R.id.all_edu_fields_groups)
    Group mFieldsGroup;
    @BindView(R.id.edu_empty_text_view)
    TextView mEmptyTextView;
    @BindView(R.id.university_name_edu_text_view)
    TextView mUniversityName;
    @BindView(R.id.stream_name_edu_text_view)
    TextView mStreamName;
    @BindView(R.id.city_name_edu_text_view)
    TextView mCityName;
    @BindView(R.id.start_date_edu_text_view)
    TextView mStartDate;
    @BindView(R.id.end_date_edu_text_view)
    TextView mEndDate;
    @BindView(R.id.delete_edu_details_button)
    Button mDeleteButton;
    @BindView(R.id.edu_del_edit_button_groups)
    Group mButtonGroups;
    private Session s;
    private VolleySingleton mVolleySingleton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_educationdetails, container, false);
        ButterKnife.bind(this, view);

        s = new Session(getContext());

        //Getting userId and UserEmail from shared preference and check for login functionality
        HashMap<String, String> userData;
        userData = s.getUserDetails();
        final String id = userData.get(s.KEY_USERID);
        final String deleteId = userData.get(s.KEY_DELETE_ID);
        //check whether the given user is logged in or not
        if (!s.isLoggedIn()) {
            Intent intent = new Intent(getContext(), login.class);
            startActivity(intent);
        } else {
            editUserData(id);
        }


        mEditDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), keducationdetailsactivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_id", id);
                bundle.putString("flag", "Edit");
                bundle.putString("university_name", mUniversityName.getText().toString());
                bundle.putString("stream_name", mStreamName.getText().toString());
                bundle.putString("city_name", mCityName.getText().toString());
                bundle.putString("start_date", mStartDate.getText().toString());
                bundle.putString("end_date", mEndDate.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mAddDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), keducationdetailsactivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("user_id", id);
                bundle.putString("flag", "Add");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Log.i("Deleted Id", "" + deleteId);
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEducationalDetails(deleteId);
            }
        });
        return view;
    }

    private void deleteEducationalDetails(String id) {
        String mDeleteUrl = "http://139.59.65.145:9090//user/educationdetail/";
        mVolleySingleton = VolleySingleton.getInstance(getContext());
        final RequestQueue requestQueue = mVolleySingleton.getRequestQueue();
        requestQueue.start();

        mDeleteUrl += id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, mDeleteUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("educational-details", response.toString());
                if (!response.isNull("status_message")) {
                    Toast.makeText(getContext(), "Delete Data Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), showpages.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }

    /***
     * This method is used to retrieve data from the server and preset onto user profile
     * @param id of the user
     */
    private void editUserData(String id) {
        String mUrl = "http://139.59.65.145:9090//user/educationdetail/";
        mVolleySingleton = VolleySingleton.getInstance(getContext());
        final RequestQueue requestQueue = mVolleySingleton.getRequestQueue();
        requestQueue.start();

        mUrl += id;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("educational-details", response.toString());
                if (response.isNull("data")) {
                    mFieldsGroup.setVisibility(View.GONE);
                    mEmptyTextView.setVisibility(View.VISIBLE);
                    mAddDetailsButton.setVisibility(View.VISIBLE);
                    mButtonGroups.setVisibility(View.GONE);
                } else {
                    mFieldsGroup.setVisibility(View.VISIBLE);
                    mEmptyTextView.setVisibility(View.GONE);
                    mAddDetailsButton.setVisibility(View.GONE);
                    mButtonGroups.setVisibility(View.VISIBLE);

                    try {
                        mUniversityName.setText(response.getJSONObject("data").getString("organisation"));
                        mStreamName.setText(response.getJSONObject("data").getString("degree"));
                        mCityName.setText(response.getJSONObject("data").getString("location"));
                        mStartDate.setText(response.getJSONObject("data").getString("start_year"));
                        mEndDate.setText(response.getJSONObject("data").getString("end_year"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(request);
    }
}
