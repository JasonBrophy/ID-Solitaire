/**
 *Jason Brophy (c) 2016
 *
 */


class Card {
    protected int value;
    protected int suit;

    static String [] names = {"Empty", "Empty", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King", "Ace"};
    static char[] suits2 = {'\u2664', '\u2661', '\u2662', '\u2667'};
    static char [] suits = {'S', 'D', 'H', 'C'};

    Card(int value, int suit){
        this.value = value;
        this.suit = suit;
    }

    Card(Card toCopy){
        this.value = toCopy.value;
        this.suit = toCopy.suit;
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

    public void setValue(int value){
        this.value = value;
    }

    public void setSuit(int suit){
        this.suit = suit;
    }

    public void display(){
        System.out.print(names[value] + " " + suits[suit] + " ");
    }

}
