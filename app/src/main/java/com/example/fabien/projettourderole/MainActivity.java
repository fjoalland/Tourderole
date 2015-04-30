package com.example.fabien.projettourderole;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private EditText EditTextUtilisateur;
    private EditText EditTextPassword;
    private String utilisateur;
    private String password;
    private Boolean success;
    private int matricule;
    private int idVehicule;

    //Bare de chargement
    private ProgressDialog pDialog;

    //URL vers la page php de connexion
    private static String url = "http://joallandfabien.no-ip.org/Tourderole/affichage_bd.php";
    private static final String TAG_RESULT = "valeurs";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MATRICULE = "matricule";
    private static final String TAG_IDVEHICULE = "idVehicule";

    JSONArray user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_connexion = (Button) findViewById(R.id.ButtonConnexion);
        button_connexion.setOnClickListener(new View.OnClickListener() {
            //listener sur le boutton connexion
            @Override
            public void onClick(View v) {

                //On récupère dans un String l'utilisateur et le mot de passe
                EditTextUtilisateur = (EditText) findViewById(R.id.EditTextUtilisateur);
                utilisateur = EditTextUtilisateur.getText().toString();
                EditTextPassword = (EditText) findViewById(R.id.EditTextPassword);
                password = EditTextPassword.getText().toString();

                new JSONParse().execute();

                //System.out.println("Sucess onCreate "+success);

            }
        });



        Button button_annuler = (Button) findViewById(R.id.ButtonAnnuler);
        button_annuler.setOnClickListener(new View.OnClickListener() {

            //listener sur le boutton annuler
            @Override
            public void onClick(View v) {

            }
        });

    }


    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //On initialise la barre de chargement
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Connexion en cours ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, utilisateur, password);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {

            System.out.println("Résultat du json: "+json);
            try {
                // Getting JSON Array
                user= json.getJSONArray(TAG_RESULT);
                JSONObject c = user.getJSONObject(0);
                // Storing  JSON item in a Variable
                success = c.getBoolean(TAG_SUCCESS);
                matricule = c.getInt(TAG_MATRICULE);
                idVehicule = c.getInt(TAG_IDVEHICULE);
                //Set JSON Data in TextView
                System.out.println(success+" "+ matricule+" "+idVehicule);


            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("Erreur dans le parsing du json.");
            }
                if(success){
                    pDialog.dismiss();
                    Intent secondeActivite = new Intent(MainActivity.this, Mission.class);

                    //On envoit les informations de connexion de l'utilisateur dans la seconde Activité Mission.class
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("success", success);
                    bundle.putInt("matricule", matricule);
                    bundle.putInt("idVehicule", idVehicule);
                    secondeActivite.putExtras(bundle);
                    startActivity(secondeActivite);
                }

                if(!success){
                    pDialog.setMessage("Mauvais mot de passe ou utilisateur.");
                } else {
                    pDialog.setMessage("Problème de connexion.");
                }

        }
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




}
