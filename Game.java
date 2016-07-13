import java.util.Scanner;
/**
 *
 *Copyright (c) Jason Brophy 2016
 */

class Game {

    protected Deck pile;
    protected Stack [] stacks;
    protected int score;
    protected boolean [] removable;
    Game(){
        this.pile = new Deck();
        pile.shuffle();
        this.stacks = new Stack [4];
        for(int i = 0; i < 4; ++i)
            stacks[i] = new Stack();
        this.score = 0;
        removable = new boolean[4];

    }

    public void play(){
        for(int i = 0; i < 4; ++i)
            stacks[i].insertCard(pile.getOne());
        displaySituation();
        setRemovable();
        while(isLegalMove() && pile.notEmpty())
            getMove();
        System.out.println("Game Over");
    }

    public void setRemovable(){
        for(int i = 0; i < 4; ++i){
            if(stacks[i].getTop() == null)
                continue;
            for(int j = 0; j < 4; ++j){
                if(i == j || stacks[j] == null);
                else if(removable[i] = stacks[i].getTop().canRemove(stacks[j].getTop()))
                    break;
            }
        }
    }

    public boolean isLegalMove(){
        for(int i = 0; i < 4; ++i){
            if(removable[i])
                return true;
        }
        return false;
    }

    public boolean isLegalMove(int removeFrom){
        removeFrom = removeFrom % 4;
        return removable[removeFrom];
    }


    public void getMove(){
        boolean flag = false;
        boolean flag2 = false;
        int tmp1;
        int tmp2;
        Scanner in = new Scanner(System.in); 
        System.out.println("Move, Remove, or Deal, 0 for Move, 1 for Remove, 2 for deal: ");
        tmp1 = in.nextInt();
        if(tmp1 == 0){
            for(int i = 0; i < 5; ++i){
                if(i == 4){
                    if(!flag)
                        System.out.println("No column is empty, but there is a legal move. ");
                    break;
                }
                if(stacks[i].canMoveTo())
                    flag = true;
            }
            if(flag){
                do{
                    System.out.println("Move from column ?  0, 1, 2, or 3: ");
                    tmp1 = in.nextInt();
                }while (!stacks[tmp1].canMoveFrom());
                do{
                    flag = false;
                    flag2 = false;
                    System.out.println("Move to column ? 0, 1, 2, or 3: ");
                    tmp2 = in.nextInt();
                    if(tmp1 != tmp2)
                        flag = true;
                    if(stacks[tmp2 % 4].canMoveTo())
                        flag2 = true;
                }while(!flag || !flag2);
                stacks[tmp2].insertCard(stacks[tmp1].remove());
            }
            setRemovable();
        }
        else if(tmp1 == 2){
            if (!pile.notEmpty())
                return;
            for(int i = 0; i < 4; ++i)
                stacks[i].insertCard(pile.getOne());
            displaySituation();
        }
        else {
            do {

                System.out.println("Please enter the column to remove the card from, 0, 1, 2, or 3: ");
                tmp1 = in.nextInt() % 4;
                if (tmp1 < 0)
                    tmp1 = -tmp1;
                if (!removable[tmp1])
                    System.out.println("That is not a valid move, please try again.");
            } while (!removable[tmp1]);
            stacks[tmp1].remove();
            System.out.println();
            System.out.println();
            this.displaySituation();
        }
    }

    public void displaySituation(){
        int [] indexes = new int [4];
        int sum;
        int j;
        for(int i = 0; i < 4; ++i){
            indexes[i] = stacks[i].getIndex();
        }
        while(true){
            sum = -4;
            j = 0;
            while(j < 4) {
                if (indexes[j] == -1) {
                    System.out.print("        ");
                    ++j;
                    continue;
                }
                stacks[j].displayLoc(indexes[j]--);
                sum += indexes[j];
                ++j;
            }
            System.out.println();
            if(sum == -4) return;
        }

    }

}
