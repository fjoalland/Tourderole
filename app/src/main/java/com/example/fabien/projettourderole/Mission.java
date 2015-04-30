package com.example.fabien.projettourderole;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by fjoalland on 09/04/2015.
 */
public class Mission extends ActionBarActivity {
    private static String url = "http://joallandfabien.no-ip.org/Tourderole/affichageMission_bd.php";
    private static final String TAG_RESULT = "missions";
    private static final String TAG_IDMISSION = "IdMission";
    private static final String TAG_COMMENTAIRE = "Commentaire";
    private static final String TAG_Urgent = "Urgent";
    private static final String TAG_Nom = "Nom";
    private static final String TAG_Prenom = "Prenom";
    private static final String TAG_Adresse = "Adresse";
    private static final String TAG_Cp = "Cp";

    JSONArray mission = null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        //On récupère les informations de connxion et de l'utilisateur connecté
        Boolean success = getIntent().getExtras().getBoolean("success");

        Button myButton = new Button(Mission.this);
        myButton.setText("Push Me");
        LinearLayout ll = (LinearLayout)findViewById(R.id.ll);
        ll.addView(myButton);

        new JSONParse().execute();


    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {

        TextView TVcommentaire = (TextView) findViewById (R.id.textViewCommentaire);
        TextView TVUrgent = (TextView) findViewById (R.id.textViewUrgent);
        TextView TVNom = (TextView) findViewById (R.id.textViewNom);
        TextView TVPrenom = (TextView) findViewById (R.id.textViewPrenom);
        TextView TVAdresse = (TextView) findViewById (R.id.textViewAdresse);
        TextView TVCp = (TextView) findViewById (R.id.textViewCp);

        public Integer parcourTabl = 0;
        private String Idmission;
        private String Commentaire;
        private String Urgent;
        private String Nom;
        private String Prenom;
        private String Adresse;
        private String Cp;

        private ProgressDialog pDialog;
        private int i=1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //On initialise la barre de chargement
            pDialog = new ProgressDialog(Mission.this);
            pDialog.setMessage("Connexion en cours ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected JSONObject doInBackground(String... args) {

            int matricule = getIntent().getExtras().getInt("matricule");
            int idVehicule = getIntent().getExtras().getInt("idVehicule");
            JSONParserMission jParser = new JSONParserMission();
            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url, 2, 2);

            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();
            System.out.println("Résultat du json: "+json);
            try {
                // Getting JSON Array



                mission= json.getJSONArray(TAG_RESULT);
                JSONObject c = mission.getJSONObject(0);
                // Storing  JSON item in a Variable
                Idmission = c.getString(TAG_IDMISSION);
                Commentaire = c.getString(TAG_COMMENTAIRE);
                Urgent = c.getString(TAG_Urgent);
                Nom = c.getString(TAG_Nom);
                Prenom = c.getString(TAG_Prenom);
                Adresse = c.getString(TAG_Adresse);
                Cp = c.getString(TAG_Cp);

                TVcommentaire.setText(Commentaire);
                TVUrgent.setText(Urgent);
                TVNom.setText(Nom);
                TVPrenom.setText(Prenom);
                TVAdresse.setText(Adresse);
                TVCp.setText(Cp);




                    Button button_suivant = (Button) findViewById(R.id.ButtonSuivant);
                    button_suivant.setOnClickListener(new View.OnClickListener() {

                        //listener sur le boutton suivant
                        @Override
                        public void onClick(View v) {
                            JSONObject c = null;
                            try {

                                c = mission.getJSONObject(i);
                                Idmission = c.getString(TAG_IDMISSION);
                                Commentaire = c.getString(TAG_COMMENTAIRE);
                                Urgent = c.getString(TAG_Urgent);
                                Nom = c.getString(TAG_Nom);
                                Prenom = c.getString(TAG_Prenom);
                                Adresse = c.getString(TAG_Adresse);
                                Cp = c.getString(TAG_Cp);

                                TVcommentaire.setText(Commentaire);
                                TVUrgent.setText(Urgent);
                                TVNom.setText(Nom);
                                TVPrenom.setText(Prenom);
                                TVAdresse.setText(Adresse);
                                TVCp.setText(Cp);

                                i+=1;
                                if (i>2){
                                    i=0;
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            // Storing  JSON item in a Variable

                        }
                    });


                //Set JSON Data in TextView
                System.out.println("JSONParse{" +
                        "Idmission='" + Idmission + '\'' +
                        ", Commentaire='" + Commentaire + '\'' +
                        ", Urgent='" + Urgent + '\'' +
                        ", Nom='" + Nom + '\'' +
                        ", Prenom='" + Prenom + '\'' +
                        ", Adresse='" + Adresse + '\'' +
                        ", Cp='" + Cp + '\'' +
                        '}');


            } catch (JSONException e) {
                e.printStackTrace();
                System.out.println("Erreur dans le parsing du json.");
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
