package in.starlabs.startcare;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Arvind on 28/03/16.
 */
public class ConnectionManager extends AsyncTask<String, String, String> {
    Context mContext;
    public AsyncResponse delegate = null;

//    Activity activity;

//    public ConnectionManager(Context applicationContext) {
//        this.mContext = applicationContext;
//    }

    @Override
    protected String doInBackground(String... params) {
        String subjectURL = params[0];
        String subjectStr = "";

        try {
            subjectStr = getSubject(subjectURL);
        }catch (Exception e){
        }
        return subjectStr;
    }

    private String getSubject(String subjectURL) {


        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(subjectURL);
            /*
             HttpClient is more then less deprecated. Need to change to URLConnection
              */
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.addRequestProperty("Content-length", "0");
            urlConnection.setUseCaches(false);
            urlConnection.setAllowUserInteraction(false);
            urlConnection.connect();

            int statusCode = urlConnection.getResponseCode();
            switch (statusCode) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    return sb.toString();
            }

        } catch (IllegalStateException e3) {

            e3.printStackTrace();
        } catch (ConnectException e){
            e.printStackTrace();


        } catch (IOException e4) {

            e4.printStackTrace();
        }
        finally {
            if (urlConnection != null) {
                try {
                    urlConnection.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        delegate.processFinish(100,s);
        super.onPostExecute(s);
    }
}