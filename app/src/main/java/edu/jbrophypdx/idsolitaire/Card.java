package edu.jbrophypdx.idsolitaire;

import android.graphics.Bitmap;

/**
 *Copyright (c) Jason Brophy 2016
 *
 */


class Card {
    protected int value;
    protected int suit;
    protected Bitmap img;
    static String [] names = {"Empty", "Empty", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
    static char[] suits2 = {'\u2664', '\u2661', '\u2662', '\u2667'};
    static char [] suits = {'S', 'D', 'H', 'C'};
    static char [] lSuits = {'s', 'd', 'h', 'c'};

    Card(int value, int suit, Bitmap img){
        this.value = value;
        this.suit = suit;
        this.img = img;
    }

    Card(Card toCopy){
        this.value = toCopy.value;
        this.suit = toCopy.suit;
        this.img = toCopy.img;
    }

    public boolean canRemove(Card testAgainst){
        if(testAgainst == null)
            return false;
        if(this.value < testAgainst.getValue() && this.suit == testAgainst.getSuit())
            return true;
        return false;
    }

    public int getValue(){
        return this.value;
    }

    public int getSuit(){
        return this.suit;
    }

    public Bitmap getBitmap() { return this.img; }

    public void setValue(int value){
        this.value = value;
    }

    public void setSuit( int suit){this.suit = suit; }

    public void display(){
        System.out.print(names[value] + " " + suits[suit] + " ");
    }

}
