package com.example.achi.legiontzabar.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.achi.legiontzabar.R;


/**
 * Training fragment - display the training in the database - NOT IMPLEMENTED
 */
public class TrainingFragment extends Fragment {
    String trainingDate,mistatfim;

    public TrainingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
          /* Decleration for communicating with the layout*/
        View view= inflater.inflate(R.layout.fragment_trainging, container, false);
        Button addButton = (Button) view.findViewById(R.id.addButton);
        Bundle bundle = getArguments();
        if (bundle != null)
        {
            TextView trainingDateTv = (TextView) view.findViewById(R.id.tvTrainingDate);
            TextView mistatfimTv = (TextView) view.findViewById(R.id.tvMistatfim);
            trainingDate = (String) bundle.get("TrainingDate");
            mistatfim = (String) bundle.get("Mistatfim");
            trainingDateTv.setText(trainingDate);
            mistatfimTv.setText(mistatfim);

        }

        /* When add button is pressed, its open addNewSolider fragment*/
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new AddNewTraining();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });


        return view;

    }
    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("TrainingDate",trainingDate);
        outState.putString("Mistatfim",mistatfim);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null)
        {
            // Restore last state for checked position.
            trainingDate = (String) savedInstanceState.get("TrainingDate");
            mistatfim = (String) savedInstanceState.get("Mistatfim");
        }
    }
}
