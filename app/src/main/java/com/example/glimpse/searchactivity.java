package com.example.glimpse;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class searchactivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText e1;
    Button b1,share;
    RadioGroup R1;
    RadioButton rb1,rb2,rb3,rb4,rb5,rb6;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchactivity);
        db = new DatabaseHelper(this);
        e1 = (EditText)findViewById(R.id.eentername);
        b1 = (Button)findViewById(R.id.getdetail);
        share = (Button)findViewById(R.id.sharebutton);
        R1 = (RadioGroup)findViewById(R.id.catradiogroup);
        rb1=(RadioButton)findViewById(R.id.crbworking);
        rb2=(RadioButton)findViewById(R.id.crbworkingcity);
        rb3=(RadioButton)findViewById(R.id.rbalternate);
        rb4=(RadioButton)findViewById(R.id.rbemail);
        rb5=(RadioButton)findViewById(R.id.rblinkedin);
        rb6=(RadioButton)findViewById(R.id.rbaddress);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str1 = e1.getText().toString().trim().toLowerCase();
                if(str1.equals("")){
                    Toast.makeText(getApplicationContext(),"PLEASE ENTER NAME",Toast.LENGTH_LONG).show();
                }
                else{
                    Boolean chkname = db.chkname(str1);
                    if(chkname==true){
                        Toast.makeText(getApplicationContext(), "USER NOT FOUND", Toast.LENGTH_LONG).show();
                    }
                    else{
                        Cursor cursor = db.alldata(str1);
                        while (cursor.moveToNext()) {
                            //Toast.makeText(getApplicationContext(), "RESULT:" + cursor.getString(+flag), Toast.LENGTH_LONG).show();
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            String shareBody = "Contact Detail\nName:"+str1+"\nWhatsapp Num:"+cursor.getString(1)+"\nEmail:"+cursor.getString(3)+"\nLinkedIn:"+cursor.getString(4)+"\nWorking:"+cursor.getString(5);
                            String shareSub = "";
                            sharingIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
                            sharingIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
                            startActivity(Intent.createChooser(sharingIntent,"Share Using"));
                        }
                        //Toast.makeText(getApplicationContext(), "USER NOT FOUND"+flag, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s1 = e1.getText().toString().trim().toLowerCase();
                int selectedid = R1.getCheckedRadioButtonId();
                if(selectedid!=-1) {
                    RadioButton radioButton = (RadioButton) R1.findViewById(selectedid);
                    String str1 = radioButton.getText().toString();
                    if (str1.equals("Company")) {
                        flag = 5;
                    } else if (str1.equals("Whatsapp Number")) {
                        flag = 1;
                    } else if (str1.equals("Alternate Number")) {
                        flag = 2;
                    } else if (str1.equals("Email")) {
                        flag = 3;
                    } else if (str1.equals("LinkedIn")) {
                        flag = 4;
                    } else if (str1.equals("Address")) {
                        flag = 7;
                    }
                    if(s1.equals("")){
                        Toast.makeText(getApplicationContext(), "ENTER NAME", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Boolean chkname = db.chkname(s1);
                        if (chkname == true) {
                            Toast.makeText(getApplicationContext(), "USER NOT FOUND", Toast.LENGTH_LONG).show();
                        } else {
                            Cursor cursor = db.alldata(s1);
                            StringBuffer buffer = new StringBuffer();
                            while (cursor.moveToNext()) {
                                if(flag==5){
                                    buffer.append("" + cursor.getString(+flag));
                                    buffer.append("-" + cursor.getString(6));
                                }
                                else {
                                    buffer.append("" + cursor.getString(+flag));
                                }
                            }
                            showmsg(buffer.toString(),flag);
                        }
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "SELECT OPTION", Toast.LENGTH_LONG).show();
            }
        });
    }
    public void showmsg(final String message, int flag){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Details");
        builder.setMessage(message);
        builder.setPositiveButton("Copy Text", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("copied", ""+message);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(getApplicationContext(),"Copied", Toast.LENGTH_LONG).show();
                builder.setCancelable(true);
            }
        });
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                builder.setCancelable(true);
            }
        });
        builder.show();
    }
}
