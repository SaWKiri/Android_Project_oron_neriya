package com.example.achi.legiontzabar.Fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;

import com.example.achi.legiontzabar.DataBase.DBHelper;
import com.example.achi.legiontzabar.DataBase.DatabaseHelper;
import com.example.achi.legiontzabar.R;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Training Detail fragment - display training info - DEMO
 * demonstrating sendinf file store in the database to be open in msword
 */
public class TrainingDetails extends Fragment {


    private static final int REQUEST_PERMISSIONS_CODE_WRITE_STORAGE = 5;

    public TrainingDetails() {
        // Required empty public constructor
    }

    /**
     * setting up button action to open file in ms word
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.fragment_training_details, container, false);

        Button btnFile3 = (Button) view.findViewById(R.id.btnFile3);
        Button btnFile2 = (Button) view.findViewById(R.id.btnFile2);
        Button btnFile1 = (Button) view.findViewById(R.id.btnFile1);

        btnFile1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                //database access
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                Cursor cursor =dbHelper.getFileByEid("1", DBHelper.FILE_TABLE_FILE1_BLOB);

                //checking sql query not empty
                if(!cursor.moveToFirst())
                {
                    return;
                }

                //get file from databse as byte array
                byte[] file1 = cursor.getBlob(cursor.getColumnIndex(DBHelper.FILE_TABLE_FILE2_BLOB));


                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDateandTime = sdf.format(new Date());

                //creating file to save in Download directory
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                File outputFile = new File( path+ "/" +"file1_" + currentDateandTime + ".doc");

                //premission to write
                if(!checkPermission(5))
                {
                    return;
                }

                //creating the file in storage
                if(!outputFile.exists())
                {
                    try {
                        outputFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                try {
                    //write the byte to storage
                    FileOutputStream outputStream = new FileOutputStream(outputFile);

                    outputStream.write(file1);  //write the bytes and your done.

                } catch (Exception e) {
                    String m = e.getMessage();
                    e.printStackTrace();
                }
                //sharing the file to be open in msword using intent
                Uri uri = Uri.fromFile(outputFile);

                Intent intent = new Intent(Intent.ACTION_VIEW);

                if (outputFile.toString().contains(".doc") || outputFile.toString().contains(".docx")) {
                    // Word document
                    intent.setDataAndType(uri, "application/msword");
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);


            }
        });

        btnFile2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //database access
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                Cursor cursor =dbHelper.getFileByEid("1", DBHelper.FILE_TABLE_FILE2_BLOB);

                //checking sql query not empty
                if(!cursor.moveToFirst())
                {
                    return;
                }
                //saving the file from database as byte array
                byte[] file2 = cursor.getBlob(cursor.getColumnIndex(DBHelper.FILE_TABLE_FILE2_BLOB));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDateandTime = sdf.format(new Date());

                //saving the file in download directory
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                File outputFile = new File( path+ "/" +"file2_" + currentDateandTime+".doc");

                if(!checkPermission(5))
                {
                    return;
                }

                //create file if not exist
                if(!outputFile.exists())
                {
                    try {
                        outputFile.createNewFile();
                    } catch (IOException e) {
                        //failed to create file
                        Log.e("Error", e.getMessage());;
                    }
                }


                try {
                    //write the byte to storage
                    FileOutputStream outputStream = new FileOutputStream(outputFile);

                    outputStream.write(file2);  //write the bytes and your done.

                } catch (Exception e) {
                    //failed to write file
                    Log.e("Error", e.getMessage());
                    return;
                }

                //sharing the file with msword
                Uri uri = Uri.fromFile(outputFile);

                Intent intent = new Intent(Intent.ACTION_VIEW);

                if (outputFile.toString().contains(".doc") || outputFile.toString().contains(".docx")) {
                    // Word document
                    intent.setDataAndType(uri, "application/msword");
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);

            }
        });

        btnFile3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                //database access
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                Cursor cursor =dbHelper.getFileByEid("1", DBHelper.FILE_TABLE_FILE3_BLOB);

                //checking sql query result not empty
                if(!cursor.moveToFirst())
                {
                    return;
                }
                //saving the file from db to byte array
                byte[] file3 = cursor.getBlob(cursor.getColumnIndex(DBHelper.FILE_TABLE_FILE3_BLOB));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
                String currentDateandTime = sdf.format(new Date());

                //saving the file to download directory in storage
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                File outputFile = new File( path+ "/" +"file3_" + currentDateandTime+".doc");

                //check and aquier write permission
                if(!checkPermission(5))
                {
                    return;
                }
                //creating the file
                if(!outputFile.exists())
                {
                    try {
                        outputFile.createNewFile();
                    } catch (IOException e) {
                        //failed to create file
                        Log.e("Error", e.getMessage());
                        return;
                    }
                }


                try {
                    //write the file to storage
                    FileOutputStream outputStream = new FileOutputStream(outputFile);
                    outputStream.write(file3);  //write the bytes and your done.

                } catch (Exception e) {
                    //failed to write file to storage
                    return;
                }

                //sharing the file to be open with msword
                Uri uri = Uri.fromFile(outputFile);

                Intent intent = new Intent(Intent.ACTION_VIEW);

                if (outputFile.toString().contains(".doc") || outputFile.toString().contains(".docx")) {
                    // Word document
                    intent.setDataAndType(uri, "application/msword");
                }

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);


            }
        });


        // Inflate the layout for this fragment
        return view;


    }


    public String get_mime_type(String url) {
        String ext = MimeTypeMap.getFileExtensionFromUrl(url);
        String mime = null;
        if (ext != null) {
            mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
        }
        return mime;
    }

    private boolean checkPermission(int permissionCode)
    {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED   )
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(getActivity()
                        ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        ,permissionCode);

            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_CODE_WRITE_STORAGE) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            }
        }
    }


}
