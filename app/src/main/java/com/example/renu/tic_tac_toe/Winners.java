package com.example.renu.tic_tac_toe;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.renu.tic_tac_toe.Game.Outcome;

/**
 * Created by renu on 09/08/16.
 */
public class Winners extends Dialog {

    String name;
    Game parent;

    Button button;
    TextView heading;
    EditText entry;
    public Winners(final Context context, final Outcome out, final int player_number, final String prev_name, int np) {
        super(context);
        this.setContentView(R.layout.winners);
        final Winners me = this;
        parent = (Game) context;

		 setCancelable(false);

	  entry = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.bsubmit);
        heading = (TextView) findViewById(R.id.tview);

		 entry.setText("");

		String h = new String( );
        if(np == 1) h = "Player";

        heading.setText( " Please Enter Your Name:");

        button.setOnClickListener(new View.OnClickListener( ) {
            public void onClick(View v) {
                name = entry.getText( ).toString( );
                if(name.isEmpty()){
                    Toast.makeText(getContext(),"name required",Toast.LENGTH_SHORT).show();
                }
                else {
            	     if (player_number == 1) {
                        parent.finishGame(out, player_number + 1, name, "");
                    } else {
                        parent.finishGame(out, player_number + 1, prev_name, name);
                    }
                    me.dismiss();
                    ContentValues values = new ContentValues();

                    values.put(ScoreDatabase.NAME, name);

                  Uri uri=getContext().getContentResolver().insert(ScoreDatabase.CONTENT_URI, values);


                }
            }
        });

    }
}
