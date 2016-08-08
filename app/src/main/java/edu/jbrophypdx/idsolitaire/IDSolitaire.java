/**
 * Copyright (c) Jason Brophy 2016
 */
package edu.jbrophypdx.idsolitaire;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class IDSolitaire extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_idsolitaire);
    }

    //Start the game
    public void startGame(View view) {
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }

    //Go to the instructions activity.
    public void instructions(View view) {
        Intent intent = new Intent(this, Instructions.class);
        startActivity(intent);
    }
}
