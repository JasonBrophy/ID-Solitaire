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

    public void startGame(View view){
        Intent intent = new Intent(this, Game.class);
        startActivity(intent);
    }
}
