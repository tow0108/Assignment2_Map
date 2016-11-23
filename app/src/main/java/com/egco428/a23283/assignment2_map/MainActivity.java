package com.egco428.a23283.assignment2_map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CommentsDataSource dataSource;
    private ArrayAdapter<Comment> courseArrayAdapter;
    public static final String XYposition = "position";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        dataSource = new CommentsDataSource(this);
        dataSource.open();
        final List<Comment> values = dataSource.getAllComments();
        ListView listView = (ListView) findViewById(R.id.listview);
        courseArrayAdapter = new CourseArrayAdapter(this, 0,values);
        listView.setAdapter(courseArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView,final View view, int position, long l) {
                if (courseArrayAdapter.getCount()>0) {
                    final Comment course = values.get(position);
                    Intent intent = new Intent(MainActivity.this,MapsActivity.class);
                    String x="latP",y="longP";
                    String latitude = course.getLatitude();
                    String longtitude = course.getLongtitude();
                    String username   = course.getUsername();
                    String inputValue[] = {latitude,longtitude,username};

                    intent.putExtra(XYposition,inputValue);
//                    dataSource.deleteComment(course);
                    startActivity(intent);
                }
            }
        });


    }
    class CourseArrayAdapter extends ArrayAdapter<Comment> {
        Context context;
        List<Comment> objects;
        public CourseArrayAdapter(Context context,int resource,List <Comment> objects) {
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Comment course = objects.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);//ประกาศตัวแปร Inflater
            View view = inflater.inflate(R.layout.list_template,null);//ตรง null ถ้าสร้างเป็นกรุ๊บ Layout สามารถใส่เพิ่มได้

            TextView word = (TextView)view.findViewById(R.id.textView);
            word.setText(course.getUsername());

            return view;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom_dialog);
                dialog.setCancelable(true);

                Button button1 = (Button)dialog.findViewById(R.id.btnYes);
                button1.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);

                    }
                });

                Button button2 = (Button)dialog.findViewById(R.id.btnNO);
                button2.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });


                dialog.show();

        }
        return super.onOptionsItemSelected(item);
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
