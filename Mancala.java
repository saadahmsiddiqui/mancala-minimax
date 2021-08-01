package K152881AI;
import java.util.Scanner;

import org.omg.SendingContext.RunTime;

import jdk.nashorn.internal.runtime.regexp.joni.ast.StringNode;

class Mancala{
    private int difficulty_level;
    private int first_turn;
    private int[] Mancalas;
    private Scanner scanner;
    public Mancala(int difficulty_level){
        this.scanner= new Scanner(System.in);
        this.difficulty_level= difficulty_level;
        this.first_turn=0;
        this.Mancalas= new int [14];
        for (int _loop=1;_loop<14;_loop++){
            for(int _loop2=1;_loop2<=4;_loop2++){
                this.Mancalas[_loop]++;
            }
        }
        this.Mancalas[0]=0;
        this.Mancalas[7]=0;
    }
    public void setDifficultyLevel(int difficulty_level){
        this.difficulty_level=difficulty_level;
    }
    public void playGame(){
        float Turn=(float) Math.random() * 6;
        if (Turn>=2.5){
            this.playerOneTurn();
            this.first_turn=1;
        }
        else{
            this.playerTwoTurn();
            this.first_turn=2;
        }
        this.showBoard();
        while(this.gameShouldLoop()){
            if(this.first_turn==1){
                this.playerTwoTurn();
                this.first_turn=2;
            }
            else{
                this.playerOneTurn();
                this.first_turn=1;
            }
            System.out.print("\033[H\033[2J");
            System.out.flush();
            this.showBoard();
        }
    }
    private boolean CheckValidity(int cell_number, int player_number){
        if (player_number==1){
            if (this.Mancalas[cell_number]>0){
                return true;
            }
            return false;
        }
        else if (player_number==2){
            if (this.Mancalas[cell_number]>0){
                return true;
            }
            return false;
        }
        return false;
    }
    private boolean gameShouldLoop(){
        int _1count=0,_2count=0;
        for (int _loop=1;_loop<=6;_loop++){
            if (this.Mancalas[_loop]==0){
                _1count++;
            }
            if (this.Mancalas[_loop+7]==0){
                _2count++;
            }
        }
        if (_1count==6 || _2count==6){
            return false;
        }
        return true;
    }
    private boolean Take_Turn(int cell_number,int player_number){
        if (player_number==1){
            int stone_count=this.Mancalas[cell_number];
            this.Mancalas[cell_number]=0;
            int next_cell=cell_number-1;
            while(stone_count>0){
                if(next_cell>=1 && next_cell<=6){
                    if (stone_count==1 && this.Mancalas[next_cell]==0){
                        stone_count--;
                        this.Mancalas[7]++;
                        this.Mancalas[7]+=this.Mancalas[7+next_cell];
                        this.Mancalas[7+next_cell]=0;
                        return false;
                    }
                    this.Mancalas[next_cell--]++;
                    stone_count--;
                    if (next_cell==0){
                        this.Mancalas[7]++;
                        stone_count--;
                        if (stone_count==0){
                            return true;
                        }
                        next_cell=8;
                    }
                }
                else if (next_cell>=8 && next_cell<=13){
                    this.Mancalas[next_cell]++;
                    stone_count--;
                    next_cell++;
                    if (next_cell==14 && stone_count>0){
                        next_cell=6;
                    }
                }
                else if (next_cell==0){
                    if (stone_count==1){
                        this.Mancalas[7]++;
                        return true;
                    }
                    if (stone_count>1){
                        this.Mancalas[7]++;
                        stone_count--;
                    }
                    next_cell=8;
                }    
            }
        }
        else{
            int next_cell=cell_number+1;
            int stone_count=this.Mancalas[cell_number];
            this.Mancalas[cell_number]=0;
            while(stone_count>0){
                if(next_cell>=1 && next_cell<=6){
                    this.Mancalas[next_cell--]++;
                    stone_count--;
                    if (next_cell==0 && stone_count>1){
                        next_cell=8;
                    }
                }
                else if (next_cell>=8 && next_cell<=13){
                    if (stone_count==1 && this.Mancalas[next_cell]==0){
                        this.Mancalas[0]++;
                        this.Mancalas[0]=this.Mancalas[0]+ this.Mancalas[next_cell-7];
                        this.Mancalas[next_cell-7]=0;
                        stone_count--;
                        return false;
                    }
                    this.Mancalas[next_cell++]++;
                    stone_count--;
                    if (next_cell==14 && stone_count>0){
                        this.Mancalas[0]++;
                        stone_count--;
                        if (stone_count==0){
                            return true;
                        }
                        next_cell=6;
                    }
                }
                else if(next_cell==14){
                    if (stone_count>0){
                        stone_count--;
                        this.Mancalas[0]++;
                        if (stone_count==0){
                            return true;
                        }
                        next_cell=6;
                    }
                }    
            }
        }
        return false;
    }
    private void playerOneTurn(){
        int cell_number;
        boolean take_turns=false;
        do{
            if (take_turns==true){
                this.showBoard();
            }
            System.out.print("(Top Row cells - Cell Number) Player 1: ");
            cell_number=this.scanner.nextInt();
            if (cell_number>=1&&cell_number<=6){
                if(this.CheckValidity(cell_number,1)){
                    take_turns= this.Take_Turn (cell_number,1);
                }
                
            }
            else{
                this.playerOneTurn();
            }
        }while(take_turns);
    }
    private void playerTwoTurn(){
        int cell_number;
        boolean take_turns=false;
        do{
            if (take_turns==true){
                this.showBoard();
            }
            System.out.print("(Bottom Row cells - Cell Number) Player 2: ");
            cell_number=this.scanner.nextInt();
            if (cell_number>=1&&cell_number<=6){
                if (this.CheckValidity(cell_number, 2)){
                    take_turns=this.Take_Turn (cell_number+7,2);
                }
            }
            else{
                this.playerTwoTurn();
            }
        }while(take_turns);
    }
    public void playWithAI(){
        float Turn=(float) Math.random() * 6;
        if (Turn>=2.5){
            this.playerOneTurn();
            this.first_turn=1;
        }
        else{
            this.AITurn();
            this.first_turn=2;
        }
        this.showBoard();
        while(this.gameShouldLoop()){
            if(this.first_turn==1){
                this.AITurn();
                this.first_turn=2;
            }
            else{
                this.playerOneTurn();
                this.first_turn=1;
            }
            System.out.print("\n\n");
            this.showBoard();
        }
        if(this.Mancalas[0]>this.Mancalas[7]){
            System.out.print("AI Won!");
        }
        else{
            System.out.print("Player Won!");
        }
    }
    private void AITurn(){
        int [] current_board_state=this.CopyArray(this.Mancalas);
        int cell_number=Get_Best_Turn(2,current_board_state)+8;
        System.out.print("AI Turn - Bottom Cells: "+(cell_number-7)+"\n");
        if (cell_number>=8&&cell_number<=13){
            if (this.Take_Turn(cell_number, 2)){
                System.out.print("AI Turn Again . . .\nThinking . . .\n");
                this.showBoard();
                this.AITurn();
            }
        }
    }
    private int Get_Best_Turn(int player_number,int [] current_board_state){
        int [] Utilities= new int [6];
        int [] board_config= this.CopyArray(current_board_state);
        if (player_number==1){
            for (int i=1; i<=6;i++){
                if (board_config[i+7]>0){
                    boolean next_turn=Utility_Take_Turn(i, board_config,1);
                    Utilities[i-1]+= board_config[7];
                }
                board_config= this.CopyArray(current_board_state);
            }
            return this.getMaxIndex(Utilities);
        }
        else{
            //Search current level
            for (int i=1; i<=6;i++){
                if (board_config[i+7]>0){
                    boolean next_turn=Utility_Take_Turn(i+7, board_config,2);
                    Utilities[i-1]+= board_config[0];
                }
                board_config= this.CopyArray(current_board_state);
            }
            int best_AI_turn=this.getMaxIndex(Utilities);
            int [] BoardFirstLevel= this.CopyArray(current_board_state);
            this.Utility_Take_Turn(best_AI_turn+8, BoardFirstLevel, 2);
            int best_human_turn=this.Get_Best_Turn(1, BoardFirstLevel);
            boolean human_next_turn=this.Utility_Take_Turn(best_human_turn+1, BoardFirstLevel,1);
            for (int i=1; i<=6;i++){
                if (BoardFirstLevel[i+7]>0){
                    boolean next_turn=Utility_Take_Turn(i+7, BoardFirstLevel,2);
                    Utilities[i-1]+= BoardFirstLevel[0];
                }
                board_config= this.CopyArray(BoardFirstLevel);
            }
            if (this.difficulty_level==1){
                return this.getMaxIndex(Utilities);
            }
            else{
                return this.getRandomIndex();
            }
        }
    }
    private boolean Utility_Take_Turn(int cell_number,int [] board,int player_number){
        if (player_number==1){
            int stone_count=board[cell_number];
            board[cell_number]=0;
            int next_cell=cell_number-1;
            while(stone_count>0){
                if(next_cell>=1 && next_cell<=6){
                    if (stone_count==1 && board[next_cell]==0){
                        stone_count--;
                        board[7]++;
                        board[7]+=board[7+next_cell];
                        board[7+next_cell]=0;
                        return false;
                    }
                    board[next_cell--]++;
                    stone_count--;
                    if (next_cell==0){
                        board[7]++;
                        stone_count--;
                        if (stone_count==0){
                            return true;
                        }
                        next_cell=8;
                    }
                }
                else if (next_cell>=8 && next_cell<=13){
                    board[next_cell]++;
                    stone_count--;
                    next_cell++;
                    if (next_cell==14 && stone_count>0){
                        next_cell=6;
                    }
                }
                else if (next_cell==0){
                    if (stone_count==1){
                        board[7]++;
                        return true;
                    }
                    if (stone_count>1){
                        board[7]++;
                        stone_count--;
                    }
                    next_cell=8;
                }    
            }
        }
        else{
            int next_cell=cell_number+1;
            int stone_count=board[cell_number];
            board[cell_number]=0;
            while(stone_count>0){
                if(next_cell>=1 && next_cell<=6){
                    board[next_cell--]++;
                    stone_count--;
                    if (next_cell==0 && stone_count>1){
                        next_cell=8;
                    }
                }
                else if (next_cell>=8 && next_cell<=13){
                    if (stone_count==1 && board[next_cell]==0){
                        board[0]++;
                        board[0]=board[0]+ board[next_cell-7];
                        board[next_cell-7]=0;
                        stone_count--;
                        return false;
                    }
                    else if (next_cell==14 && stone_count>0){
                        board[0]++;
                        stone_count--;
                        if (stone_count==0){
                            return true;
                        }
                        next_cell=6;
                    }
                    else{
                        board[next_cell++]++;
                        stone_count--;    
                    }
                }
                else if(next_cell==14){
                    if (stone_count>0){
                        stone_count--;
                        board[0]++;
                        if (stone_count==0){
                            return true;
                        }
                        next_cell=6;
                    }
                    return false;
                }    
            }
        }
        return false;
    }
    private int getRandomIndex(){
        return (int) Math.ceil(Math.random()*5);
    }
    public int getMaxIndex(int [] Array){
        int Max=0,Index=0;
        while (Index<Array.length){
            if (Array[Index]>Max){
                Max=Array[Index];
            }
            Index++;
        }
        Index=0;
        while (Index<Array.length){
            if (Array[Index]==Max){
                return Index;
            }
            Index++;
        }
        return Index;
    }
    private int[] CopyArray(int [] toCopy){
        int[] newArray = new int [toCopy.length];
        for (int i=0;i<toCopy.length;i++){
            newArray[i]=toCopy[i];
        }
        return newArray;
    }
    public void showBoard(){
        System.out.print(Mancalas[7]+"    -->  ");
        for (int _loop=1;_loop<=6;_loop++){
            System.out.print(Mancalas[_loop]+"   ");
        }
        System.out.println();
        System.out.print("          ");
        for (int _loop=8;_loop<14;_loop++){
            System.out.print(Mancalas[_loop]+"   ");
        }
        System.out.print("    <--  "+Mancalas[0]);
        System.out.println();
    }
    private void showBoard(int [] Board){
        System.out.print(Board[7]+"    -->  ");
        for (int _loop=1;_loop<=6;_loop++){
            System.out.print(Board[_loop]+"   ");
        }
        System.out.println();
        System.out.print("          ");
        for (int _loop=8;_loop<14;_loop++){
            System.out.print(Board[_loop]+"   ");
        }
        System.out.print("    <--  "+Board[0]);
        System.out.println();
    }
    private void PrintArray(int [] Array){
        System.out.println();
        for (int i=0;i<Array.length;i++){
            System.out.print(Array[i]+" ");
        }
        System.out.println();
    }
}