package com.example.achi.legiontzabar.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.achi.legiontzabar.R;


/**
 * AddNewTraining - add new Training to Database - NOT IMPLEMENTED
 */
public class AddNewTraining extends Fragment {
    EditText trainingDate,mistatfim;

    public AddNewTraining() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        /* Decleration for communicating with the layout*/
        View view= inflater.inflate(R.layout.fragment_add_new_training, container, false);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);
        Button btnOk = (Button) view.findViewById(R.id.btnOk);
        trainingDate = (EditText) view.findViewById(R.id.etTrainingDate);
        mistatfim = (EditText) view.findViewById(R.id.etMishtatfim);
        /*Button cancel press opens Training Fragment*/

        btnCancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Fragment fragment= new TrainingFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String trainingD = trainingDate.getText().toString();
                String mistatfims = mistatfim.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("TrainingDate",trainingD);
                bundle.putString("Mistatfim",mistatfims);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                TrainingFragment traingingFragment = new TrainingFragment();
                traingingFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fram,traingingFragment);
                fragmentTransaction.commit();

            }
        });
        return view;
    }

}
