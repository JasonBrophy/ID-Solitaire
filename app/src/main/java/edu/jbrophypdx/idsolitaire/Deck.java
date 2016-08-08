/**
 * Copyright (c) Jason Brophy 2016
 */
package edu.jbrophypdx.idsolitaire;

import android.widget.ImageView;

import java.util.Random;

class Deck {

    protected final int size;
    protected Card[] deck;
    protected int top;

    //Initialize all cards in the deck to have the respective ImageView associated with them.
    //This is used by the passed in imageview array, images are already loaded in them.
    Deck(ImageView[] img) {
        this.top = 0;
        this.size = 52;
        this.deck = new Card[this.size];
        for (int i = 0; i < 52; ++i)
            deck[i] = new Card(i % 13 + 2, i / 13, img[i]);
    }

    //Get one, and move the index past that top card.
    public Card getOne() {
        return deck[top++];
    }

    //Shuffle the deck, randomly swapping some two cards from 30 - 100 times.
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

    //if the top is less than 52, we are not past the number of cards in the deck, and therefore not empty.
    public boolean notEmpty() {
        return top < 52;
    }

}
