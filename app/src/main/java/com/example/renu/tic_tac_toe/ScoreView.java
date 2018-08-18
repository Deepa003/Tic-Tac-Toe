package com.example.renu.tic_tac_toe;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.text.NumberFormat;

/**
 * Created by renu on 10/08/16.
 */
public class ScoreView extends Activity {

    Button button,clr;
    TextView textView;
    ListView lv;
    ArrayAdapter<String>  aa;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scoreview);
        textView = (TextView) findViewById(R.id.tname);
        button = (Button) findViewById(R.id.br);
        lv = (ListView) findViewById(R.id.listView);
        aa=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1);
        clr=(Button)findViewById(R.id.clear);
        clr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver cr=getContentResolver();
                String URL = "content://com.example.renu.tic_tac_toe/students";

                Uri uri = Uri.parse(URL);
                cr.delete(uri,null,null);
                aa.clear();



            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URL = "content://com.example.renu.tic_tac_toe/students";

                Uri students = Uri.parse(URL);
                Cursor c = managedQuery(students, null, null, null, "name");
                if (c.moveToFirst()) {
                    do {
                        String m = c.getString(c.getColumnIndex(ScoreDatabase.NAME));
                        aa.add(m);
                        lv.setAdapter(aa);

                    }
                    while (c.moveToNext());
                }
            }

        });

    }


}
