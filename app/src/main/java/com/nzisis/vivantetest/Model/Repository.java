package com.nzisis.vivantetest.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vromia on 12/26/16.
 */
public class Repository extends RealmObject {

    private String name;
    private String avatarURL;
    private String description;
    private int forks;
    private String language;

    @PrimaryKey
    private int id;

    public Repository(String name,String avatarURL,String description,int forks , String language){
        this.name = name;
        this.avatarURL = avatarURL;
        this.description = description;
        this.forks = forks;
        this.language = language;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public String getAvatarURL(){
        return avatarURL;
    }

    public String getDescription(){
        return description;
    }

    public int getForks(){
        return forks;
    }

    public String getLanguage(){
        return language;
    }

    public int getId(){
        return id;
    }

}
