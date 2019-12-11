package com.example.glimpse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class updateactivity extends AppCompatActivity {
    EditText e1,e2;
    Button b1;
    DatabaseHelper db;
    RadioGroup UR1;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateactivity);
        db=new DatabaseHelper(this);
        UR1=(RadioGroup)findViewById(R.id.catradiogroup);
        e1=(EditText)findViewById(R.id.edname);
        e2=(EditText)findViewById(R.id.edalter);
        b1=(Button)findViewById(R.id.updatebutton);
        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = e1.getText().toString().trim().toLowerCase();
                String s2 = e2.getText().toString().trim().toLowerCase();
                int selectedid = UR1.getCheckedRadioButtonId();
                if(selectedid!=-1){
                    RadioButton radioButton = (RadioButton)UR1.findViewById(selectedid);
                    String str1 = radioButton.getText().toString();
                    if(str1.equals("Company")){
                        flag=5;
                    }
                    else if(str1.equals("Whatsapp Number")){
                        flag=1;
                    }
                    else if(str1.equals("Alternate Number")){
                        flag=2;
                    }
                    else if(str1.equals("Email")){
                        flag=3;
                    }
                    else if(str1.equals("Working City")){
                        flag=6;
                    }
                    else if(str1.equals("Address")){
                        flag=7;
                    }
                    Boolean chkname = db.chkname(s1);
                    if(chkname==true){
                        vibe.vibrate(100);
                        Toast.makeText(getApplicationContext(),"USER NOT FOUND",Toast.LENGTH_LONG).show();
                    }
                    else{
                        db.update(s1,s2,flag);
                        //if(update==true) {
                        Toast.makeText(getApplicationContext(), "UPDATED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                        //}
                    }
                }
                else{
                    vibe.vibrate(100);
                    Toast.makeText(getApplicationContext(), "SELECT OPTION", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
