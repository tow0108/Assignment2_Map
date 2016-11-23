package com.egco428.a23283.assignment2_map;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    Button signIn;
    Button signUp;
    Button cancle;
    EditText username,password;
    private CommentsDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signIn = (Button)findViewById(R.id.btnSignIn);
        signUp = (Button)findViewById(R.id.btnSignup);
        cancle = (Button)findViewById(R.id.cancle);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


        dataSource = new CommentsDataSource(this);
        dataSource.open();
        final List<Comment> values = dataSource.getAllComments();


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean check = checkID(values);
                if(check) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    Context context = getApplicationContext();
                    CharSequence text = "Login success!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                    startActivity(intent);
                }
                else {
                    Context context = getApplicationContext();
                    CharSequence text = "Login fail!";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("");
                password.setText("");

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
    @Override
    protected void onResume(){
        super.onResume();
        dataSource.open();

    }
    @Override
    protected void onPause(){
        super.onPause();
        dataSource.close();
    }
}
