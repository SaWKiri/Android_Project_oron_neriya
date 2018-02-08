package com.example.achi.legiontzabar.Activitys;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.achi.legiontzabar.DataBase.DBHelper;
import com.example.achi.legiontzabar.DataBase.DatabaseHelper;
import com.example.achi.legiontzabar.Fragments.CommunicationFragment;
import com.example.achi.legiontzabar.Fragments.HumanResourceFragment;
import com.example.achi.legiontzabar.Fragments.QualificationFragment;
import com.example.achi.legiontzabar.Fragments.SoliderDetailFragment;
import com.example.achi.legiontzabar.Fragments.TrainingDetails;
import com.example.achi.legiontzabar.Fragments.TrainingFragment;
import com.example.achi.legiontzabar.R;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * Navigator Activity - the main activity of the app, inside this activity we
 * draw all the app fragments.
 *
 * we use app bar as a navigator - app drawer to move between main fragments.
 *
 */
public class NavigatorActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    boolean doubleBackToExitPressedOnce = false;
    private static final int PERMISSION_REQUEST = 61;
    private static final int PERMISSION_REQUEST_EXPORT = 62;
    private static final int PERMISSION_REQUEST_READ_FILES = 63;


    /**
     * when this activity created - draw the navbar and app bar.
     * @param savedInstanceState - saved activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* Deceleration for communicating with the layout*/
        setContentView(R.layout.activity_navigator);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /* Opens HumanResource Fragment when Login is pressed and validation was OK*/
        if (savedInstanceState == null)
        {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.fram, new HumanResourceFragment());
            tx.commit();;
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * double press back to close app
     */
    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce)
        {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "אם תרצה לצאת מהאפליקציה לחץ שוב", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case R.id.action_exportDB:{
                dispalyToast("מייצא מסד");
                exportDBToXls();

            }
            case R.id.action_importDB: {
                dispalyToast("מייבא מסד");
                importToDB();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * whene app request permission here we handle the request result
     * @param requestCode - code to identify witch method request permission
     * @param permissions - the permission requested
     * @param grantResults - the permission result
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if(requestCode == PERMISSION_REQUEST)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                importToDB();
            }
        }
        else if(requestCode == PERMISSION_REQUEST_EXPORT)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                exportDBToXls();
            }

        }
        else if(requestCode == PERMISSION_REQUEST_READ_FILES)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED)
            {
                DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
                DemoFileImportToDB(dbHelper);
            }

        }
    }

    /**
     * when user press on the nav bar menu item - we handle the action - replacing the fragment ob the screen
     * @param item - item that user press on
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    /* Passing between Fragments on the Navigator Menu + Declaring their names*/
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_HumanResourceFragment)
        {
            setTitle("סדכ");
            HumanResourceFragment fragment = new HumanResourceFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,fragment,"HumanResourceFragment");
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_CommunicationFragment)
        {
            setTitle("תקשורת");
            CommunicationFragment fragment = new CommunicationFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,fragment,"CummunicationFragment");
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_TrainingFragment)
        {
            setTitle("אימונים");
            TrainingFragment fragment = new TrainingFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,fragment,"TrainingFragment");

            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_QualificationFragment)
        {
            setTitle("הכשרות");
            QualificationFragment fragment = new QualificationFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,fragment,"QualificationFragment");
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_trainingDetailsFragment)
        {
            setTitle("פרטי אימון");
            TrainingDetails fragment = new TrainingDetails();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fram,fragment,"TrainingDetails");
            fragmentTransaction.commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    /**
     * Helper method - load the excel file to Database
     */
    private void importToDB(){

        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        //checking permission to read from storage - if no perma finish method
        if(!checkPermission(PERMISSION_REQUEST))
        {
            return;
        }

        final File databsePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Database.xls");

        if(!databsePath.exists())
        {
            dispalyToast("cannot open database");
            return;
        }
        //Heavy task we do in background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                //opening the excel file
                HSSFWorkbook wb = null;

                try {
                    wb = new HSSFWorkbook(new FileInputStream(databsePath));

                    importTable(wb,dbHelper,DBHelper.SOLDIERS_TABLE,DBHelper.SOLDIERS_TABLE_WIDTH);

                    importTable(wb,dbHelper,DBHelper.QUALIFICATION_TABLE,DBHelper.QUALIFICATION_TABLE_WIDTH);

                    DemoFileImportToDB(dbHelper);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    /**
     * Helper method for importToDB - import a sheet to store as a table in Database
     * the Sheets name in the excel file need to match the table name in the DB.
     * @param wb - the excel file
     * @param dbHelper - database access
     * @param tableName - table name to import to DB
     * @param tableWidth - the width of the table
     */
    private void importTable(HSSFWorkbook wb, DatabaseHelper dbHelper, String tableName, int tableWidth) {

        String[] tableData = new String[tableWidth];

        //getting the soldiers table sheet
        Sheet sheet1 = wb.getSheet(tableName);

        Iterator<Row> rowIterator = sheet1.iterator();

        //skip first row - first row is column names
        Row row = rowIterator.next();

        while (rowIterator.hasNext()) {
            row = rowIterator.next();

            //For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();
                String cellStringValue = extractCellValueAsString(cell);

                if (cellStringValue != null) {
                    if (cell.getColumnIndex() <= tableWidth && cell.getColumnIndex() >= 0) {
                        tableData[cell.getColumnIndex()] = cellStringValue;
                    } else {
                        //end of line
                        continue;
                    }


                } else {
                    //TODO unsoppurted cell value, figure out what to do later
                }
            }

            boolean result = dbHelper.insertRowByTable(tableData,tableName);

        }
    }

    /**
     * import word files to Database - demo method loading files from folder Download of the Device
     * storing the file as byte array.
     * @param dbHelper - access to DB
     */
    private void DemoFileImportToDB(DatabaseHelper dbHelper)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        if(!checkPermission(PERMISSION_REQUEST_READ_FILES))
        {
            return;
        }

        final File filepath1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"file1-1.doc");
        final File filepath2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"file2-1.doc");
        final File filepath3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"file3-1.doc");

        if(!filepath1.exists() && !filepath2.exists() && !filepath3.exists())
        {
            //one file is missing
            return;
        }
        try {

            FileInputStream fis1 = new FileInputStream(filepath1);
            FileInputStream fis2 = new FileInputStream(filepath2);
            FileInputStream fis3 = new FileInputStream(filepath3);

            BufferedInputStream bis = new BufferedInputStream(fis1);

            byte[] bufferfile1 = new byte[(int) filepath1.length()];
            byte[] bufferfile2 = new byte[(int) filepath2.length()];
            byte[] bufferfile3 = new byte[(int) filepath3.length()];

            fis1.read(bufferfile1);fis2.read(bufferfile2);fis3.read(bufferfile3);

            dbHelper.insertFiles(DBHelper.FILE_TABLE, 1L ,bufferfile1,bufferfile2,bufferfile3);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * Export the database as an excel file, saving it in the Download directory
     */
    private void exportDBToXls()
    {
        final DatabaseHelper dbHelper = new DatabaseHelper(this);

        if(!checkPermission(PERMISSION_REQUEST_EXPORT))
        {
            dispalyToast("no permission");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        final String currentDateandTime = sdf.format(new Date());

        final File databsePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Database_"+ currentDateandTime + ".xlsx");

        if(!databsePath.exists())
        {
            dispalyToast("cannot open database");
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {


                //opening the excel file
            try {
                HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(databsePath));

                HSSFSheet sheet1 = wb.createSheet("soldiers");

                int numbersOfRows = 0;

                Cursor cursor = dbHelper.getAllSoldiers();

                cursor.moveToFirst();


                //creating the first row - column names
                Row row = sheet1.createRow(numbersOfRows++);
                for(int i = 0 ;i <= DBHelper.SOLDIERS_TABLE_WIDTH - 1; i++ ) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(cursor.getColumnName(i));
                }

                do{
                    row = sheet1.createRow(numbersOfRows++);

                    for(int i = 0 ;i <= DBHelper.SOLDIERS_TABLE_WIDTH; i++ ) {
                        Cell cell = row.createCell(i);
                        cell.setCellValue(cursor.getString(i));
                    }

                }while (cursor.moveToNext());



            } catch (IOException e) {
                e.printStackTrace();
            }


            dispalyToast("ייצוא הסתיים");
            }
        }).start();

    }



    /**
     * Extracting the cell value - allowing to add support for more cell vlaues in the future
     * @param cell
     * @return the cell value as a String
     */
    private  String extractCellValueAsString(Cell cell)
    {
        switch (cell.getCellTypeEnum()) {

            case _NONE:{return null;}
            case BLANK:{return null;}
            case ERROR:{return null;}
            case STRING:{return cell.getStringCellValue();}
            case BOOLEAN:{return  null;}
            case FORMULA:{return null;}
            case NUMERIC:{return String.valueOf((int)cell.getNumericCellValue());}
        }

        return "";
    }

    /**
     * Helper method displaying toast to user
     * @param message - string to display in the toast
     */
    private void dispalyToast(String message)
    {
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     *
     * @param permissionCode - permission code requested
     * @return if we the app have permission or not
     */
    private boolean checkPermission(int permissionCode)
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED   )
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(this
                        ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}
                        ,permissionCode);

            }
            return false;
        }
        return true;
    }

}
