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

        int offset;
        if (index == 0)
            offset = -30;
        else if (index == 1)
            offset = -20;
        else if (index == 2)
            offset = -10;
        else
            offset = 0;

        if (this.index == -1) {
            Paint paint = new Paint();
            paint.setColor(0xffffff);
            canvas.drawBitmap(Bitmap.createBitmap(width / 6, height / 8, Bitmap.Config.ARGB_8888), (index + 2) * width / 6 + offset, 10, paint);
            return;
        }

        canvas.drawBitmap(column[0].getBitmap(), (index + 2) * width / 6 + offset, 10, null);
        for (int i = 1; i < this.index + 1; ++i)
            canvas.drawBitmap(column[i].getBitmap(), ((index + 2) * width / 6) + offset, i * (height / 17) + 10, null);

    }
}
        

