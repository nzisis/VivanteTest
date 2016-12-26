package com.nzisis.vivantetest.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nzisis.vivantetest.Adapters.MainAdapter;
import com.nzisis.vivantetest.Listeners.MainListener;
import com.nzisis.vivantetest.Model.RealmDatabase;
import com.nzisis.vivantetest.Model.Repository;
import com.nzisis.vivantetest.R;
import com.nzisis.vivantetest.Utils.AppController;
import com.nzisis.vivantetest.Utils.DividerItemDecoration;
import com.nzisis.vivantetest.Utils.HttpsTrustManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView rvItems;
    private MainAdapter adapter;
    private ArrayList<Repository> repositories;
    private LinearLayout llLoading;
    private RealmDatabase db;
    private String syesterday;

    private AlertDialog dialog;


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

       AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        final EditText edittext = new EditText(MainActivity.this);
        alert.setTitle("Search Repository");
        alert.setView(edittext);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String name = edittext.getText().toString();
                RealmResults<Repository> results = db.getResoBasedOnName(name);
                if(!results.isEmpty()){
                    repositories = new ArrayList<Repository>();
                    for(int i=0; i<results.size(); i++){
                        repositories.add(results.get(i));
                    }
                    adapter.notify(repositories);
                }

                dialogInterface.dismiss();
                dialog.dismiss();
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                dialog.dismiss();
            }
        });

       switch (id){
           case R.id.action_Search:
               dialog = alert.create();
               dialog.show();
               break;
       }

        return false;
    }


    private void init(){
        db = new RealmDatabase(MainActivity.this);
        repositories = new ArrayList<>();
        adapter = new MainAdapter(repositories,MainActivity.this,listener);
        rvItems.setAdapter(adapter);

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        Date date = yesterday.getTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        syesterday = format.format(date);

        loadData();

    }



    private void loadData(){


        if (!repositories.isEmpty()){
            llLoading.setVisibility(View.GONE);
        }else {
            llLoading.setVisibility(View.VISIBLE);
        }
        HttpsTrustManager.allowAllSSL();
        String url = "https://api.github.com/search/repositories?sort=stars&order=desc&q=created:"+syesterday;


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray items = response.getJSONArray("items");
                            for(int i=0; i<items.length(); i++){
                                JSONObject jsonObject = items.getJSONObject(i);
                                String name = jsonObject.getString("name");
                                JSONObject owner = jsonObject.getJSONObject("owner");
                                String avatar_ulr = owner.getString("avatar_url");
                                String description = jsonObject.getString("description");
                                int forks = jsonObject.getInt("forks");
                                String language = jsonObject.getString("language");

                                Repository repository = new Repository(name,avatar_ulr,description,forks,language);
                                repository.setId(db.getRepositoryNextKey());

                                repositories.add(repository);
                                db.insertRepository(repository);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter.notify(repositories);
                        if (!repositories.isEmpty()) {
                            llLoading.setVisibility(View.GONE);
                        } else {
                            llLoading.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });




        AppController.getInstance().addToRequestQueue(request);
    }


    private MainListener listener = new MainListener() {
        @Override
        public void onClick(int position) {
           int id = repositories.get(position).getId();
            Intent intent = new Intent(MainActivity.this,SummaryActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        }
    };

}


