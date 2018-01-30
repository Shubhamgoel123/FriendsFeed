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

public class MainActivity extends AppCompatActivity {

    EditText ed1,ed2;
    Button b1,b2,b3;
    RequestQueue queue;
    int mStatusCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed1=(EditText)findViewById(R.id.login_username);
        ed2=(EditText)findViewById(R.id.login_password);

        b1=(Button)findViewById(R.id.signin);
        b2=(Button)findViewById(R.id.signup);
        queue= Volley.newRequestQueue(getApplicationContext());
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.43.55:5000/users/login";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {

                                Db_activity db=new Db_activity(getApplication());
                                db.open();
                                db.entry(response);
                                db.close();

                                Intent intent=new Intent(MainActivity.this,NewsFeedActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(MainActivity.this,"Error"+mStatusCode,Toast.LENGTH_SHORT).show();
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
                        String username=ed1.getText().toString();
                        String password=ed2.getText().toString();

                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("username", username);
                        params.put("password", password);
                        return params;
                    }
                };
                queue.add(postRequest);

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,SignupActivity.class);
                startActivity(i);
            }
        });

    }
}
