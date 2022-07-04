package com.example.tictactoe;

import android.app.appsearch.GetByDocumentIdRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class PlayGameFragment extends Fragment {

    public static boolean PLAYER_1_TURN, PLAYER_2_TURN, PLAYER_1_WON, PLAYER_2_WON, GAME_TIED;
    public static TextView playerInfoTextView;
    public static char PLAYER_1_MARK, PLAYER_2_MARK;
    public static int numTurns;
    private static View view;
    private static char[][] board;
    private static ArrayList<Integer> buttonId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Initialising the static variables

        view = inflater.inflate(R.layout.fragment_play_game, container, false);
        numTurns = 0;
        PLAYER_1_WON = false;
        PLAYER_2_WON = false;
        GAME_TIED = false;
        PLAYER_1_TURN = true;
        PLAYER_2_TURN = false;
        board = new char[][] {
                {'\0', '\0', '\0'},
                {'\0', '\0', '\0'},
                {'\0', '\0', '\0'}
        };
        playerInfoTextView = view.findViewById(R.id.playerInfoTextView);
        playerInfoTextView.setText("Player 1 Turn");

        // setting up the Marks
        Random r = new Random();
        int i = r.nextInt(2);

        if(i == 0) {
            PLAYER_2_MARK = 'O';
            PLAYER_1_MARK = 'X';
        } else {
            PLAYER_2_MARK = 'X';
            PLAYER_1_MARK = 'O';
        }


        buttonId = new ArrayList<>();
        buttonId.add(R.id.button_11);
        buttonId.add(R.id.button_12);
        buttonId.add(R.id.button_13);
        buttonId.add(R.id.button_21);
        buttonId.add(R.id.button_22);
        buttonId.add(R.id.button_23);
        buttonId.add(R.id.button_31);
        buttonId.add(R.id.button_32);
        buttonId.add(R.id.button_33);

        for (Integer id : buttonId) {
            Button button = view.findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Main Algorithm of handling the game

                    TextView t = PlayGameFragment.view.findViewById(R.id.playerInfoTextView);

                    int index = PlayGameFragment.buttonId.indexOf(button.getId());
                    int i = index/3;
                    int j = index%3;
                    Log.d("Index", i + " " + j);
                    PlayGameFragment.numTurns++;
                    if (PlayGameFragment.PLAYER_1_TURN) {
                        button.setText(String.valueOf(PlayGameFragment.PLAYER_1_MARK));
                        PlayGameFragment.updateBoard(i, j, PLAYER_1_MARK);
                        PlayGameFragment.PLAYER_1_TURN = false;
                        PlayGameFragment.PLAYER_2_TURN = true;
                        t.setText("Player 2 Turn");
                    } else if (PlayGameFragment.PLAYER_2_TURN) {
                        button.setText(String.valueOf(PlayGameFragment.PLAYER_2_MARK));
                        PlayGameFragment.updateBoard(i, j, PLAYER_2_MARK);
                        PlayGameFragment.PLAYER_2_TURN = false;
                        PlayGameFragment.PLAYER_1_TURN = true;
                        t.setText("Player 1 Turn");
                    }

                    // setting the next turns and disabling the button
                    button.setEnabled(false);

                    // Check Whether the game ends , if ends then declare the winner
                    if(PlayGameFragment.isGameCompleted()) {
                        if(PlayGameFragment.PLAYER_1_WON) {
                            t.setText("Player 1 Won");
                        } else if(PlayGameFragment.PLAYER_2_WON) {
                            t.setText("Player 2 Won");
                        }
                        else {
                            t.setText("It's a Draw");
                        }
                        ViewGroup v = PlayGameFragment.view.findViewById(R.id.gameOverLayout);
                        v.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        Button resetButton = view.findViewById(R.id.resetGameButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, PlayGameFragment.class, null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Button gotoHomeButton = view.findViewById(R.id.gotoHomeButton);
        gotoHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        return view;
    }

    private static boolean isGameCompleted() {
        PLAYER_1_WON = isPlayerWon(PLAYER_1_MARK);
        if(PLAYER_1_WON) return true;
        PLAYER_2_WON = isPlayerWon(PLAYER_2_MARK);
        if(PLAYER_2_WON) return true;
        GAME_TIED = numTurns == 9;
        return GAME_TIED;
    }

    private static boolean isPlayerWon(char mark) {

        boolean isWon = false;
        int id1 = -1, id2 = -1, id3 = -1;
        // horizantal wins
        if(board[0][0] == mark && board[0][0] == board[0][1] && board[0][1] == board[0][2]) {
            Log.d("IsPlayerWon : " + mark, board[0][0] + " " + board[0][1] + " " + board[0][2]);
            id1 = buttonId.get(0);
            id2 = buttonId.get(1);
            id3 = buttonId.get(2);
            isWon = true;
        }
        else if(board[1][0] == mark && board[1][0] == board[1][1] && board[1][1] == board[1][2]) {
            Log.d("IsPlayerWon : " + mark, board[1][0] + " " + board[1][1] + " " + board[1][2]);
            id1 = buttonId.get(3);
            id2 = buttonId.get(4);
            id3 = buttonId.get(5);
            isWon = true;
        }
        else if(board[2][0] == mark && board[2][0] == board[2][1] && board[2][1] == board[2][2]) {
            Log.d("IsPlayerWon : " + mark, board[2][0] + " " + board[2][1] + " " + board[2][2]);
            id1 = buttonId.get(6);
            id2 = buttonId.get(7);
            id3 = buttonId.get(8);
            isWon = true;
        }
        // vertical wins
        else if(board[0][0] == mark && board[0][0] == board[1][0] && board[1][0] == board[2][0]) {
            Log.d("IsPlayerWon : " + mark, board[0][0] + " " + board[1][0] + " " + board[2][0]);
            id1 = buttonId.get(0);
            id2 = buttonId.get(3);
            id3 = buttonId.get(6);
            isWon = true;
        }
        else if(board[0][1] == mark && board[0][1] == board[1][1] && board[1][1] == board[2][1]) {
            Log.d("IsPlayerWon : " + mark, board[0][1] + " " + board[1][1] + " " + board[2][1]);
            id1 = buttonId.get(1);
            id2 = buttonId.get(4);
            id3 = buttonId.get(7);
            isWon = true;
        }
        else if(board[0][2] == mark && board[0][2] == board[1][2] && board[1][2] == board[2][2]) {
            Log.d("IsPlayerWon : " + mark, board[0][2] + " " + board[1][2] + " " + board[2][2]);
            id1 = buttonId.get(2);
            id2 = buttonId.get(5);
            id3 = buttonId.get(8);
            isWon = true;
        }
        // left diagonal win
        else if(board[0][0] == mark && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            id1 = buttonId.get(0);
            Log.d("IsPlayerWon : " + mark, board[0][0] + " " + board[1][1] + " " + board[2][2]);
            id2 = buttonId.get(4);
            id3 = buttonId.get(8);
            isWon = true;
        }
        else if(board[0][2] == mark && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            id1 = buttonId.get(2);
            id2 = buttonId.get(4);
            id3 = buttonId.get(6);
            Log.d("IsPlayerWon : " + mark, board[0][2] + " " + board[1][1] + " " + board[2][0]);
            isWon = true;
        }

        if(isWon) {
            Log.d("IsPlayerWon : " + mark,"Won");
            Button btn = view.findViewById(id1);
            btn.setText("\u2713");
            btn = view.findViewById(id2);
            btn.setText("\u2713");
            btn = view.findViewById(id3);
            btn.setText("\u2713");
            disableEveryButton();
        }
        return isWon;
    }

    // updating the board
    public static void updateBoard(int i, int j, char mark) {
        board[i][j] = mark;
    }

    public static void disableEveryButton() {
        for(Integer id : buttonId) {
            Button b = view.findViewById(id);
            b.setEnabled(false);
        }
    }

    public static void enableEveryButton() {
        for(int i = 0; i<3; ++i) {
            for(int j = 0; j<3; ++j) {
                board[i][j] = '\0';
            }
        }
        for(Integer id : buttonId) {
            Button b = view.findViewById(id);
            b.setEnabled(true);
        }
    }
}