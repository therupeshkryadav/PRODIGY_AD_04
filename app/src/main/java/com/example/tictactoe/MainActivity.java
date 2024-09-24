package com.example.tictactoe;

import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private int[][] board;
    private boolean player1Turn = true;
    private int player1Score = 0;
    private int player2Score = 0;
    private TextView player1ScoreView;
    private TextView player2ScoreView;
    private TextView winMessage;
    private ImageView[][] imageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1ScoreView = findViewById(R.id.tv_player1_score);
        player2ScoreView = findViewById(R.id.tv_player2_score);
        winMessage = findViewById(R.id.win_message);

        board = new int[3][3];
        imageViews = new ImageView[3][3];
        GridLayout gridLayout = findViewById(R.id.board);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                final int row = i;
                final int col = j;
                imageViews[i][j] = (ImageView) gridLayout.getChildAt(i * 3 + j);
                imageViews[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBoardClick(row, col, (ImageView) v);
                    }
                });
            }
        }

        findViewById(R.id.reset_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetBoard();
            }
        });
    }

    private void onBoardClick(int row, int col, ImageView imageView) {
        if (board[row][col] == 0 && !isGameOver()) {
            board[row][col] = player1Turn ? 1 : 2;
            imageView.setImageResource(player1Turn ? R.drawable.cross : R.drawable.circle);
            if (checkWin()) {
                if (player1Turn) {
                    player1Score++;
                    player1ScoreView.setText("Player 1: " + player1Score);
                    winMessage.setText("Player 1 wins!");
                } else {
                    player2Score++;
                    player2ScoreView.setText("Player 2: " + player2Score);
                    winMessage.setText("Player 2 wins!");
                }
                winMessage.setVisibility(View.VISIBLE);
                disableBoard();
            }
            player1Turn = !player1Turn;
        }
    }

    private boolean checkWin() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            return true;
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0) {
            return true;
        }
        return false;
    }

    private boolean isGameOver() {
        // Check if the game is over (either win or draw)
        if (checkWin()) {
            return true;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void disableBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                imageViews[i][j].setEnabled(false);
            }
        }
    }

    private void resetBoard() {
        winMessage.setVisibility(View.INVISIBLE);
        player1Score = 0;
        player2Score = 0;
        player1ScoreView.setText("Player 1: 0");
        player2ScoreView.setText("Player 2: 0");

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = 0;
                imageViews[i][j].setImageResource(0);
                imageViews[i][j].setEnabled(true);
            }
        }
        player1Turn = true;
    }
}
