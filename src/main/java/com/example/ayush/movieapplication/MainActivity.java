package com.example.ayush.movieapplication;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isThereInternet()){
            //starts web service
            getInfo info = new getInfo();
            info.execute();
        }else{
            Toast.makeText(this, "Internet not enabled", Toast.LENGTH_SHORT).show();
        }
    }

    public Boolean isThereInternet(){
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        //check if internet has been enabled
        return manager.getActiveNetworkInfo().isConnected();
    }

    class getInfo extends AsyncTask {
        ArrayList<Data> movieInfo = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        String movieurl = "http://api.themoviedb.org/3/tv/top_rated?api_key=8496be0b2149805afa458ab8ec27560c";
        @Override
        protected Object doInBackground(Object[] params) {
            try {
                URL url = new URL(movieurl);
                URLConnection connection = url.openConnection();
                connection.connect();
                //opens the connection
                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                String info = "";
                while ((info = reader.readLine()) != null){
                    builder.append(info);
                    //appends input into a stringBuilder
                }


            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            try {
                //gets all the information and formats it
                JSONObject object = new JSONObject(builder.toString());
                JSONArray list = object.getJSONArray("results");
                int size = list.length() - 1;
                Log.e("size",String.valueOf(size));
                for(int i = 0;i<= size;i++){
                    Data data = new Data();
                    String name = list.getJSONObject(i).getString("name");
                    //gets the name of the movie
                    long rating = list.getJSONObject(i).getLong("vote_average");
                    //gets the movie ratings
                    int id = list.getJSONObject(i).getInt("id");
                    //gets the movie id
                    data.setName(name);
                    data.setId(id);
                    data.setRating(rating);
                    //adds the data into the arraylist
                    movieInfo.add(data);
                }
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleView);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(MainActivity.this);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(new CustomAdapter(MainActivity.this, movieInfo));



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

