package com.example.nitastudcovid19trackerindia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
   public static TextView data;
   public String url="https://api.covidindiatracker.com/state_data.json";
   String mdata;
   private RecyclerView mRecyclerView;
   private  ArrayList<AdopterItems> mExampleList;
   private AdopterClass mAdopterClass;
   private ProgressBar progressBar;
   private String pp="https://docs.google.com/document/d/1E9yHmbBU41HEjg2dni2I8N1Fua8kZBstSrG1BWNwhwI/edit?usp=sharing";
   private TextView inrCnf,Cnf,inrAct,Act,inrRec,Rec,inrDec,Dec;
   private ImageButton imageButton;
   public static final String MKEY="key1";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkInternetConnection(getWindow().getDecorView().getRootView());
        mRecyclerView=findViewById(R.id.recycler_view);
        progressBar=findViewById(R.id.progressBar);
        imageButton=findViewById(R.id.question);
        inrCnf=findViewById(R.id.increment);
        inrAct=findViewById(R.id.increment_active);
        inrRec=findViewById(R.id.increment_recovered);
        inrDec=findViewById(R.id.increment_deceased);
        Cnf=findViewById(R.id.total_cases);
        Act=findViewById(R.id.total_cases_active);
        Rec=findViewById(R.id.total_cases_recovered);
        Dec=findViewById(R.id.total_cases_deceased);

        dashBoardDAta();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
         mExampleList = new ArrayList<>();



        //final HashMap<String, String> meMap=new HashMap<String, String>();
        final String[] colors={"#FFFF00","#F5F5F5","#E9967A","#F4A460","#FA8072","#4682B4","#EE82EE","#EE3030",
                "#87CEEB","#808080","#7CFC00","#FF00FF","#DFFFFF00","#556B2F","#48D1CC","#98FB98","#EE3030",
                "#32CD32","#4B0082","#00FF7F","#66CDAA","#00BFFF","#806495ED","#FF1493","#2E8B57","#8B0000",
                "#F5F5F5","#808000","#DA70D6","#8B008B","#00FFFF","#C71585","#228B22","#FFC0CB","#6A5ACD",
                "#FFE4E1","#00008B","#9932CC"};




                RequestQueue requestQueue=Volley.newRequestQueue(this);
                /*JsonObjectRequest jsonObjectRequest=new JsonObjectRequest("https://my-json-server.typicode.com/typicode/demo/profile",
                        null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("TAG", response.getString("name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "wrong ho gaya");
                    }
                });*/


                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i=0;i<response.length();i++){
                            JSONObject jsonObject= null;
                            try {
                                jsonObject = response.getJSONObject(i);
                                mdata+="\n"+jsonObject.getString("id")+"\n";
                                String s=(jsonObject.getString("state"));


                                for (int j = 0; j < jsonObject.getJSONArray("districtData").length(); j++){
                                    JSONObject object=jsonObject.getJSONArray("districtData").getJSONObject(j);
                                    mdata+=object.getString("id")+"  ";


                                }
                                if (s!=null)
                                mExampleList.add(new AdopterItems(jsonObject.getString("state"),jsonObject.getString("confirmed"),jsonObject.getString("active"),
                                        jsonObject.getString("recovered"),jsonObject.getString("deaths"),jsonObject.getString("cChanges"),
                                        jsonObject.getString("aChanges"),jsonObject.getString("rChanges"),jsonObject.getString("dChanges")
                                        ,colors[i],colors[response.length()-i-1]));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        mAdopterClass=new AdopterClass(MainActivity.this,mExampleList);
                        progressBar.setVisibility(View.GONE);
                        mRecyclerView.setAdapter(mAdopterClass);
                        //data.setText(mdata);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                });

                requestQueue.add(jsonArrayRequest);


        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });


    }

    public void dashBoardDAta(){
        String dUrl="https://api.covidindiatracker.com/total.json";
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(dUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Cnf.setText(response.getString("confirmed"));
                    Act.setText(response.getString("active"));
                    Rec.setText(response.getString("recovered"));
                    Dec.setText(response.getString("deaths"));
                    inrCnf.setText("+"+response.getString("cChanges"));
                    inrRec.setText("+"+response.getString("rChanges").replace("-",""));
                    inrDec.setText("+"+response.getString("dChanges"));
                    inrAct.setText("");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private Boolean checkInternetConnection(View view) {
        ConnectivityManager connectivityManager=(ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo==null){
            Snackbar snackbar = Snackbar.make(view, "No Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.setBackgroundTint(ContextCompat.getColor(this,R.color.dark_orange));
            snackbar.setTextColor(ContextCompat.getColor(this,R.color.black));
            snackbar.show();
            return true;
        }
        return false;
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.share:
                        Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                        String app_url = getAppUrl();
                        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                        startActivity(Intent.createChooser(shareIntent, "Share via"));
                        return true;
                    case R.id.privacy:
                        Intent intent=new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(pp));
                        startActivity(intent);
                        return true;
                    case R.id.developer:
                        openDialog();
                        return true;
                    default:
                        return false;
                }

            }
        });
    }

    private String getAppUrl() {
        String appurl;

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("AppUrl");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String s1="";
                if (dataSnapshot.exists())
                s1=dataSnapshot.getValue(String.class);


                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("k1",s1);
                editor.apply();
                //Toast.makeText(MainActivity.this,s1,Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
         appurl = prefs.getString("k1", "Please Check Your Internet Connection");
         return appurl;

    }

    public void openDialog() {
        AlertDialog.Builder alertDialog1 = new AlertDialog.Builder(MainActivity.this);
        View customView = getLayoutInflater().inflate(R.layout.layout_dialog,null);
        Button btnClose = customView.findViewById(R.id.close);

        alertDialog1.setView(customView);
        final AlertDialog dialog=alertDialog1.create();
        dialog.show();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                dialog.dismiss();


            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Insert Subject here");
                String app_url = " https://play.google.com/store/apps/details?id=com.example.administrator";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
                return true;
            case R.id.privacy:
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(pp));
                startActivity(intent);
                return true;
            case R.id.developer:
                openDialog();
                return true;
            default:
                return false;
        }
    }
}







