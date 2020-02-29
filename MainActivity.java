package com.example.sangeeth.iot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

   private int seconds,seconds2;
   private boolean startRun,startRun2;
   TextView capacity,temp,lit,humdd,motor;
    DatabaseReference dref;
     String cap,tempp,humd,light;
     String capp;
    Switch led;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      if(savedInstanceState!=null){
            seconds=savedInstanceState.getInt("Seconds");
            seconds2=savedInstanceState.getInt("Seconds2");
            startRun=savedInstanceState.getBoolean("startRun");
          startRun2=savedInstanceState.getBoolean("startRun2");
        }



      //  Timer();
        final Switch led = (Switch)findViewById(R.id.led1);
        final Switch fan = (Switch)findViewById(R.id.fan1);
        Button smart = (Button)findViewById(R.id.smart);
        capacity=(TextView)findViewById(R.id.capV);
        temp=(TextView)findViewById(R.id.Temperature);
        lit=(TextView)findViewById(R.id.light);
        humdd=(TextView)findViewById(R.id.humidity1);
        motor=(TextView)findViewById(R.id.motor);
       final TextView l=(TextView)findViewById(R.id.levell);
        final TextView time=(TextView)findViewById(R.id.time); // LED
        final TextView time2=(TextView)findViewById(R.id.time1); // FAN


       smart.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent s;
               s = new Intent(getApplicationContext(), smartAct.class);
               startActivity(s);
           }
       });





        //---------------------------------------LED--------------------------------
        led.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean on) {
                if(on)
                {


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("LED_STATUS");//LED_STATUS is Firebase database LED_STATUS
                    myRef.setValue("ON");

                  //  seconds=0;

                  // startRun=true;
                    Toast.makeText(getApplicationContext(),"ON",Toast.LENGTH_SHORT).show();  // checked...
                }
                else
                {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("LED_STATUS");//LED_STATUS is Firebase database LED_STATUS
                    myRef.setValue("OFF");





                     //startRun=false;

                    Toast.makeText(getApplicationContext(),"OFF",Toast.LENGTH_SHORT).show();  // umchecked...
                }


            }
        });
        //---------------------------------------FAN--------------------------------

        fan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean on) {

                if (on) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("FAN_STATUS:"); //FAN_STATUS is Firebase database FAN_STATUS
                    myRef.setValue("ON");

                    //seconds2 = 0;
                  //  startRun2 = true;

                    Toast.makeText(getApplicationContext(),"FAN ON",Toast.LENGTH_SHORT).show();
                } else {

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("FAN_STATUS:"); //FAN_STATUS is Firebase database FAN_STATUS
                    myRef.setValue("OFF");
                   // startRun2=false;
                    Toast.makeText(getApplicationContext(),"FAN OFF",Toast.LENGTH_SHORT).show();
                    }
            }
        });


        //---------------------------------------WATER LEVEL--------------------------------
        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                cap=dataSnapshot.child("DISTANCE").getValue().toString();
                capacity.setText(cap);
                capp=dataSnapshot.child("LEVEL").getValue().toString();
                l.setText(capp);



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //---------------------------------------LIGHT INTENSITY--------------------------------
        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                light=dataSnapshot.child("LUMINOUS").getValue().toString();
                lit.setText(light);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //---------------------------------------TEMP LEVEL--------------------------------
        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tempp=dataSnapshot.child("TEMPERATURE").getValue().toString();
                temp.setText(tempp);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //---------------------------------------HUMDITY LEVEL--------------------------------
        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                humd=dataSnapshot.child("HUMIDITY").getValue().toString();
                humdd.setText(humd);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       //-----------------------------MOTOR STATUS-------------------
        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Mstatus=dataSnapshot.child("MOTOR").getValue().toString();
                motor.setText(Mstatus);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        //-----------FAN TIME---------------------

        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             String   Ftime=dataSnapshot.child("FAN_TIME").getValue().toString();
                String   FS=dataSnapshot.child("FAN_STATUS:").getValue().toString();
                if(FS=="ON")
                {
                    fan.setChecked(true);
                }
                else
                {
                    fan.setChecked(false);
                }


                time2.setText(Ftime);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //-----------LED TIME---------------------

        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String   Ltime=dataSnapshot.child("LED_TIME").getValue().toString();
                String   LS=dataSnapshot.child("LED_STATUS").getValue().toString();
                if(LS=="ON")
                {
                    led.setChecked(true);
                }
                else
                {
                    led.setChecked(false);
                }

                time.setText(Ltime);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });









    }

 /*  private void Timer() {

        final Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                //---------------led1
                int hours=seconds/3600;
                int minutes=(seconds%3600)/60;
                int secs=seconds%60;
                String timee=String.format("%d:%02d:%02d",hours,minutes,secs);
                time.setText(timee);
                //----
                int hours2=seconds2/3600;
                int minutes2=(seconds2%3600)/60;
                int secs2=seconds2%60;
                String timee2=String.format("%d:%02d:%02d",hours2,minutes2,secs2);
                time2.setText(timee2);


                if(startRun){
                    seconds++;
                }
                if(startRun2)
                {
                    seconds2++;
                }

                handler.postDelayed(this,1000);
            }
        });
    } */


}
