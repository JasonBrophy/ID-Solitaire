package edu.jbrophypdx.idsolitaire;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;

import android.view.WindowManager;
import android.widget.ImageView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Game extends Activity {

    protected Deck pile;
    protected Stack[] stacks;
    protected int score;
    protected boolean [] removable;

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
        //ActionBar actionBar = getSupportActionBar();
        //if (actionBar != null) {
        //    actionBar.setDisplayHomeAsUpEnabled(true);
       // }

        /*mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);*/

        Context context = this.getApplicationContext();
        Resources res = context.getResources();
        int height = getScreenHeight(context);
        int width = getScreenWidth(this.getApplicationContext());
        Bitmap draw = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(draw);
        ImageView view = new ImageView(context);
        this.pile = new Deck(res, images, height, width);
        pile.shuffle();
        this.stacks = new Stack [4];
        for(int i = 0; i < 4; ++i)
            stacks[i] = new Stack();
        this.score = 0;
        removable = new boolean[4];
        dealCards(draw, canvas, view);
        //draw(canvas);
        this.play(draw, canvas, view);

    }


    public void play(Bitmap draw, Canvas canvas, ImageView view){
        for(int i = 0; i < 4; ++i)
            stacks[i].insertCard(pile.getOne());
        //displaySituation();
        setRemovable();
        while(isLegalMove() && pile.notEmpty())
            getMove(draw, canvas, view);
       // System.out.println("edu.jbrophypdx.idsolitaire.Game Over");
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


    public void getMove(Bitmap draw, Canvas canvas, ImageView view){
        boolean flag = false;
        boolean flag2 = false;
        int tmp1 =2;
       // Scanner in = new Scanner(System.in);
        //System.out.println("Move, Remove, or Deal, 0 for Move, 1 for Remove, 2 for deal: ");
        //tmp1 = in.nextInt();
        if(tmp1 == 0){
            moveCard();
        }
        else if(tmp1 == 2){
            if(!dealCards(draw, canvas, view))
                return;
        }
        else {
            removeCard();
        }
    }

    public boolean dealCards(Bitmap draw, Canvas canvas, ImageView view) {
        if (!pile.notEmpty())
            return false;
        for(int i = 0; i < 4; ++i)
            stacks[i].insertCard(pile.getOne());
        setRemovable();
        draw(draw, canvas, view);
        //displaySituation();
        return true;
    }

    public boolean removeCard(){
        //int tmp1;
        //Scanner in = new Scanner(System.in);
        //do {
        //    System.out.println("Please enter the column to remove the card from, 0, 1, 2, or 3: ");
        //    tmp1 = in.nextInt() % 4;
        //    if (tmp1 < 0)
        //        tmp1 = -tmp1;
         //   if (!removable[tmp1])
         //       System.out.println("That is not a valid move, please try again.");
       // } while (!removable[tmp1]);
       // stacks[tmp1].remove();
       // System.out.println();
       // System.out.println();
       // setRemovable();
        //this.displaySituation();
        return true;
    }
    public boolean moveCard() {
        /*Scanner in = new Scanner(System.in);
        boolean flag;
        boolean flag2;
        int tmp1;
        int tmp2;
        for (int i = 0; i < 5; ++i) {
            if (i == 4) {
                System.out.println("No column is empty, but there is a legal move. ");
                return false;
            }
            if(stacks[i].canMoveTo())
               break;
        }
        do {
         System.out.println("Move from column ?  0, 1, 2, or 3: ");
            tmp1 = in.nextInt();
        } while (!stacks[tmp1].canMoveFrom());
        do {
            flag = false;
            flag2 = false;
            System.out.println("Move to column ? 0, 1, 2, or 3: ");
            tmp2 = in.nextInt();
            if (tmp1 != tmp2)
                flag = true;
            if (stacks[tmp2 % 4].canMoveTo())
                flag2 = true;
        } while (!flag || !flag2);
        stacks[tmp2].insertCard(stacks[tmp1].remove());
        setRemovable();*/
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

    public void draw(Bitmap draw, Canvas canvas, ImageView view){
        Context context = this.getApplicationContext();
        int height = getScreenHeight(context);
        int width = getScreenWidth(context);
        canvas.drawBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bj), 20, 20, null);
        for(int i = 0; i < 4; ++i)
            stacks[i].draw(view, canvas, height, width, i);
        view.setImageBitmap(draw);
        view.invalidate();
    }
}
