package com.consulting.jbh.tictactoe;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
/*import android.widget.GridLayout;*/
import android.support.v7.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
	
	int[] gameState={2,2,2,2,2,2,2,2,2};
	int activePlayer=0;
	boolean isGameActive=true;
	int[][] winningPositions={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
	MediaPlayer mPlayer;
	
	
	public void selectSquare(View view){
		ImageView current=(ImageView)view;
		int currentTag=Integer.parseInt(current.getTag().toString());
		System.out.println(currentTag);
		if(gameState[currentTag]==2 &&isGameActive){
			gameState[currentTag]=activePlayer;
			current.setTranslationY(-1000f);
			if(activePlayer==0){
				current.setImageResource(R.drawable.tokentwo);
				activePlayer=1;
			}else{
				current.setImageResource(R.drawable.tokenone);
				activePlayer=0;
			}
			current.animate().rotationY(720).translationYBy(1000f).setDuration(500);
			for(int[] winningPosition:winningPositions)
				if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
						gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
						gameState[winningPosition[0]] != 2) {
					displayStatus("CONGRATULATIONS\nWINNER!!!",R.raw.cheers,R.drawable.winner);
					isGameActive = false;
				} else {
					boolean isGameOver = true;
					for (int counterState : gameState) {
						if (counterState == 2) isGameOver = false;
					}
					if (isGameOver) {
						displayStatus("Game Is A Draw",R.raw.belch,R.drawable.loser);
						isGameActive = false;
					}
				}
		}
		
	}
	
	private void displayStatus(String message, int soundFile,int imageFile) {
		TextView userMessage = findViewById(R.id.textViewMessage);
		userMessage.setTranslationX(-1000f);
		mPlayer = MediaPlayer.create(this, soundFile);
		mPlayer.start();
		GridLayout gridLayout=findViewById(R.id.gridLayout);
		gridLayout.animate().alpha(0f).setDuration(9000);
		ImageView loserView=findViewById(R.id.imageViewWinLose);
		loserView.setImageResource(imageFile);
		loserView.animate().alpha(1f).setDuration(6000);
		userMessage.setText(message);
		userMessage.animate().translationXBy(1000f);
	}
	
	public void resetBoard(View view){
		activePlayer=0;
		for(int iter=0;iter<gameState.length;iter++){
			System.out.println(gameState[iter]);
			gameState[iter]=2;
		}
		//Reset all image sources to empty
		GridLayout gridLayout=findViewById(R.id.gridLayout);
		System.out.println("Grid Contains " + gridLayout.getChildCount());
		for(int iter=0;iter<gridLayout.getChildCount();iter++){
			((ImageView) gridLayout.getChildAt(iter)).setImageResource(0);
		}
		TextView userMessage=findViewById(R.id.textViewMessage);
		userMessage.setText("");
		isGameActive=true;
		ImageView winLoseView=findViewById(R.id.imageViewWinLose);
		winLoseView.setImageResource(0);
		winLoseView.animate().alpha(0f);

		gridLayout.animate().alpha(1f).setDuration(0);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
}
