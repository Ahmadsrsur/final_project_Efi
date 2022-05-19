package com.example.sql_example;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RetrieveFeedTask extends AsyncTask<String , Void, String> {

    private Exception exception;
    private String modifiedSentence;
    private String user_name,password,str = "";


    public RetrieveFeedTask(String user_name, String password) {
        this.user_name = user_name;
        this.password = password;
    }
    public RetrieveFeedTask() {
    }

    protected String doInBackground(String... urls) {

        Socket clientSocket = null;

        try {
            clientSocket = new Socket("192.168.0.114", 10000);


                DataOutputStream outToServer =
                        new DataOutputStream(clientSocket.getOutputStream());

                BufferedReader inFromServer =
                        new BufferedReader(new
                                InputStreamReader(clientSocket.getInputStream()));

                Log.d("result", "yes");


                    str = user_name+" "+password;
                    outToServer.writeBytes(str + '\n');

                    modifiedSentence = inFromServer.readLine();


            } catch(IOException e){
                e.printStackTrace();
            }
            return modifiedSentence;

    }

    protected void onPostExecute(String feed) {

        Log.d("after" , feed);
    }

}
