package com.example.glimpse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class adactivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adactivity);
        db=new DatabaseHelper(this);
        e1=(EditText)findViewById(R.id.ename);
        e2=(EditText)findViewById(R.id.ewhatsapp);
        e3=(EditText)findViewById(R.id.ealternate);
        e4=(EditText)findViewById(R.id.eemail);
        e5=(EditText)findViewById(R.id.elinkedin);
        e6=(EditText)findViewById(R.id.eworking);
        e7=(EditText)findViewById(R.id.eworkingcity);
        e8=(EditText)findViewById(R.id.eaddress1);
        e9=(EditText)findViewById(R.id.eaddress2);
        b1=(Button)findViewById(R.id.aduser);
        b2=(Button)findViewById(R.id.adtocontact);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = e1.getText().toString().trim().toLowerCase();
                String s2 = e2.getText().toString().trim().toLowerCase();
                String s3 = e3.getText().toString().trim().toLowerCase();
                String s4 = e4.getText().toString().trim().toLowerCase();
                String s5 = e5.getText().toString().trim().toLowerCase();
                String s6 = e6.getText().toString().trim().toLowerCase();
                String s7 = e7.getText().toString().trim().toLowerCase();
                String s8 = e8.getText().toString().trim().toLowerCase();
                String s9 = e9.getText().toString().trim().toLowerCase();
                String s10 = s8+" "+s9;
                if(s1.equals("")){
                    Toast.makeText(getApplicationContext(),"ENTER NAME",Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean chkname = db.chkname(s1);
                    if(chkname==true){
                        Boolean insert = db.insert(s1,s2,s3,s4,s5,s6,s7,s10);
                        if(insert==true){
                            Toast.makeText(getApplicationContext(),"USER ADDED SUCCESSFULLY",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"ALREADY USER NAME EXIST",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
                contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                contactIntent
                        .putExtra(ContactsContract.Intents.Insert.NAME,e1.getText().toString())
                        .putExtra(ContactsContract.Intents.Insert.PHONE,e2.getText().toString());
                startActivity(contactIntent);
            }
        });
    }
}
