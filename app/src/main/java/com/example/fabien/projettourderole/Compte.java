package com.example.fabien.projettourderole;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Fabien on 19/03/2015.
 */
public class Compte {

    private String utilisateur;
    private String password;
    TextView txt;


    public Compte(String utilisateur, String password) {
        this.utilisateur = utilisateur;
        this.password = password;
    }



    @Override
    public String toString() {
        return "Connexion{" +
                "utilisateur='" + utilisateur + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
