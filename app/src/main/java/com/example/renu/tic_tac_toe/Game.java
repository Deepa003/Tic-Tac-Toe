package com.example.renu.tic_tac_toe;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;


public class Game extends AppCompatActivity {

    int num_players;
    enum Cell {
        OPEN,
        X,
        O
    }

    enum Outcome {
        NONE,
        P1_WON,
        P2_WON,
        COMPUTER_WON,
        CAT
    }

    public boolean checkTriple(int c1, int c2, int c3, Cell value) {
        if((cells[c1] == cells[c2]) && (cells[c2] == cells[c3]) && (cells[c3] == value))
            return true;
        else
            return false;
    }

    private Cell [] cells;

    public void setupGame( ) {
        cells = new Cell [9];

        for(int i=0; i<9; i++) {
            cells[i] = Cell.OPEN;
        }
    }

    public Outcome checkGame( ) {
	     if(checkTriple(0, 1, 2, Cell.X)) return Outcome.P1_WON;
        else if(checkTriple(0, 1, 2, Cell.O)) {
            return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
        }

	    if(checkTriple(3, 4, 5, Cell.X)) return Outcome.P1_WON;
        else if(checkTriple(3, 4, 5, Cell.O)) {
            return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
        }

		if(checkTriple(6, 7, 8, Cell.X)) return Outcome.P1_WON;
        else if(checkTriple(6, 7, 8, Cell.O)) {
            return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
        }

		if(checkTriple(0, 3, 6, Cell.X)) return Outcome.P1_WON;
        else if(checkTriple(0, 3, 6, Cell.O)) {
            return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
        }

		if(checkTriple(1, 4, 7, Cell.X)) return Outcome.P1_WON;
        else if(checkTriple(1, 4, 7, Cell.O)) {
            return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
        }

		if(checkTriple(2, 5, 8, Cell.X)) return Outcome.P1_WON;
        else if(checkTriple(2, 5, 8, Cell.O)) {
            return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
        }

		if(checkTriple(2, 4, 6, Cell.X)) return Outcome.P1_WON;
        else if(checkTriple(2, 4, 6, Cell.O)) {
            return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
        }

		if(checkTriple(0, 4, 8, Cell.X)) return Outcome.P1_WON;
        else if(checkTriple(0, 4, 8, Cell.O)) {
            return (num_players == 1) ? Outcome.COMPUTER_WON : Outcome.P2_WON;
        }

		boolean cats = true;
        for(int i = 0; i < 9; i++) {
            if(cells[i] == Cell.OPEN)
                cats = false;
        }

        if(cats)
            return Outcome.CAT;
        else
            return Outcome.NONE;
    }

    public void finishGame(Outcome out, int call_no, String p1_name, String p2_name) {

       final TextView status2 = (TextView) findViewById(R.id.TextView01);

       if(call_no == 1) {
		    status2.setText(status.getText());

           Winners diag = new Winners(this, out, 1, "", num_players);
           diag.show();

           //String s=status2.getText().toString();
           //ContentValues values=new ContentValues();
           //values.put("name",s);

           return;
       }
       else if(call_no == 2) {
           if(num_players == 2) {
				/* this dialog will call back with call_no = 3 and p1_name and p2_name in place*/
               //Winners diag = new Winners(this, out, 2, p1_name, num_players);
               //diag.show( );
               //return;
           }
       }



		if(num_players==2) p2_name="Player 2";
                else
        if(num_players==1) p2_name = "Computer";

		String winner = (out == Outcome.P1_WON) ? p1_name : p2_name;
        String loser = ((out == Outcome.P2_WON) || (out == Outcome.COMPUTER_WON)) ? p1_name : p2_name;
        if(out == Outcome.CAT)
            status2.setText("It's a tie game!!");
        else
            status2.setText(winner + " beat " + loser);


        finish();

    }

    public void update(int cell, int turn) {
	    cells[cell] = ((turn % 2) == 0) ? Cell.X : Cell.O;

	    Outcome o;
        switch(o = checkGame( )) {

            case P1_WON:
                status.setText("Player 1 Won!!");
                Winners diag = new Winners(this, o, 1, "", num_players);
                diag.show();


                //finishGame(o, 1, "Player 1", "");
                return;


            case P2_WON:
                status.setText("Player 2 Won!!");
                Winners diag1 = new Winners(this, o, 1, "", num_players);
                diag1.show();
            //    finishGame(o, 1, "Player 1", "Player 2");
                return;


            case COMPUTER_WON:
                status.setText("You Have Lost!!");
                //finishGame(o, 1, "Player 1", "");


                final AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setMessage("Would you like to continue??");
                alert.setCancelable(false);
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(getIntent());

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent newIntent = new Intent(Game.this, MainActivity.class);
                        startActivity(newIntent);
                        finish();
                }
            });


                AlertDialog alertBox = alert.create();
                alertBox.show();
                break;
            case CAT:
                status.setText("It's a tied Game!!");
                //finishGame(o, 1, "", "");
                final AlertDialog.Builder alert1 = new AlertDialog.Builder(this);
                alert1.setMessage("Would you like to continue??");
                alert1.setCancelable(false);
                alert1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(getIntent());

                    }
                });
                alert1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent newIntent = new Intent(Game.this, MainActivity.class);
                        startActivity(newIntent);
                        finish();
                    }
                });


                AlertDialog alertBox1 = alert1.create();
                alertBox1.show();
                break;
            case NONE:
                status.setText(((turn % 2) == 0) ? "O's Turn" : "X's Turn");
                break;
        }

	    if((num_players == 1) && ((turn % 2) == 0) && (o == Outcome.NONE))
            doAi();
    }
    public boolean canWin(int index, Cell player) {
	    Cell old = cells[index];


	       cells[index] = player;

        boolean can;
        Outcome out = checkGame( );
        if((out == Outcome.NONE) || (out == Outcome.CAT))
            can = false;
        else
            can = true;

        cells[index] = old;

        return can;
    }
    public int rankMove(int index) {
		if(cells[index] != Cell.OPEN)
            return 0;

		if(canWin(index, Cell.O))
            return 100;

		if(canWin(index, Cell.X))
            return 50;

		if(index == 4)
            return 25;

		if((index == 0) || (index == 2) || (index == 6) || (index == 8))
            return 10;

		return 5;

    }
    public void doAi( ) {
        int rankings [] = new int[9];

		for(int i = 0; i < 9; i++) {
            if(cells[i] == Cell.OPEN) {
                rankings[i] = rankMove(i);
            }
        }

		int best_ranking = 0;
        for(int i = 0; i < 9; i++) {
            if(rankings[i] > rankings[best_ranking])
                best_ranking = i;
        }
        buttons[best_ranking].button.performClick( );
    }
    private AnimatedButton buttons [] = new AnimatedButton [9];

    public TextView status;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        setupGame();
        status = (TextView) findViewById(R.id.TextView01);
ImageButton bt=(ImageButton)findViewById(R.id.ibtn);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Game.this,MainActivity.class);
                startActivity(i);
            }
        });
        if(getIntent( ).getType( ).equalsIgnoreCase("1"))
            num_players = 1;

        else
            num_players = 2;

        status.setText("X's Turn");


        /* setup buttons */
        int button_ids [] = {R.id.ImageButton01, R.id.ImageButton02, R.id.ImageButton03,
                R.id.ImageButton04, R.id.ImageButton05, R.id.ImageButton06,
                R.id.ImageButton07, R.id.ImageButton08, R.id.ImageButton09};

        for(int i=0; i<9; i++) {
            buttons[i] = new AnimatedButton((ImageButton) findViewById(button_ids[i]), this, i);

        }
    }
    }


class AnimatedButton {

    public ImageButton button;
    Game the_game;
    int cell_number;
    boolean taken;

    static Object animation_mutex = new Object( );

    final Handler animation_handler = new Handler( );

    final Runnable update_gui = new Runnable() {
        public void run() {
            setImage( );
        }
    };

    static final long DELAY_MS = 125;

    int stage;

    static int global_turn = 0;

    int turn;

    public void setImage( ) {
        if((turn % 2) == 0) {
            switch(stage) {
                case 0: button.setImageResource(R.drawable.x0); break;
                case 1:	button.setImageResource(R.drawable.x1);	break;
                case 2:	button.setImageResource(R.drawable.x2);	break;
                case 3: button.setImageResource(R.drawable.x3); break;
                case 4:	button.setImageResource(R.drawable.x4);	break;
                case 5:	button.setImageResource(R.drawable.x5);	break;
                case 6: button.setImageResource(R.drawable.x6); break;
                case 7:	button.setImageResource(R.drawable.x7);	break;
            }
        } else {
            switch(stage) {
                case 0:	button.setImageResource(R.drawable.o0);	break;
                case 1:	button.setImageResource(R.drawable.o1);	break;
                case 2:	button.setImageResource(R.drawable.o2);	break;
                case 3:	button.setImageResource(R.drawable.o3);	break;
                case 4:	button.setImageResource(R.drawable.o4);	break;
                case 5:	button.setImageResource(R.drawable.o5);	break;
                case 6:	button.setImageResource(R.drawable.o6);	break;
                case 7:	button.setImageResource(R.drawable.o7);	break;
            }
        }
    }

    AnimatedButton(ImageButton b, Game g, int cell) {
	     button = b;
        the_game = g;
        cell_number = cell;
        global_turn = 0;
        taken = false;

	    button.setOnClickListener(new Button.OnClickListener( ) {
            public void onClick(View v) {

                if(taken) {
                    the_game.status.setText("This Cell is Already Taken");
                    return;
                }
                taken = true;

            	synchronized(animation_mutex) {
    			    turn = global_turn;
                    global_turn++;

                    Thread animation_thread = new Thread() {
                        public void run() {
            	            for(stage = 0; stage<8; stage++) {
                                animation_handler.post(update_gui);
                                SystemClock.sleep(DELAY_MS);
                            }
                        }
                    };
                    animation_thread.start();

            	    the_game.update(cell_number, turn);
                }
            }
        });
    }
}

