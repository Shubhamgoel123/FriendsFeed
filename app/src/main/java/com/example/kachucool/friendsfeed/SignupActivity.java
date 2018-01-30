package com.example.kachucool.friendsfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText ed1,ed2,ed3,ed4,ed5;
    Button b;
    RequestQueue queue;
    int mStatusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        ed1=(EditText)findViewById(R.id.signup_name);
        ed2=(EditText)findViewById(R.id.signup_username);
        ed3=(EditText)findViewById(R.id.signup_email);
        ed4=(EditText)findViewById(R.id.signup_password);
        ed5=(EditText)findViewById(R.id.signup_confirmpassword);

        b=(Button)findViewById(R.id.signup);

        queue= Volley.newRequestQueue(getApplicationContext());
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.43.55:5000/users/register";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {

                                Db_activity db=new Db_activity(getApplication());
                                db.open();
                                db.entry(response);
                                db.close();


                                Intent i=new Intent(SignupActivity.this,NewsFeedActivity.class);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(SignupActivity.this,"Error "+mStatusCode,Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {

                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        mStatusCode = response.statusCode;
                        return super.parseNetworkResponse(response);
                    }

                    @Override
                    protected Map<String, String> getParams()
                    {
                        String name=ed1.getText().toString();
                        String username=ed2.getText().toString();
                        String email=ed3.getText().toString();
                        String password=ed4.getText().toString();
                        String confirmpassword=ed5.getText().toString();

                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("name", name);
                        params.put("username", username);
                        params.put("email", email);
                        params.put("password", password);
                        params.put("password2", confirmpassword);
                        return params;
                    }
                };
                queue.add(postRequest);

            }
        });
    }
}
