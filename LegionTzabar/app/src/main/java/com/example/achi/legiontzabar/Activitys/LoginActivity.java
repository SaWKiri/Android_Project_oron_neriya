package com.example.achi.legiontzabar.Activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.achi.legiontzabar.R;

import static com.example.achi.legiontzabar.R.id.btnLogin;
import static com.example.achi.legiontzabar.R.id.cbLogin;

/**
 * Login Activity - Entry activity of the app - login activity to keep app data safe.
 * user need to provide user name and password in order to use the app
 */
public class LoginActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private CheckBox checkBox;
    private int counter = 5;
    private static String PREFS = "PREFS";

    //creting the UI elements, input and loging
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Decleration for communicating with the layout*/
        setContentView(R.layout.activity_login);
        Name = (EditText) findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button) findViewById(btnLogin);
        checkBox = (CheckBox) findViewById(cbLogin);
        Info.setText("מספר ניסיונות שנשארו: 5");

        //receive data if checkbox was checked
        receiveData();
        /* When login button pressed its call for validate function to check user validation*/
        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(checkBox.isChecked())
                {
                    saveDate();
                    Toast.makeText(LoginActivity.this, "נשמר", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "לא נשמר", Toast.LENGTH_SHORT).show();
                }
                validate(Name.getText().toString(),Password.getText().toString());
            }

        });
    }
    /* Hard coded username and password*/
    private void validate(String userName,String userPassword)
    {

            if ((userName.equals("s") && userPassword.equals("s"))) {
                Intent intent = new Intent(LoginActivity.this, NavigatorActivity.class);
                startActivity(intent);
                finish();
            } else {
                counter--;
                Info.setText("מספר נסיונות שנשארו: " + String.valueOf(counter));
                if (counter == 0) {
                    Login.setEnabled(false);
                }
            }

    }

    ///SAVE DATA BY SHARED PREFERENCES
    public void saveDate()
    {
        String username = Name.getText().toString();
        String password = Password.getText().toString();

        SharedPreferences preferences = getSharedPreferences(PREFS,0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("USERNAME",username);
        editor.putString("PASSWORD",password);
        editor.commit();

    }
    // RECEIVE DATA BY SHARED PREFERENCES
    public void receiveData()
    {
        SharedPreferences preferences = getSharedPreferences(PREFS,0);
        String username = preferences.getString("USERNAME",null);
        String password = preferences.getString("PASSWORD",null);
        Name.setText(username);
        Password.setText(password);
    }

}
