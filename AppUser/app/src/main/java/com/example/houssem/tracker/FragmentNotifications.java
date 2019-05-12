package com.example.houssem.tracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;


public class FragmentNotifications extends Fragment implements View.OnClickListener{
    private EditText editTextName, editTextDate,editTextDescription;
    private Button button;
    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        if(SharedPrefManager.getInstance(getActivity()).isLoggedIn()){

            startActivity(new Intent(getActivity(),ProfileActivity.class));

        }
        View myView=inflater.inflate(R.layout.fragment_notifications, null);
        editTextName = (EditText)myView.findViewById(R.id.rec_name);
        editTextDate = (EditText)myView. findViewById(R.id.rec_date);
        editTextDescription = (EditText)myView. findViewById(R.id.rec_description);
        button = (Button)myView. findViewById(R.id.rec_submit);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");

        button.setOnClickListener(this);return myView;
    }
    private void addRec(){
        final String name = editTextName.getText().toString().trim();
        final String date = editTextDate.getText().toString().trim();
        final String description = editTextDescription.getText().toString().trim();
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_REC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {

                            JSONObject obj = new JSONObject(response);
                            if(!obj.getBoolean("error")){
                                editTextDate.setText("");
                                editTextName.setText("");
                                editTextDescription.setText("");
                            }else{
                                Toast.makeText(
                                        getActivity(),
                                        obj.getString("message"),
                                        Toast.LENGTH_LONG
                                ).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                        Toast.makeText(
                                getActivity(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("date", date);
                params.put("description",description);
                return params;
            }

        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if(view == button){
            addRec();
        }
    }
}