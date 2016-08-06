/**
 * Copyright (c) Jason Brophy 2016
 */
package edu.jbrophypdx.idsolitaire;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

class Stack {
    protected Card[] column; //A stack of cards, to be 13 in size (max can be reached)
    protected int index; //The most recent index in use.

    Stack() {
        column = new Card[13];
        index = -1;
    }

    public int insertCard(Card toAdd) {
        if (index > 12)
            return -1;
        ++index;
        column[index] = new Card(toAdd);
        return index;
    }

    public boolean canMoveTo() {
        return index == -1;
    }

    public boolean canMoveFrom() {
        return index > -1;
    }

    public Card remove() {
        if (column[index] == null)
            return null;
        Card temp = column[index];
        column[index] = null;
        --index;
        return temp;
    }

    public Card getTop() {
        if (index == -1)
            return null;
        return this.column[index];
    }

    public void draw(Canvas canvas, int height, int width, int index) {
        double widVal = width/6;
        int wid = (int)Math.round(widVal);
        int offset = -(-1-index)*(wid/5);

        if (this.index == -1) {
            Paint paint = new Paint();
            paint.setColor(0xffffff);
            canvas.drawBitmap(Bitmap.createBitmap(wid, (int)Math.round(widVal*1.452), Bitmap.Config.ARGB_8888), wid + offset, 0, paint);
            return;
        }

        column[0].getImageView()., (int)Math.round((index + 1) * widVal)+offset, 0, null)
        for (int i = 1; i < this.index + 1; ++i)
            canvas.drawBitmap(column[i].getBitmap(), (int)Math.round(((index + 1) * widVal))+offset, i * (height / 17), null);

    }
}
        

