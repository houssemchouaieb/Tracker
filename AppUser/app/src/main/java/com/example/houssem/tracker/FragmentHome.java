package com.example.houssem.tracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
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


public class FragmentHome extends Fragment implements View.OnClickListener {
    private Button submit;
    private EditText numero;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View myView=inflater.inflate(R.layout.fragment_home, null);
        submit=(Button)myView.findViewById(R.id.button);
        numero=(EditText)myView.findViewById(R.id.numero_home);
        submit.setOnClickListener(this);
        return myView;
    }
    private void versMaps(){


        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                Constants.URL_GET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            if(!jsonObject.getBoolean("error")){
                                String longi=jsonObject.getString("longi");
                                String lati=jsonObject.getString("lati");
                                Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query="+lati+","+longi);
                                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                mapIntent.setPackage("com.google.android.apps.maps");
                                startActivity(mapIntent);
                            }else{
                                Toast.makeText(
                                        getActivity(),
                                        jsonObject.getString("message"),
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

                Map<String,String> params =new HashMap<>();
                params.put("numcar",numero.getText().toString());
                return params;
            }
        };
        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);

    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button){
            if(numero.getText().toString().length()!=0){
                versMaps();
            }else{
                Toast.makeText(getActivity(),"Please enter Bus Number",Toast.LENGTH_LONG).show();
            }
        }
    }
}