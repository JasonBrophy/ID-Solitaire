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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;

import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Game extends Activity {

    protected Deck pile;
    protected Stack[] stacks;
    protected int score;
    protected boolean [] removable;
    protected ImageView view;
    protected Bitmap draw;
    protected Canvas canvas;

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
            //R.drawable.rj
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        this.view = (ImageView) findViewById(R.id.custom);

        Context context = this.getApplicationContext();
        Resources res = context.getResources();
        int height = getScreenHeight(context);
        int width = getScreenWidth(context);
        this.draw = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        this.canvas = new Canvas(draw);
        this.pile = new Deck(res, images, height, width);
        pile.shuffle();
        this.stacks = new Stack [4];
        for(int i = 0; i < 4; ++i)
            stacks[i] = new Stack();
        this.score = 0;
        removable = new boolean[4];
        dealCards();
        view.draw(canvas);
        view.invalidate();
        //this.play();

    }


    public void play(){
        setRemovable();
        while(isLegalMove() && pile.notEmpty())
            dealCards();
    }

    public void setRemovable(){
        for(int i = 0; i < 4; ++i){
            if(stacks[i].getTop() == null){
                removable[i] = false;
                continue;
            }

            for(int j = 0; j < 4; ++j){
                if(i == j || stacks[j] == null);
                else if(removable[i] = stacks[i].getTop().canRemove(stacks[j].getTop()))
                    break;
            }
        }
    }

    public boolean isLegalMove(){
        for(int i = 0; i < 4; ++i){
            if(removable[i])
                return true;
        }
        return false;
    }

    public boolean isLegalMove(int removeFrom){
        removeFrom = removeFrom % 4;
        return removable[removeFrom];
    }

    public void dealCards(View view){
        this.dealCards();
    }
    public boolean dealCards() {
        if (!pile.notEmpty())
            return false;
        for(int i = 0; i < 4; ++i)
            stacks[i].insertCard(pile.getOne());
        setRemovable();
        this.draw();
        return true;
    }

    public void removeC1(View view){
        if(removeCard(0))
            return;
        else{
            //Code grabbed and edited from
            //http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
            alert.setTitle("Invalid!");
            alert.setMessage("That is not a valid removal");
            alert.setPositiveButton("OK",null);
            alert.show();
        }
    }
    public void removeC2(View view){
        if(removeCard(1))
            return;
        else{
            //Code grabbed and edited from
            //http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
            alert.setTitle("Invalid!");
            alert.setMessage("That is not a valid removal");
            alert.setPositiveButton("OK",null);
            alert.show();
        }
    }
    public void removeC3(View view){
        if(removeCard(2))
            return;
        else{
            //Code grabbed and edited from
            //http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
            alert.setTitle("Invalid!");
            alert.setMessage("That is not a valid removal");
            alert.setPositiveButton("OK",null);
            alert.show();
        }
    }
    public void removeC4(View view) {
        if (removeCard(3))
            return;
        else {
            //Code grabbed and edited from
            //http://stackoverflow.com/questions/26097513/android-simple-alert-dialog
            AlertDialog.Builder alert = new AlertDialog.Builder(Game.this);
            alert.setTitle("Invalid!");
            alert.setMessage("That is not a valid removal");
            alert.setPositiveButton("OK", null);
            alert.show();
        }
    }

    public boolean removeCard(int removeFrom){
        if(removable[removeFrom]) {
            stacks[removeFrom].remove();
            setRemovable();

            Context context = this.getApplicationContext();
            int height = getScreenHeight(context);
            int width = getScreenWidth(context);
            this.draw = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            this.canvas = new Canvas(draw);
            this.draw();
            return true;
        }
        else return false;
    }
    public boolean moveCard() {
        //setRemovable();
        return true;
    }
    public void displaySituation(){
        int [] indexes = new int [4];
        int sum;
        int j;
        for(int i = 0; i < 4; ++i){
            indexes[i] = stacks[i].getIndex();
        }
        while(true){
            sum = -4;
            j = 0;
            while(j < 4) {
                if (indexes[j] == -1) {
                    System.out.print(" empty ");
                    ++j;
                    continue;
                }
                stacks[j].displayLoc(indexes[j]);
                sum += indexes[j]--;
                ++j;
            }
            System.out.println();
            if(sum == -4) return;
        }

    }

    private int getScreenHeight(Context context) {
        int height;
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        height = size.y;

        return height;
    }

    private int getScreenWidth(Context context) {
        int width;


        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;

        return width;
    }

    public void draw(){
        Context context = this.getApplicationContext();
        int height = getScreenHeight(context);
        int width = getScreenWidth(context);
        canvas.drawBitmap(Bitmap.createScaledBitmap(
                BitmapFactory.decodeResource(context.getResources(), R.drawable.bj), width/6, height/8, false), 0, 40, null);
        for(int i = 0; i < 4; ++i)
            stacks[i].draw(view, canvas, height, width, i);
        view.setImageBitmap(draw);
        view.invalidate();
    }
}
