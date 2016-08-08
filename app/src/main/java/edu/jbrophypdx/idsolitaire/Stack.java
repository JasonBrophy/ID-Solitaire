/**
 * Copyright (c) Jason Brophy 2016
 */
package edu.jbrophypdx.idsolitaire;

class Stack {
    protected Card[] column; //A stack of cards, to be 13 in size (max can be reached)
    protected int index; //The most recent index in use.

    Stack() {
        column = new Card[13];
        index = -1;
    }

    //Insert the card, increment BEFORE inserting, leaving the index AT the last card, not one past.
    public void insertCard(Card toAdd) {
        if (index > 12)
            return;
        ++index;
        column[index] = new Card(toAdd);
    }

    //If this stack is not empty, aka index is not -1, return true.
    public boolean notEmpty() {
        return index != -1;
    }

    //This function is redundant, and could be done
    // with the above function, used to make the code more readable
    public boolean isEmpty() {
        return index <= -1;
    }

    //Remove the top card in the stack, setting to null is unnecessary, return the card for
    //insertion if a move is occuring.
    public Card remove() {
        if (index == -1 || column[index] == null)
            return null;
        Card temp = column[index];
        --index;
        return temp;
    }

    //return the top card in the stack, the first check is unnecessary right now, but avoids
    //accessing a non-existent array slot if it were invoked on an empty array.
    public Card getTop() {
        if (index == -1)
            return null;
        return this.column[index];
    }

    public int getIndex() {
        return this.index;
    }

}
        

