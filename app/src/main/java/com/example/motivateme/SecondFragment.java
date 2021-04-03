package com.example.motivateme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Integer.parseInt;

public class SecondFragment extends Fragment {
    private SharedPreferences savedNumRewards = MainActivity.numRewardsPreferences;
    private SharedPreferences savedPercentages = MainActivity.percentagesPreferences;
    private SharedPreferences savedRewards = MainActivity.rewardsPreferences;
    private ArrayList<Integer> chances = new ArrayList<Integer>();
    private ArrayList<String> rewards = new ArrayList<String>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView rew = (TextView)getView().findViewById(R.id.textView4);
        rew.setText("Rewards remaining: " + PreferenceHelper.getValue(savedNumRewards, "numRewards"));

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        view.findViewById(R.id.rollButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i < PreferenceHelper.numPreferences(savedPercentages); i++){
                    chances.add(parseInt(PreferenceHelper.getValue(savedPercentages, "Percentages_" + Integer.toString(i))));
                    rewards.add(PreferenceHelper.getValue(savedRewards, "Rewards_" + Integer.toString(i)));
                }
                sortStuff();

                int numRewardsRemaining = parseInt(PreferenceHelper.getValue(savedNumRewards, "numRewards"));
                TextView tv = (TextView)getView().findViewById(R.id.rollText);
                if(numRewardsRemaining > 0){
                    String rolledReward = CalculateReward(Roll());
                    tv.setText(rolledReward);
                    numRewardsRemaining--;
                    PreferenceHelper.setValue(savedNumRewards, "numRewards", Integer.toString(numRewardsRemaining));
                }
                else{
                    tv.setText("No Rewards Remaining");
                }

                TextView rew = (TextView)getView().findViewById(R.id.textView4);
                rew.setText("Rewards remaining: " + PreferenceHelper.getValue(savedNumRewards, "numRewards"));
            }
        });
    }

    private int Roll() {
        int rolledValue = 0;
        Random rand = new Random();
        rolledValue = rand.nextInt(101);
        return rolledValue;
    }

    private String CalculateReward(int rollVal){
        int sum = 0;
        for(int j = 0; j < chances.size(); j++){
            sum += chances.get(j);
        }
        for(int i = 0; i < chances.size(); i++){
            int numToCheck = sum - chances.get(i);
            if(rollVal > numToCheck){
                return rewards.get(i);
            }
        }
        return "No Reward";
    }

    private void sortStuff(){
        boolean sorted = false;
        int temp;
        String tempString;
        while(!sorted){
            sorted = true;
            for(int i = 0; i < chances.size() - 1; i++){
                if(chances.get(i) > chances.get(i + 1)){
                    temp = chances.get(i);
                    tempString = rewards.get(i);
                    chances.set(i, chances.get(i + 1));
                    rewards.set(i, rewards.get(i + 1));
                    chances.set(i + 1, temp);
                    rewards.set(i + 1, tempString);
                    sorted = false;
                }
            }
        }
    }
}