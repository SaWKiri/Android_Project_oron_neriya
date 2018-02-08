package com.example.achi.legiontzabar.Fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.achi.legiontzabar.DataBase.DBHelper;
import com.example.achi.legiontzabar.DataBase.DatabaseHelper;
import com.example.achi.legiontzabar.Fragments.AddNewQualificationFragment;
import com.example.achi.legiontzabar.Fragments.HumanResourceFragment;
import com.example.achi.legiontzabar.R;

import org.w3c.dom.Text;


/**
 * Qualification Fragment - display soldier qualification form database
 */
public class QualificationFragment extends Fragment {


    public QualificationFragment() {
        // Required empty public constructor
    }

    /**
     * setting up the UI with data from databse
     * setting button action
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        /* Decleration for communicating with the layout*/
        View view= inflater.inflate(R.layout.fragment_qualification, container, false);
        Button btnSadak = (Button) view.findViewById(R.id.btnSadak);
        Button addButton = (Button) view.findViewById(R.id.addButton);

        /* When Sadak button is pressed its open sadak fragment*/
        btnSadak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new HumanResourceFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });

        /* When add button is pressed its open addNewQualification fragment*/
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new AddNewQualificationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });
        fillTablefromDB(view);

        return view;

        // Inflate the layout for this fragment
    }

    /**
     * loading data from database and siaply as a table
     * @param view
     */
    private void fillTablefromDB(View view)
    {
        //TODO fix row padding
        TableLayout tableLayout = view.findViewById(R.id.tableLayout1);
        tableLayout.removeAllViews();
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        final Cursor cursor = databaseHelper.getAllQualification();

        if(!cursor.moveToFirst())
        {
            //qualification table is empty
            return;
        }


        do{
            TableRow tableRow = new TableRow(this.getContext());

            //column order from left to right
            //column 1
            TextView qualificationTextView = new TextView(this.getContext());
            qualificationTextView.setText(cursor.getString(cursor.getColumnIndex(DBHelper.QUALIFICATION_TABLE_COL_NAME)));
            qualificationTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            //qualificationTextView.setPadding(50,5,0,5);

            //column 2
            TextView companyTextView = new TextView(this.getContext());
            companyTextView.setText(databaseHelper.getCompanyByMid(cursor.getString(cursor.getColumnIndex(DBHelper.QUALIFICATION_TABLE_COL_MID))));
            companyTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            //companyTextView.setPadding(10,5,0,5);

            //column 3
            TextView midTextView = new TextView(this.getContext());
            midTextView.setText(cursor.getString(cursor.getColumnIndex(DBHelper.QUALIFICATION_TABLE_COL_MID)));
            //midTextView.setPadding(10,5,0,5);

            //column 4
            TextView nameTextView = new TextView(this.getContext());
            nameTextView.setText(databaseHelper.getSoldierFullNameByMid(cursor.getString(cursor.getColumnIndex(DBHelper.QUALIFICATION_TABLE_COL_MID))));

            //tableRow.setFocusable(true);

            tableRow.addView(qualificationTextView);
            tableRow.addView(companyTextView);
            tableRow.addView(midTextView);
            tableRow.addView(nameTextView);

            tableLayout.addView(tableRow);

        }while (cursor.moveToNext());


    }


}
