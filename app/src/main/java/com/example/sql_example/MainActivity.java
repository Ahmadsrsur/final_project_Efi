package com.example.sql_example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    private Button submit_btn;
    private TextView user_name,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        submit_btn = findViewById(R.id.submit_btn);
        user_name = findViewById(R.id.user_name_txt);
        password = findViewById(R.id.pass_txt);

        final RetrieveFeedTask[] retrieveFeedTask = {new RetrieveFeedTask()};

        Intent intent = new Intent(this,FirebaseActivity.class);

            submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    retrieveFeedTask[0] = new RetrieveFeedTask(user_name.getText().toString(),password.getText().toString());
                    retrieveFeedTask[0].execute();
                    Toast.makeText(MainActivity.this, "USER ADDED", Toast.LENGTH_SHORT).show();
                  //  intent.putExtra("server",retrieveFeedTask);
                    startActivity(intent);

                }
            });



    }
}