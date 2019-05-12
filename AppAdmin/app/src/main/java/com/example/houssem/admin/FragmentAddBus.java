package com.example.houssem.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


public class FragmentAddBus extends Fragment implements View.OnClickListener {
    private EditText numero,ligne;
    private Button add;
    private ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        View myView=inflater.inflate(R.layout.fragment_add_bus, null);
        numero = (EditText)myView.findViewById(R.id.numero);
        ligne = (EditText)myView. findViewById(R.id.ligne);
        add = (Button)myView. findViewById(R.id.add_bus_button);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Adding Bus...");

        add.setOnClickListener(this);return myView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Add Bus");
    }
    private void addBus(){
        final String num = numero.getText().toString().trim();
        final String line = ligne.getText().toString().trim();
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_ADD_BUS,
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
                                numero.setText("");
                                ligne.setText("");
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
                if((num.length()!=0)&&(line.length()!=0)){
                    params.put("numero", num);
                    params.put("ligne", line);
                }
                return params;
            }

        };

        RequestHandler.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.add_bus_button){
            addBus();
        }
    }
}