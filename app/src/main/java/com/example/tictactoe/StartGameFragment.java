package com.example.tictactoe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class StartGameFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_game, container, false);

        Button singlePlayerButton = view.findViewById(R.id.singlePlayerButton);
        singlePlayerButton.setOnClickListener(view1 -> {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            if(fragmentTransaction != null) {
                Log.d("StartGameFragment", "int IF Block");
                Toast.makeText(getActivity(),"Single Player Bot hasn't designed.", Toast.LENGTH_SHORT).show();
//                {
//                    SINGLE_PLAYER = true;
//                    TWO_PLAYER= false;
//
//                    Log.d("StartGameFragment", "int try Block");
//                    fragmentTransaction.replace(R.id.fragment_container_view, PlayGameFragment.class, null);
//                    fragmentTransaction.setReorderingAllowed(true);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
//                }
            }
            else {
                Log.d("StartGameFragment", "int ELSE Block");
                Toast.makeText(getActivity(), "Can't do the framing", Toast.LENGTH_SHORT).show();
            }
        });

        Button twoPlayerButton = view.findViewById(R.id.twoPlayerButton);
        twoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, PlayGameFragment.class, null);
                fragmentTransaction.setReorderingAllowed(true);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    public static void removePlayGameFragment() {
        FragmentTransaction fragmentTransaction;
    }

}