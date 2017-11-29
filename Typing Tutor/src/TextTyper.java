import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;


public class TextTyper extends JFrame implements ActionListener,KeyListener {

	private JLabel lblTimer;
	private JLabel lblScore;
	private JLabel lblWord;
	private JTextField txtWord;
	private JButton btnStart;
	private JButton btnStop;
	
	private Timer clock_timer=null;
	private Timer word_timer=null;
	private boolean running = false;
	private int timeRemaining = 0;
	private int score =0;
	private String[] words=null;
	
	public TextTyper(String[] args) {
		words=args;
		GridLayout layout = new GridLayout(3, 2);
		super.setLayout(layout);
	
		Font font = new Font("Verdana",2,130);
		
		lblTimer = new JLabel("Time");
		lblTimer.setFont(font);
		super.add(lblTimer);
		
		lblScore = new JLabel("Score");
		lblScore.setFont(font);
		lblScore.setOpaque(true);
		lblScore.setBackground(Color.CYAN);
		super.add(lblScore);
		
		lblWord = new JLabel("");
		lblWord.setFont(font);
		super.add(lblWord);
		
		txtWord = new JTextField("");
		txtWord.addKeyListener(this);
		txtWord.setFont(font);
		super.add(txtWord);
		
		btnStart = new JButton("Start");
		btnStart.setFont(font);
		btnStart.addActionListener(this);
		super.add(btnStart);
		
		btnStop = new JButton("Stop");
		btnStop.setFont(font);
		btnStop.addActionListener(this);
		super.add(btnStop);
		
		super.setTitle("Text Typer");
		super.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		super.setExtendedState(MAXIMIZED_BOTH);
		super.setVisible(true);
	
		startGame();
	}
	
	private void startGame(){
		clock_timer=new Timer(1000, this);
		clock_timer.setInitialDelay(0);
		word_timer=new Timer(3000, this);
		word_timer.setInitialDelay(0);
		
		running = false;
		timeRemaining = 50;
		score = 0;
		
		lblTimer.setText("Time :" + timeRemaining);
		lblScore.setText("Score :" + score);
		lblWord.setText(" ");
		btnStart.setText("Start");
		
		
		btnStop.setEnabled(false);
		txtWord.setEnabled(false);
	}
	
	

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnStart){
			handleStart();
		}else if(e.getSource()== btnStop){
			handleStop();
		}else if(e.getSource() == clock_timer){
			handle_clock_timer();
		}else if(e.getSource() == word_timer){
			handle_word_timer();
		}
	}
	
	private void handle_word_timer(){
		int ridx = (int)(Math.random()*words.length);
		String actualWord=lblWord.getText();
		String answerWord=txtWord.getText();
		if(actualWord.equals(answerWord)){
			score++;
		}
		lblWord.setText(words[ridx]);
		txtWord.setText("");
		lblTimer.setText("Time :" + timeRemaining);
		lblScore.setText("Score :" + score);
		
	}
	private void handleStart(){
		if(running == false){
			clock_timer.start();
			word_timer.start();
			running = true;
	
			btnStop.setEnabled(true);
			txtWord.setEnabled(true);
			btnStart.setText("Pause");
			
			txtWord.setFocusCycleRoot(true);
			super.nextFocus();
		}else{
			clock_timer.stop();
			word_timer.stop();
			running = false;
			txtWord.setEnabled(false);
			btnStart.setText("Start");
		}
	}
	private void handleStop(){
		clock_timer.stop();
		word_timer.stop();
		int choice=JOptionPane.showConfirmDialog(this, "Your Score is: "+score+"\nDo You Want To Exit the session");
		if(timeRemaining<=0 || choice==JOptionPane.YES_OPTION){
			startGame();
		}else if(choice==JOptionPane.NO_OPTION||choice==JOptionPane.CANCEL_OPTION){
			word_timer.start();
			clock_timer.start();
		}
	}
	public void handle_clock_timer(){
		timeRemaining--;
		lblTimer.setText("Time :" + timeRemaining);
		if(timeRemaining<=0)
			handleStop();
		
	}
	
	
	

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int ridx = (int)(Math.random()*words.length);
		String actualWord=lblWord.getText();
		String answerWord=txtWord.getText();
		if(actualWord.equals(answerWord)){
			score++;
			lblWord.setText(words[ridx]);
			txtWord.setText("");
			lblTimer.setText("Time :" + timeRemaining);
			lblScore.setText("Score :" + score);
			word_timer.restart();
		}
		
	}

	
	
}
