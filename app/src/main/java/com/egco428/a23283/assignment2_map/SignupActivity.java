package com.egco428.a23283.assignment2_map;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class SignupActivity extends AppCompatActivity {
    EditText username;
    EditText password;
    EditText editLa, editLong;
    Button btnRandom;
    Button btnAdd;
    String user, pass, latitude, longtitude;
    private CommentsDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dataSource = new CommentsDataSource(this);
        dataSource.open();




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = (EditText) findViewById(R.id.signUser);
        password = (EditText) findViewById(R.id.signPass);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRandom = (Button) findViewById(R.id.btnRandom);
        editLa = (EditText) findViewById(R.id.editLa);
        editLong = (EditText) findViewById(R.id.editLong);


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String La = editLa.getText().toString();
                String Long = editLong.getText().toString();
                latitude = La;
                longtitude = Long;
                user = username.getText().toString();
                pass = password.getText().toString();
                final List<Comment> values = dataSource.getAllComments();


                if ((La.isEmpty()) || (Long.isEmpty())||user.isEmpty()||pass.isEmpty()) {
                    Context context = getApplicationContext();
                    CharSequence text = "Can't fill blank space";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
                else {
                    boolean check = checkID(values);
                    if (!check) {
                        dataSource.createComment(user, pass, latitude, longtitude); //add to database
                        dataSource.close();
                        finish();
                    }
                    else{
                        Context context = getApplicationContext();
                        CharSequence text = "username has already be use";
                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }

            }
        });
    }
    public boolean checkID (List<Comment> v){
        String user,pass;
        int i;
        user = username.getText().toString();
        pass = password.getText().toString();
        for (i = 0;i< v.size();i++)
        {
            final Comment course = v.get(i);
            if(user.equals(course.getUsername())){
                if (pass.equals(course.getPassword())){return true;}
            }

        }
        return false;
    }

}

