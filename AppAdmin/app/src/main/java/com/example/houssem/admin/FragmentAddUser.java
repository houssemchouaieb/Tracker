package com.example.houssem.admin;

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


public class FragmentAddUser extends Fragment implements View.OnClickListener {
    private EditText register_username,register_password,register_email,phone_number;
    private Button register;
    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        View myView=inflater.inflate(R.layout.fragment_add_user, null);
        register_username = (EditText)myView.findViewById(R.id.register_username);
        register_email = (EditText)myView. findViewById(R.id.register_email);
        register_password=(EditText)myView.findViewById(R.id.register_password);
        phone_number=(EditText)myView.findViewById(R.id.phone_number);
        register = (Button)myView. findViewById(R.id.register_button);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Adding User...");

        register.setOnClickListener(this);return myView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Register User");
    }
    private void registerUser(){
        final String username = register_username.getText().toString().trim();
        final String password = register_password.getText().toString().trim();
        final String email=register_email.getText().toString().trim();
        final String number=phone_number.getText().toString().trim();
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ADD_USER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressDialog.dismiss();
                        try {

                            JSONObject obj = new JSONObject(response);
                            Toast.makeText(
                                    getActivity(),
                                    obj.getString("message"),
                                    Toast.LENGTH_LONG
                            ).show();
                            if(!obj.getBoolean("error")){
                                register_email.setText("");
                                register_password.setText("");
                                register_username.setText("");
                                phone_number.setText("");
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
                if((username.length()!=0)&&(password.length()!=0)&&(number.length()!=0)&&(email.length()!=0)){
                    params.put("username", username);
                    params.put("email", email);
                    params.put("password",password);
                    params.put("telephone",number);
                }
                return params;
            }

        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.register_button){
            registerUser();

        }
    }
}