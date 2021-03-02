package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String PLAYER_ONE_POINTS_KEY = "player_one_points";
    private static final String PLAYER_TWO_POINTS_KEY = "player_two_points";
    private static final String ROUND_COUNT_KEY = "round_count";
    private static final String PLAYER_ONE_TURN_KEY = "player_one_turn";


    private Button[][] buttons = new Button[3][3];

    private Button resetBtn;
    private TextView playerOneTV;
    private TextView playerTwoTV;

    private int playerOnePoints;
    private int playerTwoPoints;

    private boolean playerOneTurn = true;

    private int roundCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind views
        resetBtn = findViewById(R.id.btn_reset);
        playerOneTV = findViewById(R.id.text_view_p1);
        playerTwoTV = findViewById(R.id.text_view_p2);

        //bind buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                String buttonID = "btn_" + i + j;
                int id = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(id);
                buttons[i][j].setOnClickListener(this);
            }
        }

        resetBtn.setOnClickListener(v -> {
            resetGame();
        });

    }

    @Override
    public void onClick(View v) {

        //check if the button has already been clicked
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        //if hasn't been clicked write X or O
        if (playerOneTurn)
            ((Button) v).setText("X");
        else
            ((Button) v).setText("O");

        roundCount++;

        if (checkWinner()) {
            if (playerOneTurn)
                playerOneWin();
            else
                playerTwoWin();

        } else {
            if (roundCount == 9)
                draw();
            else
                playerOneTurn = !playerOneTurn;
        }


    }

    private boolean checkWinner() {

        //get string array as the text written on buttons
        String[][] fields = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                fields[i][j] = buttons[i][j].getText().toString();
            }
        }

        //check vertical matching
        for (int i = 0; i < 3; i++) {
            if (fields[i][0].equals(fields[i][1]) && fields[i][0].equals(fields[i][2]) && !fields[i][0].equals(""))
                return true;
        }

        //check horizontal matching
        for (int i = 0; i < 3; i++) {
            if (fields[0][i].equals(fields[1][i]) && fields[0][i].equals(fields[2][i]) && !fields[0][i].equals(""))
                return true;
        }

        //check diagonal
        if (fields[0][0].equals(fields[1][1]) && fields[0][0].equals(fields[2][2]) && !fields[0][0].equals(""))
            return true;

        //check inverse diagonal
        if (fields[0][2].equals(fields[1][1]) && fields[0][2].equals(fields[2][0]) && !fields[0][2].equals(""))
            return true;

        return false;
    }

    private void playerOneWin() {
        playerOnePoints++;
        Toast.makeText(this, "Player One Wins", Toast.LENGTH_SHORT).show();
        updateScoreText();
        resetBoard();
    }

    private void playerTwoWin() {
        playerTwoPoints++;
        Toast.makeText(this, "Player Two Wins", Toast.LENGTH_SHORT).show();
        updateScoreText();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void resetBoard() {

        //reset buttons text
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCount = 0;
        playerOneTurn = true;

    }

    private void updateScoreText() {
        playerOneTV.setText("Player 1: " + playerOnePoints);
        playerTwoTV.setText("Player 2: " + playerTwoPoints);
    }

    private void resetGame() {
        playerOnePoints = 0;
        playerTwoPoints = 0;
        updateScoreText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PLAYER_ONE_POINTS_KEY, playerOnePoints);
        outState.putInt(PLAYER_TWO_POINTS_KEY, playerTwoPoints);
        outState.putInt(ROUND_COUNT_KEY, playerTwoPoints);
        outState.putBoolean(PLAYER_ONE_TURN_KEY, playerOneTurn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        playerOnePoints = savedInstanceState.getInt(PLAYER_ONE_POINTS_KEY);
        playerTwoPoints = savedInstanceState.getInt(PLAYER_TWO_POINTS_KEY);
        playerOneTurn = savedInstanceState.getBoolean(PLAYER_ONE_TURN_KEY);
        roundCount = savedInstanceState.getInt(ROUND_COUNT_KEY);
    }
}