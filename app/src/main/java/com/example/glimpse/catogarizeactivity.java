package com.example.glimpse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class catogarizeactivity extends AppCompatActivity {
    EditText e1;
    RadioGroup Catrg;
    Button b1;
    DatabaseHelper db;
    String[] detail = new String[10];
    RadioButton rb1,rb2;
    int flag,j=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catogarizeactivity);


        db = new DatabaseHelper(this);
        e1=(EditText)findViewById(R.id.cedname);
        b1=(Button)findViewById(R.id.catbutton);
        Catrg=(RadioGroup)findViewById(R.id.catradiogroup);
        rb1=(RadioButton)findViewById(R.id.crbworking);
        rb2=(RadioButton)findViewById(R.id.crbworkingcity);
        final Vibrator vibe = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = e1.getText().toString().trim().toLowerCase();
                int selectedid = Catrg.getCheckedRadioButtonId();

                if(selectedid!=-1){
                    RadioButton radioButton = (RadioButton)Catrg.findViewById(selectedid);
                    String str1 = radioButton.getText().toString();

                    if(str1.equals("Working Company")){
                        flag=5;
                    }
                    else{
                        flag=6;
                    }

                    if(s1.equals("")){
                        vibe.vibrate(100);
                        Toast.makeText(getApplicationContext(), "ENTER NAME", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Cursor cursor = db.getdetailsby(flag, s1);
                        StringBuffer buffer = new StringBuffer();
                        while (cursor.moveToNext()) {
                            //Toast.makeText(getApplicationContext(), "RESULT:" + cursor.getString(0), Toast.LENGTH_LONG).show();
                            buffer.append("Name:" + cursor.getString(0) + "\n");
                            buffer.append("Number:" + cursor.getString(1) + "\n\n");
                        }
                        showmsg(buffer.toString());
                    }
                }
                else{
                    vibe.vibrate(100);
                    Toast.makeText(getApplicationContext(), "SELECT OPTION", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void showmsg(final String message){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Details");
        builder.setMessage(message);

        builder.setPositiveButton("Share", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = message;
                String shareSub = "";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                startActivity(Intent.createChooser(sharingIntent,"Share Using"));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });
        builder.show();
    }
}

