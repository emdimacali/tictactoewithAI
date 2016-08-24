
import java.util.LinkedList;

public class State {
	int array[][];
	int score;
	int depth;
	LinkedList <State> childStates;
	State parent;
	static int counter;
	
	public State(int [][]state , int i,int j){
		array = new int[3][];
		for(int k = 0; k < 3; k++)
			array[k] = state[k].clone();
		this.score = -11;
		this.setDepth();
		
		int num = depth % 2;
		if(Exer10.firstToMove){
			switch(num){
				case 0://even
					array[i][j] = 1;	//player's move
					break;
				default:		
					array[i][j] = 2;	//ai's move
					break;
			}
		}
		else{
			switch(num){
			case 0://even
				array[i][j] = 2;		//ai's move
				break;
			default:		
				array[i][j] = 1;		//player's move
				break;
			}
		}

		childStates = new LinkedList<State>();
	}
	
	//we compute the score based on who will win on the current array
	public void generateScore(){
		score = 0;
		switch(Exer10.checkWin(this.array)){
		 	case 1 : //opponent wins
		 		score += depth - 10;
		 		break;
		 	case 2:
		 		score += 10 - depth;	//agent wins
		 		break;
		 	default:
		 		score += 0;
		 		break;
		}
		
	}
	//yield children states given an array and then reference its parent as well.
	public static LinkedList<State> yieldState(int[][] state,State parentRef){
		LinkedList<State> scoreList = new LinkedList<State>();
		for(int i=0 ; i<3 ; i++){
			for(int j=0 ; j<3 ; j++){
				if(state[i][j]==0){ //the state is empty;
					State newState = new State(state,i,j);
					newState.setParent(parentRef);
					scoreList.add(newState);
				}
			}
		}
		return scoreList;
	}
	
	//returns the array contained in a State
	public static String returnArray(State newState){
		String toRet="";
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				try{
					toRet += Integer.toString(newState.array[i][j]);
				}catch(Exception e){
					System.out.println("Exception Handled.");
					return null;
				}
			}
		}
		return toRet;
	}
	
	//assign the score based on the minimum of the children
	public void getMinOfChildren(){	
		int min = 99999;
		for(int i=0;i<childStates.size();i++){
			if(childStates.get(i).score < min){
				min = childStates.get(i).score;
			}
			
		}
		this.score = min;
	}

	//assign the score based on the maximum of the children
	public void getMaxOfChildren(){	
		int max = -99999;
		for(int i=0;i<childStates.size();i++){
			if(childStates.get(i).score > max){
				max = childStates.get(i).score;
			}
		}
		this.score = max;
	}
	
	//sets which level of the game tree we are
	public void setDepth(){
		int counter = 0;
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				if(this.array[i][j] != 0)
					counter++;
			}
		}
		this.depth = counter + 1;
	}
	
	//returns true if all childstates have generated their scores
	public boolean isScoreComplete(){
		for(int i=0;i<childStates.size();i++){
			if(childStates.get(i).score == -11)	//-11 means the score has not been computed yet.
				return false;
		}
		return true;
	}
	
	//returns depth
	public int getDepth(){
		return this.depth;
	}
	
	//yields state , this is just a non-static version of yieldState
	public LinkedList<State> yieldChildren(){
		childStates = yieldState(this.array,this);
		return childStates;
	}
	
	//returns score
	public int getScore(){
		return this.score;
	}
	
	//returns parent
	public State getParent(){
		return this.parent;
	}
	//sets parent
	public void setParent(State parentRef){
		this.parent = parentRef;
	}
	//sets children
	public void setChildren(LinkedList<State> states){
		this.childStates = states;
	}
	//gets children
	public LinkedList<State> getChildren(){
		return this.childStates;
	}
}
