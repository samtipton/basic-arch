package com.whaleshark.basicarch.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author stipton
 */

@Entity
public class Recipe {

    @PrimaryKey
    public int id;

    public String title;
    public String href;
    public String ingredients;
    public String thumbnail;
}
