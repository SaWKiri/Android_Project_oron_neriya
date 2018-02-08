package com.example.achi.legiontzabar.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.achi.legiontzabar.Activitys.LoginActivity;
import com.example.achi.legiontzabar.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * CommunicationFragment
 * In this fragment we can send email to groups or individual
 * sending intent to Gmail with email to send and content
 */
public class CommunicationFragment extends Fragment {

    private EditText edit ;
    private Spinner spinner;
    private String Other = "אחר";
    private String Mifkada = "מפקדה";
    private String Mahlakti = "מחלקתי";
    private String Plugati = "פלוגתי";
    public CommunicationFragment() {
        // Required empty public constructor
    }

    /**
     * creating the menu, choosing group or custom email to open in Gmail app
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO fix email keyboard
        /* Decleration for communicating with the layout*/
        View view = inflater.inflate(R.layout.fragment_communication, container, false);
        final Button sendMail = (Button) view.findViewById(R.id.btnSend);
        Button btnAddFiles = (Button) view.findViewById(R.id.btnAddFiles);
        //EditText etMailAddress = (EditText) view.findViewById(R.id.etMailAdress);
        final EditText etMail = (EditText) view.findViewById(R.id.etMail);
        edit = (EditText) view.findViewById(R.id.etMailAdress);

        /* values for the Spinner into array and Spinner choice save*/
        final String[] values = {Plugati, Mahlakti, Mifkada, Other};
        spinner = (Spinner) view.findViewById(R.id.spinnerContactL);
        final ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        LTRadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(LTRadapter);

        //action on selecting an item in the spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                String value = (String)adapterView.getItemAtPosition(i);

                if(value.equals(Other))
                {

                    edit.setEnabled(true);
                    edit.setFocusableInTouchMode(true);
                    edit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    edit.setFocusable(true);
                    sendMail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // context of the mail from application to a string
                        String str = etMail.getText().toString();
                        //strEmailAddress= edit.getText().toString();
                        // opens gmail and put subject/context...
                        //TODO if email was wrong dont open Action send.
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("message/rfc822");
                        i.putExtra(Intent.EXTRA_EMAIL,  getEmailByCustom());
                       // i.putExtra(Intent.EXTRA_EMAIL, getEmailByGroup(getSelectedItemFromSpinner()));
                        i.putExtra(Intent.EXTRA_SUBJECT, getSelectedItemFromSpinner());
                        i.putExtra(Intent.EXTRA_TEXT, str);
                        i.setPackage("com.google.android.gm");

                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }

                        }
                    });

                }
                else
                {
                    edit.setEnabled(false);
                    edit.setInputType(InputType.TYPE_NULL);
                    edit.setFocusable(false);
                    sendMail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        // context of the mail from application to a string
                        String str = etMail.getText().toString();
                        int y;

                        // opens gmail and put subject/context...
                        Intent i = new Intent(Intent.ACTION_SEND);

                        i.setType("message/rfc822");
                        //i.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");

                        i.putExtra(Intent.EXTRA_EMAIL, getEmailByGroup(getSelectedItemFromSpinner()));
                        i.putExtra(Intent.EXTRA_SUBJECT, getSelectedItemFromSpinner());
                        i.putExtra(Intent.EXTRA_TEXT, str);
                        i.setPackage("com.google.android.gm");
                        try {
                            startActivity(Intent.createChooser(i, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                        }
                        }
                    });

                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

                Toast.makeText(getActivity(), "נשמר", Toast.LENGTH_SHORT).show();
            }
        });


        //retrieve from shared preferences the soldier mid
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String soldierEmail = sharedPref.getString("emailToSend",null);

        if(soldierEmail == null || soldierEmail.equals(""))
        {
            return view;
        }

        spinner.setSelection(3);

        edit.setText(soldierEmail);


        //remove soldier mid from shared preferences
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("emailToSend");
        editor.apply();

        return view;
    }

    //TODO to access db - oron
    private String getSelectedItemFromSpinner() {
        return spinner.getSelectedItem().toString();
    }


    public String[] getEmailByGroup(String str)
    {
        return new String[]{"neriyayona@gmail.com"};
    }


    private String[] getEmailByCustom()
    {
        String text="";

        text += edit.getText().toString()+";";
        return new String[]{text};
    }

}
