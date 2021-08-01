package K152881AI;

import java.util.Scanner;

class Debug{
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in);
        System.out.print("Do You want to play with or another player? \n1. For AI\n2. For Two Player");
        int Choice = scanner.nextInt();
        Mancala newGame= new Mancala(0);
        if (Choice==1){
            System.out.print("Set Difficulty \n1. For Easy\n2. For Hard\n");
            Choice = scanner.nextInt();
            newGame.setDifficultyLevel(Choice-1);
            newGame.playWithAI();
        }
        else{
            newGame.showBoard();
        }
    }
}