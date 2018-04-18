package com.example.actorlist.util;

/*{
        "contacts": [
        {
        "id": "c200",
        "name": "Ravi Tamada",
        "email": "ravi@gmail.com",
        "address": "xx-xx-xxxx,x - street, x - country",
        "gender" : "male",
        "phone": {
        "mobile": "+91 0000000000",
        "home": "00 000000",
        "office": "00 000000"
        }
        },
        {
        "id": "c201",
        "name": "Johnny Depp",
        "email": "johnny_depp@gmail.com",
        "address": "xx-xx-xxxx,x - street, x - country",
        "gender" : "male",
        "phone": {
        "mobile": "+91 0000000000",
        "home": "00 000000",
        "office": "00 000000"
        }
        },

    ....
*/

import android.util.Log;

import com.example.actorlist.ActorEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private static final String TAG = JsonUtil.class.getSimpleName();

    private static final String JSON_ARRAY_CONTACTS = "contacts";
    private static final String JSON_OBJECT_ID = "id";
    private static final String JSON_OBJECT_NAME = "name";
    private static final String JSON_OBJECT_EMAIL = "email";
    private static final String JSON_OBJECT_ADDRESS = "address";
    private static final String JSON_OBJECT_GENDER = "gender";
    private static final String JSON_OBJECT_PHONE = "phone";
    private static final String JSON_OBJECT_MOBILE = "mobile";
    private static final String JSON_OBJECT_HOME = "home";
    private static final String JSON_OBJECT_OFFICE = "office";

    public static List<ActorEntry> parseJsonResponse(String response){
        ArrayList<ActorEntry> list = new ArrayList<ActorEntry>();

        try {
            JSONObject root = new JSONObject(response.toString());
            JSONArray contacts = root.getJSONArray(JSON_ARRAY_CONTACTS);

            for(int i=0 ; i<contacts.length() ; i++) {
                JSONObject contact = contacts.getJSONObject(i);
                JSONObject phone = contact.getJSONObject(JSON_OBJECT_PHONE);

                ActorEntry actor = new ActorEntry();
                actor.id = contact.getString(JSON_OBJECT_ID);
                actor.name = contact.getString(JSON_OBJECT_NAME);
                actor.gender = contact.getString(JSON_OBJECT_GENDER);
                actor.email = contact.getString(JSON_OBJECT_EMAIL);
                actor.address = contact.getString(JSON_OBJECT_ADDRESS);
                actor.mobile = phone.getString(JSON_OBJECT_MOBILE);
                actor.home = phone.getString(JSON_OBJECT_HOME);
                actor.office = phone.getString(JSON_OBJECT_OFFICE);
                actor.photo = ActorEntry.getPhoto(actor.gender);

                list.add(actor);

                Log.d(TAG, "Add ActorEntry : " + actor.toString());
            }

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }

        return list;
    }
}
