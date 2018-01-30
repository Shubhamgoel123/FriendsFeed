package com.example.kachucool.friendsfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NewsFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.nav_items,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId()==R.id.profile) {
            Intent i = new Intent(NewsFeedActivity.this, ProfileActivity.class);
            startActivity(i);
        }

        if(item.getItemId()==R.id.LogOut)
        {
            Db_activity db=new Db_activity(getApplication());
            db.open();
            db.delete();
            db.close();

            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
        }

        if(item.getItemId()==R.id.Post)
        {
            Intent i = new Intent(NewsFeedActivity.this, PostActivity.class);
            startActivity(i);
        }

        return true;
    }
}
