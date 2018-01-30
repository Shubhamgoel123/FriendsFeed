package com.example.kachucool.friendsfeed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {
    EditText ed1,ed2,ed3;
    Button b;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ed1=(EditText)findViewById(R.id.postd);


        Db_activity db=new Db_activity(getApplication());
        db.open();
        final String id=db.getid();
        db.close();

        b=(Button)findViewById(R.id.postbutt);

        queue= Volley.newRequestQueue(getApplicationContext());

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.43.55:5000/post";
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {


                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                ) {

                    @Override
                    protected Map<String, String> getParams()
                    {
                        String postd=ed1.getText().toString();
                        String likes="";

                        System.out.println(postd+likes+id);
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("postd", postd);
                        params.put("likes", likes);
                        params.put("use_id",id);
                        return params;
                    }
                };

                postRequest.setRetryPolicy(new DefaultRetryPolicy(20*1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(postRequest);

            }
        });
    }
}
