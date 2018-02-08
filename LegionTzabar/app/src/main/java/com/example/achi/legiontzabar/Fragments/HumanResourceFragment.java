package com.example.achi.legiontzabar.Fragments;


import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.achi.legiontzabar.DataBase.DBHelper;
import com.example.achi.legiontzabar.DataBase.DatabaseHelper;
import com.example.achi.legiontzabar.R;

import java.util.List;


/**
 *HumanResourceFragment - load data from soldier_table in the db and display its content in a table layout
 * for each row in the fragment the user can click and choos an action to perform - view detail, call or send email.
 * view detail - will display the soldier info fragment with all data on that soldier
 * call - will open caller app with the soldier number
 * send email - will open communication app with soldier email fill.
 */


public class HumanResourceFragment extends Fragment {


    public HumanResourceFragment() {
        // Required empty public constructor
    }

    /**
     * creatng UI element, loading soldiers from db and setting each row actions.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Context context = getActivity().getApplicationContext();

        //kill all fragment

        /* Decleration for communicating with the layout*/
        View view = inflater.inflate(R.layout.fragment_human_resource, container, false);
        Button btnQualify = view.findViewById(R.id.btnQualify);
        Button addButton = view.findViewById(R.id.addButton);



         /* When Qualification button is pressed, its open Qualification fragment*/
        btnQualify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearTablerows(getView());
                Fragment fragment = new QualificationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });

          /* When add button is pressed, its open addNewSolider fragment*/
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClearTablerows(getView());
                Fragment fragment = new AddNewSoliderFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });

        fillTableFromDB(view);

        return view;
    }

    //clear the table row from the table layout
    @Override
    public void onPause() {
        super.onPause();
        ClearTablerows(getView());
    }

    // onResume load line fomr databse - in case data was changed
    @Override
    public void onResume() {
        super.onResume();
        fillTableFromDB(getView());

    }


    /**
     * Helper method loading the soldier from databse to screen
     * @param view
     */
    @SuppressLint("ClickableViewAccessibility")
    private void fillTableFromDB(View view) {

        final TableLayout tableLayout = view.findViewById(R.id.tableLayout1);
        tableLayout.removeAllViews();
        tableLayout.setStretchAllColumns(true);
        tableLayout.setShrinkAllColumns(true);


        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        final Cursor cursor = databaseHelper.getAllSoldiers();

        if(!cursor.moveToFirst()) {
            //cursor is empty - soldier table is empty
            return;
        }



        do {

            final TableRow tableRow = new TableRow(this.getContext());

            //column 1
            final TextView platoon = new TextView(this.getContext());
            platoon.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_COMPANY)));
            platoon.setGravity(Gravity.CENTER_HORIZONTAL);
            platoon.setPadding(50, 5, 0, 5);
            platoon.setLongClickable(true);


            //column 2
            final TextView mid = new TextView(this.getContext());
            mid.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_MID)));
            mid.setGravity(Gravity.CENTER_HORIZONTAL);
            mid.setPadding(100, 5, 0, 5);
            mid.setLongClickable(true);

            //column 3
            final TextView name = new TextView(this.getContext());
            name.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_FIRST_NAME)) + " " + cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_LAST_NAME)));
            name.setGravity(Gravity.CENTER_HORIZONTAL);
            name.setPadding(0, 5, 0, 5);
            name.setLongClickable(true);

            //column 4 - invisible
            final TextView phone_number = new TextView(this.getContext());
            phone_number.setVisibility(View.GONE);
            phone_number.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_PHONE_NUMBER)));

            //column 5 - invisible
            final TextView email = new TextView(this.getContext());
            email.setVisibility(View.GONE);
            email.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_EMAIL)));

            //on long click show popup menu
            platoon.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    showPopupMenu(email,phone_number,mid,tableRow);
                    return true;
                }
            });

            //on long click show popup menu
            name.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showPopupMenu(email,phone_number,mid,tableRow);
                    return true;
                }
            });



            //on long click show popup menu
            mid.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    showPopupMenu(email,phone_number,mid,tableRow);
                    return true;
                }
            });


            //add the view to the table row
            tableRow.addView(platoon);
            tableRow.addView(mid);
            tableRow.addView(name);

            //add the row to table layout
            tableLayout.addView(tableRow);


        }while(cursor.moveToNext());
    }

    private void showPopupMenu(final TextView email, final TextView phone_number,final TextView mid , TableRow tableRow)
    {
        PopupMenu popup = new PopupMenu(getContext(), tableRow);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.hr_table_context_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.sendEmail: {
                        sendEmailPopupMenuAction(email.getText().toString());
                        return true;
                    }
                    case R.id.callSoldier: {
                        callsoldierPopupAction(phone_number.getText().toString());
                        return true;
                    }
                    case R.id.soldierInfo: {
                        openSoldierInfo(mid.getText().toString());
                    }
                    default: {
                        return false;
                    }
                }
            }
        });
        popup.show();
    }

    /**
     * clear table row
     * @param view
     */
    private void ClearTablerows(View view)
    {
        TableLayout tableLayout = view.findViewById(R.id.tableLayout1);
        tableLayout.removeAllViews();
    }

    //user chose to display soldier info - opening fragment and pass what soldier was choosing with
    //shared preferences.
    private void openSoldierInfo(String mid) {
        //TODO create String Constant in abstract calss
        //save data to shared preferences
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("soldierInfoMid" , mid);
        editor.commit();

        //open soldier detail fragment
        Fragment fragment = new SoliderDetailFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
        transaction.addToBackStack(null);  // this will manage backstack
        transaction.commit();

    }

    //opening the dailer with soldier phone
    private void callsoldierPopupAction(String phone_number)
    {
        phone_number = phone_number.replace("-","");
        Uri number = Uri.parse("tel: " + phone_number);
        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
        this.getContext().startActivity(callIntent);
    }
    //opening communication fragment with soldier data- share data with shared preferences
    private void sendEmailPopupMenuAction(String emailToSend)
    {
        //TODO create String Constant in abstract calss

        //save data to shared preferences
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("emailToSend" ,emailToSend );
        editor.apply();

        //open soldier detail fragment
        Fragment fragment = new CommunicationFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
        transaction.addToBackStack(null);  // this will manage backstack
        transaction.commit();
    }



}
