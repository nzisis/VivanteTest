package com.nzisis.vivantetest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.nzisis.vivantetest.Adapters.MainAdapter;
import com.nzisis.vivantetest.Utils.DividerItemDecoration;
import com.nzisis.vivantetest.Utils.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvItems;
    private MainAdapter adapter;
   // private ArrayList<LeaderBoardItem> items;
    private LinearLayout llLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Repositories");


        setSupportActionBar(toolbar);


        llLoading = (LinearLayout) findViewById(R.id.llLoading);

        rvItems = (RecyclerView) findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvItems.addItemDecoration(new DividerItemDecoration(getResources().getDrawable(R.drawable.divider), false, false));


        init();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void init(){


        //adapter = new MainAdapter(items,getActivity());
        //rvItems.setAdapter(adapter);

        loadData();

    }



    private void loadData(){


        if (!items.isEmpty()){
            llLoading.setVisibility(View.GONE);
        }else {
            llLoading.setVisibility(View.VISIBLE);
        }
        HttpsTrustManager.allowAllSSL();
        String url = "https://test.wappier.com/api/leaderboard/0";

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response", response.toString());
                for(int i=0; i<response.length(); i++){
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String avatar = jsonObject.getString("avatar");
                        String name = jsonObject.getString("name");
                        int points = jsonObject.getInt("points");
                        String star = jsonObject.getString("star_asset");
                        int rank = i+1;

                        LeaderBoardItem item = new LeaderBoardItem(name,avatar,points,star,rank);
                        items.add(item);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapter.notify(items);
                if (!items.isEmpty()){
                    llLoading.setVisibility(View.GONE);
                }else {
                    llLoading.setVisibility(View.VISIBLE);
                }

            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                String credentials = "test_me" + ":" + "G00dw11L";
                String encodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + encodedCredentials);

                return headers;
            }
        };


        AppController.getInstance().addToRequestQueue(request, "JSON_ARRAY");
    }


}
