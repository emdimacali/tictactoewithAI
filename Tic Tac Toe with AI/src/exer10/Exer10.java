package exer10;
/* Author: Ariel B. Doria 2012-37192
 * 		 : Ejandra Dimacali 2012-45232
 * Section: CMSC 170 U-6L
 * Program Description: This program aims to make an Artificial intelligence for
 * 	two players in Tic-Tac-Toe
 */


import javax.swing.JPanel;	//import packages
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class Exer10 {

	public static Random ran = new Random();															//Randomizers
    public static int winComb[][] = {{1,2,3},{4,5,6},{7,8,9},{1,4,7},{2,5,8},{3,6,9},{1,5,9},{3,5,7}};	//winning combination for winning the game
	public static int turn=0;
	public static int count=0;																			//number of turns already done.
	public static int[][] state = {{0,0,0},{0,0,0},{0,0,0}};											//initial state of the array
	public static int i,j;
	public static GUI mainGUI;
	public static boolean firstToMove = false;
	public static LinkedList<State> scoreList;
	public static LinkedList<State> winningStates;
	
	public static void main(String Args[]){	
		mainGUI = new GUI();
		mainGUI.initialize();
		ask();																							//ask who will initialize the game
	}
	
	//check if there is already a winner
	public static boolean check(){
		int res=checkWin(state);
		if(res==-1){																					//Game is draw
			JOptionPane.showMessageDialog(null,"The game is DRAW");
			for(int i=0;i<3;i++){																		//Disabling the button
				for(int j=0;j<3;j++){
					mainGUI.setClickable(i,j,false);
				}
			}
			return false;
		}
		else if(res==1){																				//Player wins
			JOptionPane.showMessageDialog(null,"You WIN the Game!");
			for(int i=0;i<3;i++){																		//Disabling the button
				for(int j=0;j<3;j++){
					mainGUI.setClickable(i,j,false);
				}
			}
			count=0;
			return false;
		}
		else if(res==2){																				//Computer wins
			JOptionPane.showMessageDialog(null,"Computer WINS!");
			for(int i=0;i<3;i++){																		//Disabling the button
				for(int j=0;j<3;j++){
					mainGUI.setClickable(i,j,false);
				}
			}
			count=0;
			return false;
		}
		return true;
		
	}
	
	//this method checks if the passed parameter st is in win state.
	public static int checkWin(int[][] st){
        int ret = 0;
        String x ="";
        String o ="";
        int c=0 , p , q;
        
        int counter = 0;
        for(int i=0;i<3;i++){
        	for(int j=0;j<3;j++){
        		if(st[i][j] != 0)
        			counter ++;
        	}
        }
        count = counter;
        for(p=0;p<3;p++)
        {
            for(q=0;q<3;q++)
            {
            	//check where the player's answer is 
                c++;
                if(st[p][q]==1)
                {
                    x+=c;
                }
                else if(st[p][q]==2)
                {
                    o+=c;
                }
            }
        }
        ret = checkWin2(x,o);
        
        //check the number of moves to know if the game is draw
        if(count>=9 && ret==0)
        {
            ret = -1;
        }
        return ret;
    }
	
    public static int checkWin2(String x,String o){
        int ret = 0;
        int p;
        for(p=0;p<8;p++)
        {
            //check if the player's answer is within the winning combination
            if(x.indexOf((char)winComb[p][0]+'0')>-1 && x.indexOf((char)winComb[p][1]+'0')>-1 && x.indexOf((char)winComb[p][2]+'0')>-1)
            {
                ret = 1;
                break;
            }
            
            if(o.indexOf((char)winComb[p][0]+'0')>-1 && o.indexOf((char)winComb[p][1]+'0')>-1 && o.indexOf((char)winComb[p][2]+'0')>-1)
            {
                ret = 2;
                break;
            }
        }
        
        return ret;
    }
    
    //asking the first player of the game
    public static void ask(){
    	String[] possibleValues = { "Player", "Computer"};
    	String selectedValue;
    	selectedValue = (String) JOptionPane.showInputDialog(null,
		"Who do you want to start the game?", "First Player",
		JOptionPane.INFORMATION_MESSAGE, null,
		possibleValues, possibleValues[0]);
    	for(int i=0;i<3;i++){																					//Enabling the button
			for(int j=0;j<3;j++){
				mainGUI.setClickable(i,j,true);
			}
		}
    	
    	try{
		if(selectedValue.matches("Player")) {
			turn=1;
			firstToMove = false;		//firstToMove not the agent
		}
		else{
			turn=2;
			firstToMove = true;			//firstToMove is the agent
			artificialIntelligence();	//the computer will first answer the game
		}
    	}catch(Exception e){			//by default player moves first
    		firstToMove = false;
    		turn=1;
    	}
    	
    }
    
    //where the agent's decision making takes place
    public static void artificialIntelligence(){

    	
    	//----------------------------------------
    	//NOTE! The artificial intelligence decision happens here
    	switch(count){
    		case 0:				//computer moves first, take over the middle spot for a sure win 
    			toggleState(1,1);
    			break;
    		default : 			//otherwise let the AI think of the best move
    			state = miniMax();
    			break;
    	}
    	//----------------------------------------
    	count++;						//increase count of turns taken
		mainGUI.toggleButton();			//change how the button arrays look like based on how the array of states looks like
		check();						//check if states are already in win state
		turn=1;							//turn = 1 in state array means that next move is player's
    }
    
    //the scoring algorithm that returns the best move based on the current state of the game's array
    public static int[][] miniMax(){
    	scoreList = new LinkedList<State>();
    	winningStates = new LinkedList<State>();
    	LinkedList<State> tempList = new LinkedList<State>();					//this will be our main queue for yielding the states.
    	scoreList = State.yieldState(state,null);	//yield states from current state attribute of the program
    		
    	//we are traversing the scoreList in here
    	for(int i=0;i<scoreList.size();i++){
    		
    		if(checkWin(scoreList.get(i).array) == 2){		//if in the first level generated from the current state contains a win state, return that already
    			return scoreList.get(i).array;
    		}
    		else{
    			tempList = new LinkedList<State>();
    			
    			scoreList.get(i).yieldChildren(); 			//make each element in scoreList yield its children states
    			State ptr;
    			
    			for(int k=0;k<scoreList.get(i).childStates.size();k++){		
    				ptr = scoreList.get(i).childStates.get(k);					//make ptr point to each of childstates of the scorelist's elements
    				tempList.add(ptr);											//then those child states, we place in our tempList 
    			}
    			//
    			while(!tempList.isEmpty()){
    				LinkedList<State> temp;
	    		
        			if(checkWin(tempList.getFirst().array) != 0){	//win or draw,add that state called to winningStates
        				winningStates.add(tempList.getFirst());
        			}
        			else{	//yield states of tempList's head if not in win state then add all the yielded states to end of queue then dequeue.
        				temp = tempList.getFirst().yieldChildren();
        				for(int j=0;j<temp.size();j++){	//add it to tempList
        					tempList.add(temp.get(j));
        				}
        			}
        			tempList.removeFirst();
        		}		
    			//after the above loop, we are expecting na meron na tayong winningStates na pwedeng magtrace pabalik hanggang sa root nodes which is yung mga entry sa scoreList
    			
    			//this loop makes sure all nodes have generated scores.
    			while(!winningStates.isEmpty()){
    				winningStates.getFirst().generateScore();
    				ptr = winningStates.getFirst().getParent();
    				
					while(ptr.isScoreComplete()){//compute for the score of the node being pointed by ptr which is either the min/max of all the scores,depending on depth.
							if(ptr.getDepth() % 2 == 0)ptr.getMinOfChildren();		//if even, get Min
							else ptr.getMaxOfChildren();							//if odd,get Max
						ptr = ptr.getParent();										//get the parent
						if(ptr == null) break;
					}
    				winningStates.removeFirst();
    			}
    	    	
    		}
    	}
 
    	//this is the final loop that lets the agent decide which move from the scoreList to chose based on the generated scores
    	//we get the node with the maximum score
    	int maxIndex = -1;
		int max = -99999;
		int maxCounter = 1;
		LinkedList<int[][]> maxStates = new LinkedList<int[][]>();
    	for(int i=0;i<scoreList.size();i++){
    		if(scoreList.get(i).getScore() >= max){
    			if(max == scoreList.get(i).getScore()){	//there are states with equal scores and the scores they have are the current Max
    				maxCounter++;	
    				maxStates.add(scoreList.get(i).array);
    			}else{
    				maxCounter = 1;
    				maxStates = new LinkedList<int[][]>();
    				maxStates.add(scoreList.get(i).array);
    				max = scoreList.get(i).getScore();
    				maxIndex = i;
    			}
    		}
    	}
    	int[][] array;
    	if(maxCounter > 1)			//if there are more than 1 maxState, we need the agent to decide the best of those by calling method resolve ties
    		array = resolveTies(maxStates);
    	else	
    		array = scoreList.get(maxIndex).array;
    	return array;
    }
    
    public static int[][] resolveTies(LinkedList<int[][]> maxStates){
    	LinkedList<State> temp;
    	int [][] resolvedAns = new int[3][3];
    	int j;
    	for(int i=0;i<maxStates.size();i++){
    		temp = State.yieldState(maxStates.get(i), null);
    		for(j=0;j<temp.size();j++){
    			if(checkWin(temp.get(j).array) == 1){//this move means the opponent will win if we choose it
    				break;	//this means we are not choosing this one
    			}
    		}
    		if(j == temp.size()){	//this means we reached the end of j loop (opponent will not win in the next move in this current state we are) so this answer is the best
    			resolvedAns = maxStates.get(i);
    			break;
    		}
    	}
    	return resolvedAns;
    }
    
    //this function changes the current value at the clicked state.
    public static void toggleState(int i,int j){
    	state[i][j] = turn;
    	mainGUI.setClickable(i,j,true);
    }

}













