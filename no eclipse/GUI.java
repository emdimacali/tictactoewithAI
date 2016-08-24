

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {
	private Button[][] b = new Button[3][3];
	private static int i,j;
	JButton reset;
	
	//initialize GUI elements
	public void initialize(){
		for(int i=0;i<3;i++){					//Initializing the array of Buttons
			for(int j=0;j<3;j++){
				b[i][j] = new Button(i,j);
			}
		}
		
		JFrame frame = new JFrame("Tic-Tac-Toe");		//initializing JFrame and JPanel
		JPanel canvas = new JPanel();
		JPanel puzCanvas = new JPanel();
		final JPanel puzCanvas2 = new JPanel();
		final JPanel buttonCanvas = new JPanel();
		final JPanel canvasBig = new JPanel();	
		puzCanvas.setLayout(new GridLayout(3,3));			//Canvass of 3 by 3
		puzCanvas.setPreferredSize(new Dimension(300,300));
		puzCanvas2.setBackground(Color.YELLOW);				//make border like effect
		puzCanvas2.setPreferredSize(new Dimension(310,310));
		buttonCanvas.setLayout(new GridLayout(1,1));			//canvas of reset button
		buttonCanvas.setPreferredSize(new Dimension(150,50));
		buttonCanvas.setBackground(Color.BLACK);
		canvasBig.setBackground(Color.BLACK);
		canvasBig.setPreferredSize(new Dimension(450,390));
		canvas.setPreferredSize(new Dimension(500,400));	//canvass of 2 both canvass
		canvas.setBackground(Color.BLACK);
		ImageIcon res = new ImageIcon("image/reset.png");	//Inserting image to the button
		reset = new JButton(res);
		reset.setBorder(null);

		
		for(int i=0;i<3;i++){					//Adding the button in canvass
			for(int j=0;j<3;j++){
				puzCanvas.add(b[i][j]);
			}
		}
		
		for(i=0;i<3;i++){					//Adding the button in canvass
			for(j=0;j<3;j++){
				
			}
		}
		
		reset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				
				for(int i=0;i<3;i++){					//Enabled the buttons
					for(int j=0;j<3;j++){
						Exer10.state[i][j]=0;
						b[i][j].setEnabled(true);
					}
				}
				Exer10.count = 0 ;
				Exer10.ask();			//ask who will first play the next game
				toggleButton();			//refreshes the GUI
				
			}
		});
		
		buttonCanvas.add(reset);			//adding the function button
		puzCanvas2.add(puzCanvas);			//adding the canvas
		canvasBig.add(puzCanvas2);
		canvasBig.add(buttonCanvas);
		canvas.add(canvasBig);
		frame.setContentPane(canvas);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}
	
	//this refreshes the images contained in the array of buttons based on the attribute array of Exer10.
	public void toggleButton(){
		ImageIcon xImage = new ImageIcon("image/x.png");	//Inserting image to the button
		ImageIcon oImage = new ImageIcon("image/o.png");	//Inserting image to the button
		for(int i=0;i<3;i++){					//Update the buttons' text base on array of state
			for(int j=0;j<3;j++){
				 this.b[i][j].setIcon(Exer10.state[i][j]==1?xImage:(Exer10.state[i][j]==2?oImage:null));
			}
		}
	}
	
	//this makes the button clickable
	public void setClickable(int i,int j,boolean value){
		b[i][j].setEnabled(value);
	}
	
	
}

