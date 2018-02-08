package com.example.achi.legiontzabar.Fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.achi.legiontzabar.DataBase.DatabaseHelper;
import com.example.achi.legiontzabar.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;


/**
 * AddNewSoliderFragment - add new soldier to database
 */
public class AddNewSoliderFragment extends Fragment {

    //local variable
    private int REQUEST_CODE = 1;
    private ImageView imageview;
    private Button btnOkClicked;
    private Bitmap bitmap;
    private File destination = null;
    private InputStream inputStreamImg;
    private String imgPath = null;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;
    private int PERMISSION_REQUEST = 61;

    public AddNewSoliderFragment() {

        // Required empty public constructor
    }

    /**
     * On create fragment - defining the UI elements and their action
     * setting OnClickListener for imageView and UI button.
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
        final View view= inflater.inflate(R.layout.fragment_add_new_solider, container, false);
        Button btnCancel = (Button) view.findViewById(R.id.btnCancel);

        /*Button cancel press opens HumanResourceFragment Fragment*/
        imageview = (ImageView) view.findViewById(R.id.ivSoliderImage);
        btnOkClicked = (Button) view.findViewById(R.id.btnOk);

        //OnbtnSelectImage click event...
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new HumanResourceFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fram, fragment); // fragmen container id in first parameter is the  container(Main layout id) of Activity
                transaction.addToBackStack(null);  // this will manage backstack
                transaction.commit();
            }
        });

        btnOkClicked.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //TODO input checks
                    String etNameSolider = ((EditText) view.findViewById(R.id.etNameSolider)).getText().toString();
                    String etPersonalNumber = ((EditText) view.findViewById(R.id.etPersonalNumber)).getText().toString();
                    String etYamam = ((EditText) view.findViewById(R.id.etYamam)).getText().toString();
                    String etCellphone = ((EditText) view.findViewById(R.id.etCellphone)).getText().toString();
                    String etMail = ((EditText) view.findViewById(R.id.etMail)).getText().toString();
                    String etAddress = ((EditText) view.findViewById(R.id.etAddress)).getText().toString();
                    String etIsraeliId = ((EditText) view.findViewById(R.id.etIsraeliId)).getText().toString();
                    String etGender = ((EditText) view.findViewById(R.id.etGender)).getText().toString();
                    String etLastName = ((EditText) view.findViewById(R.id.etLastName)).getText().toString();
                    String etRank = ((EditText) view.findViewById(R.id.etRank)).getText().toString();
                    String etOccupation = ((EditText) view.findViewById(R.id.etOccupation)).getText().toString();
                    String etInMatzeva = ((EditText) view.findViewById(R.id.etInMatzeva)).getText().toString();
                    String etPlatoon = ((EditText) view.findViewById(R.id.etPlatoon)).getText().toString();
                    String etMilitaryOccupation = ((EditText) view.findViewById(R.id.etMilitaryOccupation)).getText().toString();
                    ImageView ivSoliderImage = ((ImageView) view.findViewById(R.id.ivSoliderImage));

                    //convert image to byte array to store in Database
                    Bitmap bitmap = ((BitmapDrawable) ivSoliderImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageInByte = baos.toByteArray();


                    DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                    boolean result = dbHelper.insertNewSolider(Long.parseLong(etPersonalNumber), Long.parseLong(etIsraeliId),
                            etNameSolider, etLastName, etGender, etCellphone, etMail, etAddress, Integer.parseInt(etYamam), etRank, etOccupation, etInMatzeva, etPlatoon, etMilitaryOccupation, imageInByte);

                    if (!result) {
                        dispalyToast("נכשל לשמור חייל");
                    }
                    dispalyToast("חייל נשמר");

                    //returning to Humane resource fragment
                    HumanResourceFragment fragment = new HumanResourceFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fram,fragment,"HumanResourceFragment");
                    fragmentTransaction.commit();
                }

            });




        return view;

    }

    private boolean checkPermission()
    {
        if(ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
             || ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED   )
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                      ActivityCompat.requestPermissions(this.getActivity()
                      ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}
                      ,PERMISSION_REQUEST);

            }
            return false;
        }
        return true;
    }



    // Select image from camera and gallery
    private void selectImage() {
            final CharSequence[] options = {"צלם תמונה", "בחר מהגלריה", "ביטול"};
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
            builder.setTitle("בחר אופציה");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                if(checkPermission()){


                    if (options[item].equals("צלם תמונה")) {

                        dialog.dismiss();
                        pickCamera();

                    } else if (options[item].equals("בחר מהגלריה")) {
                        dialog.dismiss();
                        checkPermission();
                        pickGallery();
                    } else if (options[item].equals("ביטול")) {
                        dialog.dismiss();
                    }
                }

                }
            });
            builder.show();

        }

    /**
     * open camera app to take picture of the soldier
     * picture return in Intent activity result
     */
    public void pickCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PICK_IMAGE_CAMERA);
    }

    /**
     * open gallery to choose an image of the soldier
     */
    public void pickGallery()
    {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, PICK_IMAGE_GALLERY);
    }

    /**
     * proccessing the result from camera or gallery app
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        inputStreamImg = null;

        if (requestCode == PICK_IMAGE_CAMERA) {
            try {
                Uri selectedImage = data.getData();
                bitmap = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

                Log.e("Activity", "Pick from Camera::>>> ");
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

                destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) , "/" +
                         getString(R.string.app_name)+ "IMG_" + timeStamp + ".jpg");

                FileOutputStream fo;
                try {
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imgPath = destination.getAbsolutePath();
                Toast.makeText(this.getActivity(),imgPath,Toast.LENGTH_SHORT).show();
                Log.e("Activity", imgPath);
                imageview.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_GALLERY)
        {

            try {
                Uri selectedImage = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                int orientation = getOrientation(this.getActivity(),selectedImage);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                Log.e("Activity", "Pick from Gallery::>>> ");
                Matrix matrix = new Matrix();
                switch (orientation)
                {
                    case 90:
                        matrix.setRotate(90);
                        break;
                    case 180:
                        matrix.setRotate(180);
                        break;
                    case 270:
                        matrix.setRotate(-90);
                        break;
                }
                bitmap = Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);
                imageview.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //MAKE PICTURE NOT FLIPPED
    private static int getOrientation(Context context, Uri photoUri)
    {
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);

        if (cursor.getCount() != 1) {
            cursor.close();
            return -1;
        }

        cursor.moveToFirst();
        int orientation = cursor.getInt(0);
        cursor.close();
        cursor = null;
        return orientation;
    }

    private void dispalyToast(String message)
    {
        Context context = getContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
