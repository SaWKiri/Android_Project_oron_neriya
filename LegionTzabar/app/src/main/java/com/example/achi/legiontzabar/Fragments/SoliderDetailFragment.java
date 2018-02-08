package com.example.achi.legiontzabar.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.achi.legiontzabar.DataBase.DBHelper;
import com.example.achi.legiontzabar.DataBase.DatabaseHelper;
import com.example.achi.legiontzabar.R;


/**
 * Soldier Detail fragment - display soldier information - retreving soldier info from shared preferences
 */
public class SoliderDetailFragment extends Fragment {


    public SoliderDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_solider_detail, container, false);

        //retrive from shared preferences the soldier mid
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        final String soldierMid = sharedPref.getString("soldierInfoMid",null);

        //remove soldier mid from shared preferences
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("soldierInfoMid");
        editor.apply();

        new Thread(new Runnable() {
            @Override
            public void run() {
            //retrieved soldier info from database
            DatabaseHelper dbHelper = new DatabaseHelper(getContext());
            Cursor cursor =dbHelper.getSoldiersByMid(soldierMid);
            cursor.moveToFirst();

            //display soldier info on fragment
            TextView tvDBSoliderName = view.findViewById(R.id.tvDBSoliderName);
            tvDBSoliderName.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_FIRST_NAME)));

            TextView tvDBLastName = view.findViewById(R.id.tvDBLastName);
            tvDBLastName.setText( cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_LAST_NAME)));

            TextView tvDBPersonalId = view.findViewById(R.id.tvDBPersonalId);
            tvDBPersonalId.setText( cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_PID)));

            TextView tvDBPersonalNumber = view.findViewById(R.id.tvDBPersonalNumber);
            tvDBPersonalNumber.setText( cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_MID)));

            TextView tvDBGender = view.findViewById(R.id.tvDBGender);
            tvDBGender.setText( cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_GENDER)));

            TextView tvDBYamam = view.findViewById(R.id.tvDBYamam);
            tvDBYamam.setText( cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_SERVICE_DAYS)));

            TextView tvDBCellphone = view.findViewById(R.id.tvDBCellphone);
            tvDBCellphone.setText( cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_PHONE_NUMBER)));

            TextView tvDBMail = view.findViewById(R.id.tvDBMail);
            tvDBMail.setText( cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_EMAIL)));

            TextView tvDBAddress = view.findViewById(R.id.tvDBAddress);
            tvDBAddress.setText( cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_ADDRESS)));

            TextView tvDBRank = view.findViewById(R.id.tvDBRank);
            tvDBRank.setText( cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_RANK)));

            TextView tvDBOccupation = view.findViewById(R.id.tvDBOccupation);
            tvDBOccupation.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_OCCUPATION)));

            TextView tvDBInMatzeva = view.findViewById(R.id.tvDBInMatzeva);
            tvDBInMatzeva.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_IN_MATZEVA)));

            TextView tvDBPlatoon = view.findViewById(R.id.tvDBPlatoon);
            tvDBPlatoon.setText(cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_COMPANY)));

            TextView tvDBMilitaryOccupation = view.findViewById(R.id.tvDBMilitaryOccupation);
            tvDBMilitaryOccupation.setText( cursor.getString(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_MILITARY_OCCUPATION)));

            try {
                ImageView ivSoliderImageDetail = view.findViewById(R.id.ivSoliderImageDetail);
                ivSoliderImageDetail.setImageBitmap(convertBlobToImage(cursor.getBlob(cursor.getColumnIndex(DBHelper.SOLDIERS_TABLE_COL_IMAGE))));
            }catch (Exception e)
            {
                //probably no image ignoring it
                //do nothing
            }
            }
        }).start();

        //Button action - opening dialer with soldier phone
        Button btnCall = view.findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone_number = ((TextView) view.findViewById(R.id.tvDBCellphone)).getText().toString();
                phone_number = phone_number.replace("-","");
                Uri number = Uri.parse("tel: " + phone_number);
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                getContext().startActivity(callIntent);
            }
        });
        //Send mail Button action - open communication fragment with sodlier email
        Button btnSendMail = (Button) view.findViewById(R.id.btnSendMail);
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String email = ((TextView) view.findViewById(R.id.tvDBMail)).getText().toString();

            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("emailToSend" ,email );
            editor.apply();

            //open soldier detail fragment
            Fragment fragment = new CommunicationFragment();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
            transaction.addToBackStack(null);  // this will manage backstack
            transaction.commit();
            }
        });

        return view;
    }

    /**
     * Helper method convert byte array to image
     * @param blob the byte array from Database
     * @return data as bitmap
     */
    private Bitmap convertBlobToImage(byte[] blob) {

        return BitmapFactory.decodeByteArray(blob,0,blob.length);
    }

}
