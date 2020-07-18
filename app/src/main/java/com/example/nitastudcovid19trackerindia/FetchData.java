package com.example.nitastudcovid19trackerindia;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class FetchData extends AsyncTask {
    String data ="";
    String dataParsed = "";
    String singleParsed ="";
    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            URL url = new URL("https://api.covidindiatracker.com/state_data.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA = new JSONArray(data);
            for(int i =0 ;i < JA.length(); i++){
                JSONObject JO = (JSONObject) JA.get(i);
                singleParsed =  "Name:" + JO.get("id") + "\n"
                        /*"Password:" + JO.get("password") + "\n"+
                        "Contact:" + JO.get("contact") + "\n"+
                        "Country:" + JO.get("country") + "\n"*/;

                dataParsed = dataParsed + singleParsed +"\n" ;


            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        MainActivity.data.setText(this.dataParsed);



    }



}
