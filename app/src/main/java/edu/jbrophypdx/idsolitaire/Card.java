/**
 * Copyright (c) Jason Brophy 2016
 */
package edu.jbrophypdx.idsolitaire;

import android.graphics.Bitmap;

class Card {
    protected int value;
    protected int suit;
    protected Bitmap img;

    Card(int value, int suit, Bitmap img) {
        this.value = value;
        this.suit = suit;
        this.img = img;
    }

    Card(Card toCopy) {
        this.value = toCopy.value;
        this.suit = toCopy.suit;
        this.img = toCopy.img;
    }

    public boolean canRemove(Card testAgainst) {
        return testAgainst != null && this.value < testAgainst.getValue() && this.suit == testAgainst.getSuit();
    }

    public int getValue() {
        return this.value;
    }

    public int getSuit() {
        return this.suit;
    }

    public Bitmap getBitmap() {
        return this.img;
    }

}
