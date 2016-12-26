package com.nzisis.vivantetest.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nzisis.vivantetest.Model.RealmDatabase;
import com.nzisis.vivantetest.Model.Repository;
import com.nzisis.vivantetest.R;
import com.squareup.picasso.Picasso;

import io.realm.RealmResults;


/**
 * Created by vromia on 12/26/16.
 */
public class SummaryActivity extends AppCompatActivity {
    private TextView tvForks,tvLanguage,tvDescription,tvName;
    private RoundedImageView ivAvatar;
    private RealmDatabase db;
    private int id;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);


        tvForks = (TextView) findViewById(R.id.tvForks);
        tvLanguage = (TextView) findViewById(R.id.tvLanguage);
        tvName = (TextView) findViewById(R.id.tvName);
        tvDescription = (TextView) findViewById(R.id.tvDes);
        ivAvatar = (RoundedImageView) findViewById(R.id.ivAvatar);

        init();


    }

    private void init(){
        db = new RealmDatabase(SummaryActivity.this);
        id = getIntent().getExtras().getInt("id");

        RealmResults<Repository> results = db.getResoBasedOnID(id);
        if(!results.isEmpty()){
            repository = results.get(0);
            tvName.setText(repository.getName());
            tvDescription.setText(repository.getDescription());
            tvForks.setText(repository.getForks()+"");
            tvLanguage.setText(repository.getLanguage());
            Picasso.with(SummaryActivity.this).load(repository.getAvatarURL()).into(ivAvatar);
        }


    }


}
