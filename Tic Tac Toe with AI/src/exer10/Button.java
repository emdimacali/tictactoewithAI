package exer10;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton{
	final int i,j;
	
	//constructor of the buttons in the tic tac toe board
	public Button(final int i,final int j){
		this.i = i;
		this.j =j;
		
		this.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(Exer10.state[i][j] == 0){
					updateArray();//update the state of an array
					
					if(Exer10.turn == 1&& Exer10.check()){		//check if the player completed in his turn and check if there is already a winner
						Exer10.turn=2;
						Exer10.artificialIntelligence();	//turn of the computer
						
					}
				}
			}
		});
	}
	
	//updates array , if the player clicks on it
	public void updateArray(){
		Exer10.state[this.i][this.j] = Exer10.turn;
		Exer10.count++;						//counter for number of "tira"
		Exer10.mainGUI.toggleButton();				//refresh the UI
		Exer10.mainGUI.setClickable(i,j,true);
	}	
}
