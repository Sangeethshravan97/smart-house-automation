package com.example.sangeeth.iot;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class smartAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart);

        DatabaseReference dref;
        final Switch Sac = (Switch)findViewById(R.id.ac);
        final Switch Sheater = (Switch)findViewById(R.id.heater);
        final Switch Slight = (Switch)findViewById(R.id.lights);


        SharedPreferences sharedPreferences = getSharedPreferences("save",MODE_PRIVATE);




        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String T=dataSnapshot.child("SMART_AC").getValue().toString(); // TEMPERATURE



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String H=dataSnapshot.child("SMART_HEATER").getValue().toString(); // LIGHT




            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //------------
        dref= FirebaseDatabase.getInstance().getReference();
        dref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String L=dataSnapshot.child("SMART_LIGHT").getValue().toString(); // LIGHT





            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });











        //---------------------------------------LED--------------------------------

        Sac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Sac.isChecked())
                {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("swtich on", true);
                    editor.apply();
                    Sac.setChecked(true);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("SMART_AC");
                    myRef.setValue("ON");
                    Toast.makeText(getApplicationContext(),"SMART AC ON",Toast.LENGTH_SHORT).show();  // checked..
                }
                else
                {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("swtich on", false);
                    editor.apply();
                    Sac.setChecked(false);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("SMART_AC");
                    myRef.setValue("OFF");
                    Toast.makeText(getApplicationContext(),"SMART AC OFF",Toast.LENGTH_SHORT).show();  // umchecked..


                }
            }
        });

     /*   Sac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean on) {
                if(on)
                {


                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("swtich on", on);
                    editor.apply();


                  //  SharedPreferences.Editor editor = preferences.edit();
                   // editor.putBoolean("tgpref", Sac.isChecked()); // value to store
                   // editor.commit();

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("SMART_AC");
                    myRef.setValue("ON");
                    Toast.makeText(getApplicationContext(),"SMART AC ON",Toast.LENGTH_SHORT).show();  // checked...
                }
                else
                {




                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("SMART_AC");
                    myRef.setValue("OFF");
                    Toast.makeText(getApplicationContext(),"SMART AC OFF",Toast.LENGTH_SHORT).show();  // umchecked...
                }


            }
        });  */

        //----------------------SMART HEATER------------------------------------------------


          Sheater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Sheater.isChecked())
                    {
                        SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor.putBoolean("swtich on", true);
                        editor.apply();
                        Sheater.setChecked(true);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("SMART_HEATER");
                        myRef.setValue("ON");
                        Toast.makeText(getApplicationContext(),"SMART HEATER ON",Toast.LENGTH_SHORT).show();  // checked..
                    }
                    else
                    {
                        SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                        editor.putBoolean("swtich on", false);
                        editor.apply();
                        Sheater.setChecked(false);

                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("SMART_HEATER");
                        myRef.setValue("OFF");
                        Toast.makeText(getApplicationContext(),"SMART HEATER OFF",Toast.LENGTH_SHORT).show();  // umchecked..


                    }
                }
            });
        //----------------SMART LIGHTS----------------------------------------------------------
        Slight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Slight.isChecked())
                {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("swtich on", true);
                    editor.apply();
                    Slight.setChecked(true);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("SMART_LIGHT");
                    myRef.setValue("ON");
                    Toast.makeText(getApplicationContext(),"SMART LIGHT ON",Toast.LENGTH_SHORT).show();  // checked..
                }
                else
                {
                    SharedPreferences.Editor editor = getSharedPreferences("save", MODE_PRIVATE).edit();
                    editor.putBoolean("swtich on", false);
                    editor.apply();
                    Slight.setChecked(false);

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("SMART_LIGHT");
                    myRef.setValue("OFF");
                    Toast.makeText(getApplicationContext(),"SMART LIGHT OFF",Toast.LENGTH_SHORT).show();  // umchecked..


                }
            }
        });
    }
}
