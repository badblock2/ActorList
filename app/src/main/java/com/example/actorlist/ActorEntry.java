package com.example.actorlist;

import android.os.Parcel;

import java.io.Serializable;

public class ActorEntry implements Serializable{
    private static final int [] FEMALE_PHOTOS = {
        R.drawable.female_001,
        R.drawable.female_002,
        R.drawable.female_003,
        R.drawable.female_004,
        R.drawable.female_005,
        R.drawable.female_006,
        R.drawable.female_007,
        R.drawable.female_008,
        R.drawable.female_009,
        R.drawable.female_010,
        R.drawable.female_011,
        R.drawable.female_012,
        R.drawable.female_013,
        R.drawable.female_014
    };

    private static final int [] MALE_PHTOTOS = {
        R.drawable.male_001,
        R.drawable.male_002,
        R.drawable.male_003,
        R.drawable.male_004,
        R.drawable.male_005,
        R.drawable.male_006,
        R.drawable.male_007
    };

    public int photo;
    public String id;
    public String name;
    public String email;
    public String address;
    public String gender;
    public String mobile;
    public String home;
    public String office;

    public static int getPhoto(String gender){
        final int [] photos;
        if(gender.equals("female")) photos = FEMALE_PHOTOS;
        else                        photos = MALE_PHTOTOS;

        return photos[(int)(Math.random() * photos.length)];
    }

    public String toString(){
        return "Actor (" + name + "/" + id + "/" + email + ")";
    }

}
