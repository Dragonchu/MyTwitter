package com.dragonchu.control.login;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shrey.task1sample.R;
import com.dragonchu.control.register.RegisterActivity;
import com.dragonchu.control.tweetlist.TweetsListActivity;
import com.dragonchu.database.PHPConnect;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class loginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextUsername,editTextPassword;
    private Button buttonLogin;
    private ProgressDialog progressDialog;
    private TextView goRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        goRegister =(TextView)findViewById(R.id.goRegister);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);

        progressDialog = new ProgressDialog(this);
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        buttonLogin.setOnClickListener(this);
    }


    private void registerUser(){
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        progressDialog.setMessage("Login ...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                PHPConnect.URL_LOGIN,
                new Response.Listener<String>() {
                    @SuppressLint("ResourceAsColor")
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("error").equals("true")){
                                editTextUsername.getText().clear();
                                editTextPassword.getText().clear();
                                editTextUsername.setHint(jsonObject.getString("message"));
                                editTextUsername.setHintTextColor(R.color.md_red_A700);
                            }else{
                                String username = jsonObject.getString("username");
                                String email = jsonObject.getString("email");
                                Intent intent = TweetsListActivity.newIntent(loginActivity.this,username,email);
                                Log.d("intent", "onResponse: "+username);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonLogin)
            registerUser();
    }


}
