package com.example.glimpse;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class allcontactsactivity extends AppCompatActivity {
    TextView t1,t2;
    EditText e1;
    int num=0;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allcontactsactivity);
        t1=(TextView)findViewById(R.id.contacttextview);
        t2=(TextView)findViewById(R.id.counttextview);
        e1=(EditText)findViewById(R.id.searchedit);
        db=new DatabaseHelper(this);
        Cursor cursor = db.contacts();
        while (cursor.moveToNext()) {
            t1.setText(t1.getText() + "\n" + (cursor.getString(0) + "\n" + cursor.getString(1) + "\n" + cursor.getString(3))+"\n----------");
            num++;
        }
        String str = Integer.toString(num);
        t2.setText(""+str);
        e1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s1 = e1.getText().toString().trim().toLowerCase();
                t1.setText("");
                Cursor cursor = db.contactsimilar(s1);
                while (cursor.moveToNext()) {
                    t1.setText(t1.getText() + "\n" + (cursor.getString(0) + "\n" + cursor.getString(1) + "\n" + cursor.getString(3))+"\n----------");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s1 = e1.getText().toString().trim().toLowerCase();
                t1.setText("");
                Cursor cursor = db.contactsimilar(s1);
                while (cursor.moveToNext()) {
                    t1.setText(t1.getText() + "\n" + (cursor.getString(0) + "\n" + cursor.getString(1) + "\n" + cursor.getString(3))+"\n----------");
                }
            }
        });
    }
}
