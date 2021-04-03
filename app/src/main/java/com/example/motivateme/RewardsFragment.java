package com.example.motivateme;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RewardsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RewardsFragment extends Fragment {
    private RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private SharedPreferences savedRewards = MainActivity.rewardsPreferences;
    private SharedPreferences savedPercentages = MainActivity.percentagesPreferences;

    public RewardsAdapter adapter;

    public RewardsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RewardsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RewardsFragment newInstance(String param1, String param2) {
        RewardsFragment fragment = new RewardsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);

        recyclerView = view.findViewById(R.id.rewardsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new RewardsAdapter();
        recyclerView.setAdapter(adapter);
        registerForContextMenu(recyclerView);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.rewardsButtonHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RewardsFragment.this)
                        .navigate(R.id.action_rewardsFragment_to_FirstFragment);
            }
        });

        view.findViewById(R.id.button_rewards_additem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: show pop up menu with edit text to add into recyclerview list
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Add Reward:");
                alert.setMessage("Enter a title or name to add to the list of rewards");

                // Set an EditText view to get user input
                final EditText input = new EditText(getActivity());
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String s = input.getText().toString();
                        int numOfRewards = PreferenceHelper.numPreferences(savedRewards);
                        PreferenceHelper.setValue(savedRewards, "Rewards_" + Integer.toString(numOfRewards), s);
                        PreferenceHelper.setValue(savedPercentages, "Percentages_" + Integer.toString(numOfRewards), "");
                        adapter.updateLists();
                        adapter.notifyDataSetChanged();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });

        view.findViewById(R.id.button_rewards_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceHelper.clearData(savedRewards);
                PreferenceHelper.clearData(savedPercentages);
                adapter.clearLists();
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle() == "Delete"){
            PreferenceHelper.removeItem(savedRewards, "Rewards_" + Integer.toString(adapter.getPosition()));
            PreferenceHelper.removeItem(savedPercentages, "Percentages_" + Integer.toString(adapter.getPosition()));
            adapter.reloadPreferences();
            adapter.updateLists();
            adapter.notifyDataSetChanged();
        }
        else if(item.getTitle() == "Edit"){
            AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

            alert.setTitle("Edit Reward:");

            // Set an EditText view to get user input
            final EditText input = new EditText(getActivity());
            alert.setView(input);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String s = input.getText().toString();
                    PreferenceHelper.setValue(savedRewards, "Rewards_" + Integer.toString(adapter.getPosition()), s);
                    PreferenceHelper.setValue(savedPercentages, "Percentages_" + Integer.toString(adapter.getPosition()), "");
                    adapter.updateLists();
                    adapter.notifyDataSetChanged();
                }
            });

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    // Canceled.
                }
            });

            alert.show();
        }
        return false;
    }
}