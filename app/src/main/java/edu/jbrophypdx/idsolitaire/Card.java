/**
 * Copyright (c) Jason Brophy 2016
 */
package edu.jbrophypdx.idsolitaire;

import android.widget.ImageView;

class Card {
    protected int value;
    protected int suit;
    protected ImageView img;

    Card(int value, int suit, ImageView img) {
        this.value = value;
        this.suit = suit;
        this.img = img;
    }

    Card(Card toCopy) {
        this.value = toCopy.value;
        this.suit = toCopy.suit;
        this.img = toCopy.img;
    }

    //Used to set the removable value.  If the test against card makes this removable, return true.
    public boolean canRemove(Card testAgainst) {
        return testAgainst != null && this.value < testAgainst.getValue() && this.suit == testAgainst.getSuit();
    }

    public int getValue() {
        return this.value;
    }

    public int getSuit() {
        return this.suit;
    }

    public ImageView getImageView() {
        return this.img;
    }

}
