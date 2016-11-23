package com.egco428.a23283.assignment2_map;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SignupActivity extends AppCompatActivity implements SensorEventListener {
    EditText username;
    EditText password;
    EditText editLa, editLong;
    Button btnRandom;
    Button btnAdd;
    String user, pass, latitude, longtitude;
    private SensorManager sensorManager;
    private long lastUpdate;
    private CommentsDataSource dataSource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dataSource = new CommentsDataSource(this);
        dataSource.open();




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        lastUpdate = System.currentTimeMillis();

        username = (EditText) findViewById(R.id.signUser);
        password = (EditText) findViewById(R.id.signPass);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnRandom = (Button) findViewById(R.id.btnRandom);
        editLa = (EditText) findViewById(R.id.editLa);
        editLong = (EditText) findViewById(R.id.editLong);

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                double randomlat = -85.000000 + (85.000000 - (-85.000000)) * r.nextDouble();
                double randomlong = -179.999989 + (179.999989 - (-179.999989)) * r.nextDouble();
                DecimalFormat df = new DecimalFormat("#.000000");
                String lat = df.format(randomlat);
                String longt = df.format(randomlong);
                editLa.setText(lat);
                editLong.setText(longt);
            }
        });

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
                        Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                        startActivity(intent);
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
        for (i = 0;i< v.size();i++)
        {
            final Comment course = v.get(i);
            if(user.equals(course.getUsername())){return true;}

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

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER ){
            getAccelerometer(event);
        }

    }

    private void getAccelerometer(SensorEvent event){
        float[] values = event.values;
        // Movement
        float x = values[0];
        float y = values[1];
        float z = values[2];

        float accelationSquareRoot = (x * x + y * y + z * z)
                / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
        long actualTime = System.currentTimeMillis();



        if (accelationSquareRoot >= 3.5 )
        {
            if (actualTime - lastUpdate < 700) {
                return;
            }
            Random r = new Random();
            double randomlat = -85.000000 + (85.000000 - (-85.000000)) * r.nextDouble();
            double randomlong = -179.999989 + (179.999989 - (-179.999989)) * r.nextDouble();
            DecimalFormat df = new DecimalFormat("#.000000");
            String lat = df.format(randomlat);
            String longt = df.format(randomlong);
            editLa.setText(lat);
            editLong.setText(longt);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

