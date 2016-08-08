/**
 * Copyright (c) Jason Brophy 2016
 */

package edu.jbrophypdx.idsolitaire;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 *
 */
public class Game extends Activity {

    static final int[] images = {
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
    protected Deck pile;
    protected Stack[] stacks;
    protected int score;
    protected boolean[] removable;
    protected int moveFrom;
    protected ImageView[] imgViews;
    protected ImageView cardBack;
    protected MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        Context context = this.getApplicationContext();
        Resources res = context.getResources();
        int dim = getScreenHeight(context);
        RelativeLayout rL = (RelativeLayout) findViewById(R.id.playField);
        rL.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) Math.round(dim * .65)));
        this.imgViews = new ImageView[52];
        this.cardBack = new ImageView(this.getApplicationContext());
        for (int i = 0; i < 52; ++i) {
            this.imgViews[i] = new ImageView(this.getApplicationContext());
            this.imgViews[i].setImageBitmap(Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(res, images[i]), dim / 10, (int) Math.round((dim / 10) * 1.452), false));
            this.imgViews[i].setVisibility(ImageView.INVISIBLE);
            this.imgViews[i].setScaleType(ImageView.ScaleType.FIT_CENTER);
        }
        Bitmap bmp = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.back), dim / 6, (int) Math.round((dim / 6) * 1.452), false);
        cardBack.setImageBitmap(bmp);
        cardBack.setVisibility(ImageView.VISIBLE);
        cardBack.invalidate();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dim / 10, (int) Math.round((dim / 10) * 1.452));
        params.topMargin = 40;
        params.leftMargin = 0;
        rL.addView(cardBack, params);
        this.pile = new Deck(imgViews);
        pile.shuffle();
        this.stacks = new Stack[4];
        for (int i = 0; i < 4; ++i)
            stacks[i] = new Stack();
        this.score = 0;
        removable = new boolean[4];
        this.mp = MediaPlayer.create(context, R.raw.shufcards);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release();
            }
        });
        this.moveFrom = -1;
    }

    //Set which columns are removable from, it may be faster to do this on each click, but
    //The load is pretty minimal (4! iterations).
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

    //This function deals the cards to the piles, updating the layout with the now visible image.
    public void dealCards(View view) {
        Card temp;
        int index;
        int offset;
        ImageView tmp;
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.playField);
        RelativeLayout.LayoutParams params;
        int dim = getScreenHeight(this.getApplicationContext());
        //The cards are dealt here, with some static values determining the size of the cards
        //by using the screen size to create the images and the locations, it prevents overflow.
        for (int i = 0; i < 4; ++i) {
            params = new RelativeLayout.LayoutParams(dim / 10, (int) Math.round(dim / 10 * 1.452));
            temp = pile.getOne();
            tmp = temp.getImageView();
            index = stacks[i].getIndex();
            offset = (1 + i) * dim / 40;
            params.setMargins(Math.round(((i + 1) * dim / 10) + offset), (index + 1) * dim / 25, 0, 0);
            rl.addView(tmp, params);
            tmp.setVisibility(View.VISIBLE);
            tmp.invalidate();
            stacks[i].insertCard(temp);
        }
        setRemovable();

        //If the pile is empty, start the end of game process, giving an end game button to replace
        //the deal cards button, when the button is clicked, bring up an alert, then end the activity.
        if (!pile.notEmpty()) {
            cardBack.setVisibility(View.INVISIBLE);
            cardBack.invalidate();
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
                    final AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
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
    }

    //End the activity, used due to alert not having access to finish inside function.
    public void endActivity() {
        this.finish();
    }

    //The following four functions correspond to their respective buttons, on the xml layout
    //they could be done as one, just getting the id of the view passed in, but I chose to have
    //the value known, since it can just be passed into a set remove function with no switch cases.
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

    //All move functions follow the same pattern (correlated to the buttons
    // on the activity screen), but to avoid excessive switch statements were split
    //into their own four instances.  On first click, the class int movefrom is updated
    //with the value of the button clicked.  The other three buttons are then updated to have
    //new text stating move to (// TODO: 8/8/2016 only update ones legally moveable to.
    //With the view passed into each being the clicked button.  On second click,
    //The stored moveFrom is used to move a card from that stack to the empty.  If not empty
    //an alert is shown stating as such.  Otherwise, an animated move is done, then all buttons
    //returned to the original state.
    public void moveC1(View view) {
        if (this.moveFrom == -1) {
            if (stacks[0].isEmpty()) {
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
            if (stacks[0].notEmpty()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is not empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                animate(moveFrom, 0);
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
            if (stacks[1].isEmpty()) {
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
            if (stacks[1].notEmpty()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is not empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                animate(moveFrom, 1);
                //this.draw();
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
            if (stacks[2].isEmpty()) {
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
            if (stacks[2].notEmpty()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is not empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                animate(moveFrom, 2);
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
            if (stacks[3].isEmpty()) {
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
            if (stacks[3].notEmpty()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
                alert.setTitle("Invalid!");
                alert.setMessage("That column is not empty!");
                alert.setPositiveButton("OK", null);
                alert.show();
            } else {
                animate(moveFrom, 3);
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

    //Move the ImageView from the top of stack moveFrom, into empty stack MoveTo.  It is left
    //at the new position once finished, so there is no need to update the overall drawing.
    //// TODO: 8/8/2016 add animation for dealing cards, and for removing cards.
    public void animate(int moveFrom, int moveTo) {
        int dim = this.getScreenHeight(this.getApplicationContext());
        int newX = Math.round((moveTo + 1) * dim / 10 + (1 + moveTo) * dim / 40);
        ImageView tmp = stacks[moveFrom].getTop().getImageView();
        stacks[moveTo].insertCard(stacks[moveFrom].remove());
        ObjectAnimator oA = ObjectAnimator.ofFloat(tmp, "X", newX);
        ObjectAnimator oA2 = ObjectAnimator.ofFloat(tmp, "Y", 0);
        oA.setDuration(1000);
        oA2.setDuration(1000);
        oA.start();
        oA2.start();
    }

    //Actually remove the card, done by subtracting the index, setting the card ImageView to invisible
    //invalidating the view.  If removable from, return true, return false if not (for button function.
    public boolean removeCard(int removeFrom) {
        if (!removable[removeFrom])
            return false;
        Card temp = stacks[removeFrom].remove();
        temp.getImageView().setVisibility(ImageView.INVISIBLE);
        temp.getImageView().invalidate();
        setRemovable();
        ++this.score;
        return true;
    }

    //Get the display height, useful for setting the size of the cards on screen.
    private int getScreenHeight(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    //A relic of the pre animation, pre imageview stage.
    /*public void draw() {
        Context context = this.getApplicationContext();
        int dim = getScreenHeight(context);
        if (this.pile.notEmpty())
            cardBack.setVisibility(ImageView.VISIBLE);
        for (int i = 0; i < 4; ++i)
            stacks[i].draw(dim, i);
        RelativeLayout rL = (RelativeLayout) findViewById(R.id.playField);
        rL.invalidate();
    }*/
}
