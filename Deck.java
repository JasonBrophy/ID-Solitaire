import java.util.Random;

/**
 *
 *
 *Copyright (c) Jason Brophy 2016
 */

class Deck {

    protected Card [] deck;
    protected int top;
    protected int size;

    Deck(){
        this.top = 0;
        this.size = 52;
        this.deck = new Card [this.size];
        for(int i = 0; i < 13; ++i){
            deck[i] = new Card(i+2, 0);
            deck[i+13] = new Card(i+2, 1);
            deck[i+26] = new Card(i+2, 2);
            deck[i+39] = new Card(i+2, 3);
        }
    }

    public Card getOne(){
        return deck[top++];
    }

    public void shuffle(){
        Random rand = new Random();
        int numSwaps = rand.nextInt(71) + 30;
        int swap1;
        int swap2;
        Card tmp;
        while(numSwaps > 0){
            swap1 = rand.nextInt(52);
            swap2 = rand.nextInt(52);
            tmp = deck[swap1];
            deck[swap1] = deck[swap2];
            deck[swap2] = tmp;
            --numSwaps;
        }
    }

    public boolean notEmpty(){
        if(top < 52)
            return true;
        return false;
    }

}
