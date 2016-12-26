package com.nzisis.vivantetest.Model;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by nzisis on 26/12/16.
 */
public class RealmDatabase {
    private Realm realm;


    public RealmDatabase(Context context) {
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }


    public void insertRepository(final Repository repository) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.insert(repository);
            }
        });
    }

    public int getRepositoryNextKey() {
        if (realm.where(Repository.class).findAll().isEmpty()) return 1;


        return realm.where(Repository.class).max("id").intValue() + 1;
    }

    public RealmResults<Repository> getResoBasedOnID(int id){
        RealmResults<Repository> items = realm.where(Repository.class).equalTo("id",id).findAll();

        return items;
    }

    public RealmResults<Repository> getResoBasedOnName(String name){
        RealmResults<Repository> items = realm.where(Repository.class).equalTo("name", name).findAll();

        return items;
    }


}
