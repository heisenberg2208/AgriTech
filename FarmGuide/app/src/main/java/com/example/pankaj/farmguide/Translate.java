package com.example.pankaj.farmguide;

/**
 * Created by PANKAJ on 2/23/2019.
 */

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by PANKAJ on 6/1/2018.
 */
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
//import com.koushikdutta.async.future.FutureCallback;
//import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static com.example.pankaj.farmguide.Translate.op;


/**
 * Created by Devendra on 3/25/2018.
 */

class Task1 extends AsyncTask<String , Void , String>
{
    String res;

    @Override
    protected String doInBackground(String... strings) {

        String line="",json="";
        try
        {
            URL u = new URL(strings[0]);
            HttpURLConnection c=(HttpURLConnection) u.openConnection();
            c.connect();


            InputStream is= c.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            while( (line = br.readLine()) != null)
            {
                json = json + line + "\n";
            }
            if(json != null)
            {

                JSONObject jo = new JSONObject(json);
                String a = jo.getJSONObject("data").getString("translations");
                JSONArray ar = new JSONArray(a);
                op = ar.getJSONObject(0).getString("translatedText");
                return op;
                //Toast.makeText(c, op, Toast.LENGTH_SHORT).show();
            }

        }
        catch(Exception e)
        {

        }

        return res;
    }

    @Override
    protected void onPostExecute(String res) {
        super.onPostExecute(res);
//        tvSearchResults.setText(aDouble+" C");
    }
}


public class Translate {

    static boolean flag = true;
    static String op="";
    public static String translate(String src , String tar , String text , final Context c) {


        final String ip = Uri.encode(text);
        String strapi = "https://translation.googleapis.com/language/translate/v2?q=" + ip + "&target=" + tar + "&format=text&source=" + src + "&key=AIzaSyBFIJa30C78SrBU7g0Lxo9lxoJkhCpfNUw";
        Task1 task = new Task1();
        try {
            op = task.execute(strapi).get();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Toast.makeText(c, "Now sending result" + op, Toast.LENGTH_SHORT).show();
        return op;
//
//        Toast.makeText(c, "after farmer only", Toast.LENGTH_SHORT).show();
//        Ion.with(c)
//                .load(strapi)
//                .asString()
//                .setCallback(new FutureCallback<String>() {
//                    @Override
//                    public void onCompleted(Exception e, String result) {
//
//                        try {
//                            JSONObject jo = new JSONObject(result);
//                            String a = jo.getJSONObject("data").getString("translations");
//                            JSONArray ar = new JSONArray(a);
//                            op = ar.getJSONObject(0).getString("translatedText");
//                           // Toast.makeText(c, op, Toast.LENGTH_SHORT).show();
//
//
//                        } catch (JSONException e1) {
//                            e1.printStackTrace();
//                        }
//
//
//                    }
//                });
//
//        return op;
    }


}


