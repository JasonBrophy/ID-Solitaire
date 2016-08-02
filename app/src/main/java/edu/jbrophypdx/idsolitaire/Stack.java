package edu.jbrophypdx.idsolitaire;

import android.graphics.Canvas;
import android.widget.ImageView;

/**
 *Copyright (c) Jason Brophy 2016
 */

class Stack {
    protected Card[] column; //A stack of cards, to be 13 in size (max can be reached)
    protected int index; //The most recent index in use.
    Stack(){
        column = new Card [13];
        index = -1;
    }

    public int insertCard(Card toAdd){
        if(index > 12)
            return -1;
        ++index;
        column[index] = new Card(toAdd);
        return index;
    }

    public boolean canMoveTo(){
        if(index == -1)
            return true;
        return false;
    }

    public boolean canMoveFrom(){
        if(index > -1)
            return true;
        return false;
    }

    public Card remove(Card makesRemovable){
        if(index == -1)
            return null;
        if(column[index].canRemove(makesRemovable)){
            Card temp = column[index];
            column[index--] = null;
            return temp;
        }
        return null;
    }

    public Card remove(){
        if(column[index] == null)
            return null;
        Card temp = column[index];
        column[index] = null;
        --index;
        return temp;
    }

    public boolean canRemove(Card makesRemovable){
    
        if(index == -1)
            return false;
        if(column[index].canRemove(makesRemovable)){
            return true;
        }
        return false;
    }

    public Card getTop(){
        if(index == -1)
            return null;
       // System.out.println(index);
        return this.column[index];
    }

    public void displayAll(){
        for(int i = index; i >= 0; --i){
            column[i].display();
            System.out.println();
        }
    }

    public void displayLoc(int loc){
        if(loc != -1)
                loc = loc % 13;
        if(column[loc] == null) {
            System.out.print("empty  ");
            return;
        }
        column[loc].display();
    }

    public void displayTop(){
        if(column[index] == null){
            System.out.print("empty  ");
            return;
        }
        column[index].display();
    }

    public int getIndex(){
        return index;
    }

    public void draw(ImageView view, Canvas canvas, int height, int width, int index){

        if(this.index == -1){
            return;
            //add draw empty
        }
        int offset;
        if(index == 0)
            offset = -30;
        else if(index == 1)
            offset = -20;
        else if(index == 2)
            offset = -10;
        else
            offset = 0;

        canvas.drawBitmap(column[0].getBitmap(), (index+2) * width/6 + offset, 10,null);
        for(int i = 1; i < this.index+1; ++i)
            canvas.drawBitmap(column[i].getBitmap(), ((index+2) * width/6) + offset, i*(height/17)+10,null);

    }
}
        

