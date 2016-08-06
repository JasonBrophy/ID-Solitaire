/**
 * Copyright (c) Jason Brophy 2016
 */
package edu.jbrophypdx.idsolitaire;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.util.Random;

class Deck {

    protected Card[] deck;
    protected int top;
    protected int size;

    Deck(Resources res, ImageView [] img, int height, int width) {
        this.top = 0;
        this.size = 52;
        this.deck = new Card[this.size];
        for (int i = 0; i < 52; ++i)
            deck[i] = new Card(i % 13 + 2, i / 13, img[i]);
    }

    public Card getOne() {
        return deck[top++];
    }

    public void shuffle() {
        Random rand = new Random();
        int numSwaps = rand.nextInt(71) + 30;
        int swap1;
        int swap2;
        Card tmp;
        while (numSwaps > 0) {
            swap1 = rand.nextInt(52);
            swap2 = rand.nextInt(52);
            tmp = deck[swap1];
            deck[swap1] = deck[swap2];
            deck[swap2] = tmp;
            --numSwaps;
        }
    }

    public boolean notEmpty() {
        return top < 52;
    }

}
