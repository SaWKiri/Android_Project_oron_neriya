package com.example.achi.legiontzabar.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.achi.legiontzabar.R;


/**
 * AddNewQualification fragmant - to add qualification to a soldier - NOT IMPLEMENTED
 */
public class AddNewQualificationFragment extends Fragment {


    public AddNewQualificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        /* Decleration for communicating with the layout*/

        View view= inflater.inflate(R.layout.fragment_add_new_qualification, container, false);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        /*Button cancel press opens Qualification Fragment*/

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new QualificationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

}
