/**
 * Copyright (c) Jason Brophy 2016
 */

package edu.jbrophypdx.idsolitaire;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

/**
 *
 */
public class Game extends Activity {

    protected Deck pile;
    protected Stack[] stacks;
    protected int score;
    protected boolean[] removable;
    protected ImageView view;
    protected Bitmap draw;
    protected Canvas canvas;
    protected int moveFrom;
    protected ImageView [] imgViews;

    static int images[] = {
            R.drawable.s2,
            R.drawable.s3,
            R.drawable.s4,
            R.drawable.s5,
            R.drawable.s6,
            R.drawable.s7,
            R.drawable.s8,
            R.drawable.s9,
            R.drawable.s10,
            R.drawable.sj,
            R.drawable.sq,
            R.drawable.sk,
            R.drawable.sa,
            R.drawable.d2,
            R.drawable.d3,
            R.drawable.d4,
            R.drawable.d5,
            R.drawable.d6,
            R.drawable.d7,
            R.drawable.d8,
            R.drawable.d9,
            R.drawable.d10,
            R.drawable.dj,
            R.drawable.dq,
            R.drawable.dk,
            R.drawable.da,
            R.drawable.h2,
            R.drawable.h3,
            R.drawable.h4,
            R.drawable.h5,
            R.drawable.h6,
            R.drawable.h7,
            R.drawable.h8,
            R.drawable.h9,
            R.drawable.h10,
            R.drawable.hj,
            R.drawable.hq,
            R.drawable.hk,
            R.drawable.ha,
            R.drawable.c2,
            R.drawable.c3,
            R.drawable.c4,
            R.drawable.c5,
            R.drawable.c6,
            R.drawable.c7,
            R.drawable.c8,
            R.drawable.c9,
            R.drawable.c10,
            R.drawable.cj,
            R.drawable.cq,
            R.drawable.ck,
            R.drawable.ca,
            R.drawable.bj,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        this.view = (ImageView) findViewById(R.id.custom);

        Context context = this.getApplicationContext();
        Resources res = context.getResources();
        int dim = getScreenHeight(context);
        this.draw = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(draw);
        this.imgViews = new ImageView[52];
        for(int i = 0; i < 52; ++i){
            this.imgViews[i] = new ImageView(Game.this);
            this.imgViews[i].setImageBitmap(Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(res, images[i]), dim / 6, (int)Math.round((dim/6)*1.452), false)));
        }
        this.pile = new Deck(res, imgViews, dim, dim);
        pile.shuffle();
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.shufcards);
        mediaPlayer.start();
        this.stacks = new Stack[4];
        for (int i = 0; i < 4; ++i)
            stacks[i] = new Stack();
        this.score = 0;
        removable = new boolean[4];
        dealCards();
        view.draw(canvas);
        view.invalidate();
        this.moveFrom = -1;
    }

    public void setRemovable() {
        for (int i = 0; i < 4; ++i) {
            if (stacks[i].getTop() == null) {
                removable[i] = false;
                continue;
            }

            for (int j = 0; j < 4; ++j) {
                if (i != j && stacks[j] != null) {
                    if (removable[i] = stacks[i].getTop().canRemove(stacks[j].getTop()))
                        break;
                }
            }
        }
    }

    public void dealCards(View view) {

        if (!this.dealCards()) {
            if (!this.pile.notEmpty()) {
                for (int i = 0; i < 4; ++i) {
                    if (removable[i]) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                        alert.setTitle("Out of Cards!");
                        alert.setMessage("There are still valid moves however.");
                        alert.setPositiveButton("OK", null);
                        alert.show();
                        return;
                    }
                }
            }
        }
    }

    public boolean dealCards() {
        if (!pile.notEmpty())
            return false;
        for (int i = 0; i < 4; ++i)
            stacks[i].insertCard(pile.getOne());
        setRemovable();
        this.draw();
        if (!pile.notEmpty()) {
            Button dC = (Button) findViewById(R.id.button);
            dC.setText(R.string.endGame);
            dC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String end;
                    if (score == 48)
                        end = "You win!";
                    else
                        end = "Your score was " + Integer.toString(score) + " out of 48";
                    AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                    alert.setTitle("Game Over");
                    alert.setMessage(end);
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            endActivity();
                        }
                    });
                    alert.show();
                }
            });
            dC.invalidate();
        }
        return true;
    }

    public void endActivity() {
        this.finish();
    }

    public void removeC1(View view) {
        if (!removeCard(0)) {
            //Code grabbed and edited from
            //http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
            alert.setTitle("Invalid!");
            alert.setMessage("That is not a valid removal");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    public void removeC2(View view) {
        if (!removeCard(1)) {
            //Code grabbed and edited from
            //http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
            alert.setTitle("Invalid!");
            alert.setMessage("That is not a valid removal");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    public void removeC3(View view) {
        if (!removeCard(2)) {
            //Code grabbed and edited from
            //http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
            alert.setTitle("Invalid!");
            alert.setMessage("That is not a valid removal");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    public void removeC4(View view) {
        if (!removeCard(3)) {
            //Code grabbed and edited from
            //http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
            alert.setTitle("Invalid!");
            alert.setMessage("That is not a valid removal");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    public void moveC1(View view) {
        if (this.moveFrom == -1) {
            if (!stacks[0].canMoveFrom()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
                return;
            }
            this.moveFrom = 0;
            Button bC2 = (Button) findViewById(R.id.moveC2);
            Button bC3 = (Button) findViewById(R.id.moveC3);
            Button bC4 = (Button) findViewById(R.id.moveC4);
            bC2.setText(getString(R.string.mtC2));
            bC3.setText(getString(R.string.mtC3));
            bC4.setText(getString(R.string.mtC4));
            bC2.invalidate();
            bC3.invalidate();
            bC4.invalidate();
        } else {
            if (!stacks[0].canMoveTo()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is not empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                stacks[0].insertCard(stacks[moveFrom].remove());
                this.draw();
                this.setRemovable();
            }
            this.moveFrom = -1;
            Button bC2 = (Button) findViewById(R.id.moveC2);
            Button bC3 = (Button) findViewById(R.id.moveC3);
            Button bC4 = (Button) findViewById(R.id.moveC4);
            Button v = (Button) view;
            bC2.setText(getString(R.string.mfC2));
            bC3.setText(getString(R.string.mfC3));
            bC4.setText(getString(R.string.mfC4));
            v.setText(getString(R.string.mfC1));
            bC2.invalidate();
            bC3.invalidate();
            bC4.invalidate();
            v.invalidate();
        }
    }

    public void moveC2(View view) {
        if (this.moveFrom == -1) {
            if (!stacks[1].canMoveFrom()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
                return;
            }
            this.moveFrom = 1;
            Button bC1 = (Button) findViewById(R.id.moveC1);
            Button bC3 = (Button) findViewById(R.id.moveC3);
            Button bC4 = (Button) findViewById(R.id.moveC4);
            bC1.setText(getString(R.string.mtC1));
            bC3.setText(getString(R.string.mtC3));
            bC4.setText(getString(R.string.mtC4));
            bC1.invalidate();
            bC3.invalidate();
            bC4.invalidate();
        } else {
            if (!stacks[1].canMoveTo()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is not empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                stacks[1].insertCard(stacks[moveFrom].remove());
                this.draw();
                this.setRemovable();
            }
            this.moveFrom = -1;
            Button bC1 = (Button) findViewById(R.id.moveC1);
            Button bC3 = (Button) findViewById(R.id.moveC3);
            Button bC4 = (Button) findViewById(R.id.moveC4);
            Button v = (Button) view;
            bC1.setText(getString(R.string.mfC1));
            bC3.setText(getString(R.string.mfC3));
            bC4.setText(getString(R.string.mfC4));
            v.setText(getString(R.string.mfC2));
            bC1.invalidate();
            bC3.invalidate();
            bC4.invalidate();
            view.invalidate();
        }
    }

    public void moveC3(View view) {
        if (this.moveFrom == -1) {
            if (!stacks[2].canMoveFrom()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
                return;
            }
            this.moveFrom = 2;
            Button bC1 = (Button) findViewById(R.id.moveC1);
            Button bC2 = (Button) findViewById(R.id.moveC2);
            Button bC4 = (Button) findViewById(R.id.moveC4);
            bC1.setText(getString(R.string.mtC1));
            bC2.setText(getString(R.string.mtC2));
            bC4.setText(getString(R.string.mtC4));
            bC1.invalidate();
            bC2.invalidate();
            bC4.invalidate();
        } else {
            if (!stacks[2].canMoveTo()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is not empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                stacks[2].insertCard(stacks[moveFrom].remove());
                this.draw();
                this.setRemovable();
            }
            this.moveFrom = -1;
            Button bC1 = (Button) findViewById(R.id.moveC1);
            Button bC2 = (Button) findViewById(R.id.moveC2);
            Button bC4 = (Button) findViewById(R.id.moveC4);
            Button v = (Button) view;
            bC1.setText(getString(R.string.mfC1));
            bC2.setText(getString(R.string.mfC2));
            bC4.setText(getString(R.string.mfC4));
            v.setText(getString(R.string.mfC3));
            bC1.invalidate();
            bC2.invalidate();
            bC4.invalidate();
            v.invalidate();
        }
    }

    public void moveC4(View view) {
        if (this.moveFrom == -1) {
            if (!stacks[3].canMoveFrom()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
                return;
            }
            this.moveFrom = 3;
            Button bC2 = (Button) findViewById(R.id.moveC2);
            Button bC3 = (Button) findViewById(R.id.moveC3);
            Button bC1 = (Button) findViewById(R.id.moveC1);
            bC2.setText(getString(R.string.mtC2));
            bC3.setText(getString(R.string.mtC3));
            bC1.setText(getString(R.string.mtC1));
            bC2.invalidate();
            bC3.invalidate();
            bC1.invalidate();
        } else {
            if (!stacks[3].canMoveTo()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is not empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                stacks[3].insertCard(stacks[moveFrom].remove());
                this.draw();
                this.setRemovable();
            }
            this.moveFrom = -1;
            Button bC1 = (Button) findViewById(R.id.moveC1);
            Button bC2 = (Button) findViewById(R.id.moveC2);
            Button bC3 = (Button) findViewById(R.id.moveC3);
            Button v = (Button) view;
            bC1.setText(getString(R.string.mfC1));
            bC2.setText(getString(R.string.mfC2));
            bC3.setText(getString(R.string.mfC3));
            v.setText(getString(R.string.mfC4));
            bC1.invalidate();
            bC2.invalidate();
            bC3.invalidate();
            v.invalidate();
        }
    }

    public boolean removeCard(int removeFrom) {
        if (removable[removeFrom]) {
            stacks[removeFrom].remove();
            setRemovable();
            Context context = this.getApplicationContext();
            int dim = getScreenHeight(context);
            this.draw = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888);
            this.canvas = new Canvas(draw);
            this.draw();
            ++this.score;
            return true;
        } else return false;
    }

    private int getScreenHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public void draw() {
        Context context = this.getApplicationContext();
        int dim = getScreenHeight(context);
        this.draw = Bitmap.createBitmap(dim, dim, Bitmap.Config.ARGB_8888);
        canvas.setBitmap(this.draw);
        if (this.pile.notEmpty())
            canvas.drawBitmap(Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.back), dim/6, (int)Math.round((dim/6)*1.452), false), 0, 40, null);
        for (int i = 0; i < 4; ++i)
            stacks[i].draw(canvas, dim, dim, i);
        view.setImageBitmap(draw);
        view.invalidate();
    }
}
