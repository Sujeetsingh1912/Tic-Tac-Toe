package com.app.tictacto;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Best extends AppCompatActivity implements View.OnClickListener {

    private boolean playerOneActive = true;
    private int[] gameState = new int[9]; // 0 = empty, 1 = player1, 2 = player2
    private int playerOneScoreCount = 0, playerTwoScoreCount = 0, roundCount = 0;

    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button reset, playagain;

    private String player1Name, player2Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_best);

        playerOneScore = findViewById(R.id.score_player1);
        playerTwoScore = findViewById(R.id.score_player2);
        playerStatus = findViewById(R.id.status);

        reset = findViewById(R.id.button_reset);
        playagain = findViewById(R.id.Play_again);

        // get player names
        player1Name = getIntent().getStringExtra("PLAYER1_NAME");
        player2Name = getIntent().getStringExtra("PLAYER2_NAME");
        if (player1Name == null || player1Name.trim().isEmpty()) player1Name = "Player 1";
        if (player2Name == null || player2Name.trim().isEmpty()) player2Name = "Player 2";

        // initialize board
        for (int i = 0; i < 9; i++) gameState[i] = 0;

        // Initialize buttons with correct IDs
        buttons[0] = findViewById(R.id.btn0);
        buttons[1] = findViewById(R.id.btn1);
        buttons[2] = findViewById(R.id.btn2);
        buttons[3] = findViewById(R.id.btn3);
        buttons[4] = findViewById(R.id.btn4);
        buttons[5] = findViewById(R.id.btn5);
        buttons[6] = findViewById(R.id.btn6);
        buttons[7] = findViewById(R.id.btn7);
        buttons[8] = findViewById(R.id.btn8);

        // Set click listeners with null checks
        for (Button button : buttons) {
            if (button != null) {
                button.setOnClickListener(this);
            } else {
                Toast.makeText(this, "Error: Button not found!", Toast.LENGTH_SHORT).show();
            }
        }

        reset.setOnClickListener(v -> resetGame());
        playagain.setOnClickListener(v -> playAgain());

        updatePlayerScore();
        playerStatus.setText(player1Name + "'s Turn (X)");
    }

    @Override
    public void onClick(View view) {
        Button clickedBtn = (Button) view;
        int btnIndex = getButtonIndex(clickedBtn);

        if (btnIndex == -1 || gameState[btnIndex] != 0) return;

        if (playerOneActive) {
            clickedBtn.setText("X");
            clickedBtn.setTextColor(Color.RED);
            gameState[btnIndex] = 1;
        } else {
            clickedBtn.setText("O");
            clickedBtn.setTextColor(Color.BLUE);
            gameState[btnIndex] = 2;
        }

        roundCount++;

        if (checkWinner()) {
            if (playerOneActive) {
                playerOneScoreCount++;
                playerStatus.setText(player1Name + " Wins!");
            } else {
                playerTwoScoreCount++;
                playerStatus.setText(player2Name + " Wins!");
            }
            updatePlayerScore();
            disableButtons();
        } else if (roundCount == 9) {
            playerStatus.setText("It's a Draw!");
        } else {
            playerOneActive = !playerOneActive;
            playerStatus.setText(playerOneActive ? player1Name + "'s Turn (X)" : player2Name + "'s Turn (O)");
        }
    }

    private int getButtonIndex(Button button) {
        int id = button.getId();
        if (id == R.id.btn0) return 0;
        if (id == R.id.btn1) return 1;
        if (id == R.id.btn2) return 2;
        if (id == R.id.btn3) return 3;
        if (id == R.id.btn4) return 4;
        if (id == R.id.btn5) return 5;
        if (id == R.id.btn6) return 6;
        if (id == R.id.btn7) return 7;
        if (id == R.id.btn8) return 8;
        return -1;
    }

    private boolean checkWinner() {
        int[][] winningPositions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] pos : winningPositions) {
            if (gameState[pos[0]] != 0 &&
                    gameState[pos[0]] == gameState[pos[1]] &&
                    gameState[pos[0]] == gameState[pos[2]]) {
                return true;
            }
        }
        return false;
    }

    private void updatePlayerScore() {
        playerOneScore.setText(String.valueOf(playerOneScoreCount));
        playerTwoScore.setText(String.valueOf(playerTwoScoreCount));
    }

    private void resetGame() {
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        updatePlayerScore();
        playAgain();
    }

    private void playAgain() {
        roundCount = 0;
        playerOneActive = true;
        for (int i = 0; i < 9; i++) {
            gameState[i] = 0;
            buttons[i].setText("");
            buttons[i].setEnabled(true);
        }
        playerStatus.setText(player1Name + "'s Turn (X)");
    }

    private void disableButtons() {
        for (Button button : buttons) {
            button.setEnabled(false);
        }
    }

}







