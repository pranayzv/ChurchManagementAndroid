package com.example.churchmanagementsystem.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.churchmanagementsystem.Constants;
import com.example.churchmanagementsystem.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DaoUser {


    public static User setLoggedInUser(User u, Context c) {
        SharedPreferences sp = c.getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", u.getUname());
        editor.putString("pass", u.getPass());
        editor.putString("type", u.getType());
        editor.putString("churchbranch", u.getBranch());
        Set<String> p = new HashSet<>(u.getPrayer());
        editor.putStringSet("prayer", p);
        if (!u.getType().equals("member")) {
            Set<String> s = new HashSet<>(u.getScripture());
            editor.putStringSet("scripture", s);
        } else {
            u.setScripture(null);
        }
        editor.apply();
        return u;
    }


    public static User getLoggedUser(Context c) {
        SharedPreferences sp = c.getSharedPreferences("credentials", MODE_PRIVATE);
        Set<String> prayer = sp.getStringSet("prayer", null);
        if (prayer == null) {
            return null;
        }
        Set<String> scripture = sp.getStringSet("scripture", null);
        ArrayList<String> s = new ArrayList<>();
        ArrayList<String> p = new ArrayList<>(prayer);
        if (scripture != null) {
            s.addAll(scripture);
        }
        return new User(sp.getString("username", ""),
                sp.getString("pass", ""),
                sp.getString("type", ""),
                s,
                p,
                sp.getString("churchbranch", "")
        );

    }

    public static void removeLoggedInUser(Context c) {
        SharedPreferences sp = c.getSharedPreferences("credentials", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", "");
        editor.putString("pass", "");
        editor.putString("type", "");
        editor.putString("churchbranch", "");
        editor.putStringSet("prayer", null);
        editor.putStringSet("scripture", null);
        editor.apply();
        Constants.user = null;
    }

}
